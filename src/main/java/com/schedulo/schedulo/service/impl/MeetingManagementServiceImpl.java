package com.schedulo.schedulo.service.impl;

import com.schedulo.schedulo.entity.Meeting;
import com.schedulo.schedulo.enums.MeetingStatus;
import com.schedulo.schedulo.entity.ParticipantsMeeting;
import com.schedulo.schedulo.enums.MeetingType;
import com.schedulo.schedulo.exception.UserErrorException;
import com.schedulo.schedulo.model.CreateMeetingReqDto;
import com.schedulo.schedulo.model.MeetingByUserRespDto;
import com.schedulo.schedulo.repository.MeetingParticipantsMappingRepository;
import com.schedulo.schedulo.repository.MeetingRepository;
import com.schedulo.schedulo.service.MeetingManagementService;
import com.schedulo.schedulo.service.MeetingOrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
public class MeetingManagementServiceImpl implements MeetingManagementService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingOrchestrationService meetingOrchestrationService;

    @Autowired
    private  MeetingParticipantsMappingRepository meetingParticipantsMappingRepository;

    @Override
    public Meeting createMeeting(CreateMeetingReqDto requestDto) throws Exception{

        Meeting meeting = new Meeting();
        meeting.setMeetingTitle(requestDto.getMeetingTitle());
        meeting.setMeetingDesc(requestDto.getMeetingDesc());
        meeting.setMeetingAgenda(requestDto.getMeetingAgenda());
        meeting.setMeetingStartTime(requestDto.getMeetingStartTime());
        meeting.setMeetingEndTime(requestDto.getMeetingEndTime());
        meeting.setOwner(requestDto.getOwner());
        if("S".equalsIgnoreCase(requestDto.getStatus())){
            meeting.setStatus(MeetingStatus.SCHEDULED.getCode());
            meeting.setMeetingType(MeetingType.SCHEDULED.getCode());
        }else if("O".equalsIgnoreCase(requestDto.getStatus())){
            meeting.setStatus(MeetingStatus.ONGOING.getCode());
            meeting.setMeetingType(MeetingType.CURRENT.getCode());
        }

        byte[] participantsBlob = String.join(",", requestDto.getParticipantsMail()).getBytes();
        meeting.setParticipants(participantsBlob);

        meeting.setUpdatedOn(LocalDateTime.now());

        Meeting savedMeeting =  meetingRepository.save(meeting);

        meetingOrchestrationService.createMeeting(savedMeeting);
        saveParticipantsForMeetingId(String.valueOf(savedMeeting.getMeetingId()),requestDto.getParticipantsMail());

        return savedMeeting;



    }

    @Override
    public CompletableFuture<List<MeetingByUserRespDto>> getMeetingByUser(String emailId,String status) throws Exception {
        CompletableFuture<List<MeetingByUserRespDto>> ownerMeeting = CompletableFuture.supplyAsync(()->{
            return getMeetingByUserOwner(emailId,status);

       });
        CompletableFuture<List<MeetingByUserRespDto>>participantMeeting=CompletableFuture.supplyAsync(()->{
            return  getMeetingByUserParticipants(emailId,status);
        });
       return ownerMeeting.thenCombine(participantMeeting,(ownerMeetings,participantMeetings)->{
           List<MeetingByUserRespDto>combinedMeetings=new ArrayList<>();
           if(ownerMeetings !=null){
               combinedMeetings.addAll(ownerMeetings);
           }
           if(participantMeetings!=null){
               combinedMeetings.addAll(participantMeetings);
           }
           return combinedMeetings;
       });
    }
    @Override
    public List<MeetingByUserRespDto> getMeetingByUserOwner(String emailId,String status) {
        List<Meeting>meetingList=meetingRepository.findByOwnerAndStatus(emailId,status);
        if(!meetingList.isEmpty()){
            MeetingByUserRespDto meetingByUserRespDto=new MeetingByUserRespDto();
            meetingByUserRespDto.setRole("Owner");
            meetingByUserRespDto.setIsDeleted(Boolean.TRUE);

            return getMeetingByUserRespDtos(meetingList,meetingByUserRespDto);
        }
        return null;
    }

    @Override
    public List<MeetingByUserRespDto> getMeetingByUserParticipants(String emailId,String status) {
        List<ParticipantsMeeting>meetingList=meetingParticipantsMappingRepository.findByParticipantsMailId(emailId);
        List<String> meetingIds = meetingList.stream()
                .map(ParticipantsMeeting::getMeetingId)
                .toList();
        List<Long> meetingIdsLong = meetingIds.stream()
                .map(Long::parseLong)
                .toList();
        List<Meeting>meetingList1=meetingRepository.findByStatusAndMeetingIdIn(status,meetingIdsLong);
        if(!meetingList.isEmpty()){

            MeetingByUserRespDto meetingByUserRespDto=new MeetingByUserRespDto();
            meetingByUserRespDto.setRole("Participants");
            meetingByUserRespDto.setIsDeleted(FALSE);
            return getMeetingByUserRespDtos(meetingList1,meetingByUserRespDto);
        }
        return null;

    }

    private static List<MeetingByUserRespDto> getMeetingByUserRespDtos(List<Meeting> meetingList,MeetingByUserRespDto meetingByUserRespDto) {
        List<MeetingByUserRespDto>meetingByUserRespDtoList=new ArrayList<>();
        for(Meeting meeting: meetingList){
            MeetingByUserRespDto dto = new MeetingByUserRespDto();
            dto.setRole(meetingByUserRespDto.getRole());
            dto.setIsDeleted(meetingByUserRespDto.getIsDeleted());
            if(dto.getIsDeleted()==TRUE){
                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime meetingStartTime = meeting.getMeetingStartTime();
                if (meetingStartTime.isBefore(currentTime.plusMinutes(10))) {
                    dto.setIsDeleted(false);
                }
            }
            dto.setMeetingId(meeting.getMeetingId());
            dto.setMeetingTitle(meeting.getMeetingTitle());
            dto.setMeetingDesc(meeting.getMeetingDesc());
            dto.setMeetingAgenda(meeting.getMeetingAgenda());
            dto.setMeetingStartTime(meeting.getMeetingStartTime());
            dto.setMeetingEndTime(meeting.getMeetingEndTime());
            dto.setMeetingType(meeting.getMeetingType());
            dto.setOwner(meeting.getOwner());
            dto.setCreatedOn(meeting.getCreatedOn());
            dto.setUpdatedOn(meeting.getUpdatedOn());
            byte[]participantsBlob=meeting.getParticipants();
            String participants=new String(participantsBlob);
            String[] participantsArray=participants.split(",");
            dto.setParticipantsMail(Arrays.asList(participantsArray));
            meetingByUserRespDtoList.add(dto);
        }
        return meetingByUserRespDtoList;
    }



    @Async
    public CompletableFuture<Void> saveParticipantsForMeetingId(String meetingId, List<String>participantsMail){

        return CompletableFuture.runAsync(() -> {
            for(String email:participantsMail){
                ParticipantsMeeting participantsMeeting=new ParticipantsMeeting();
                participantsMeeting.setMeetingId(meetingId);
                participantsMeeting.setParticipantsMailId(email);
                participantsMeeting.setCreatedOn(LocalDateTime.now());
                meetingParticipantsMappingRepository.save(participantsMeeting);
            }
        });
    }

}
