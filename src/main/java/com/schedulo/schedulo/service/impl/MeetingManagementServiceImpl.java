package com.schedulo.schedulo.service.impl;

import com.schedulo.schedulo.entity.Meeting;
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

        byte[] participantsBlob = String.join(",", requestDto.getParticipantsMail()).getBytes();
        meeting.setParticipants(participantsBlob);

        meeting.setMeetingType(requestDto.getMeetingType());
        meeting.setUpdatedOn(LocalDateTime.now());

        Meeting savedMeeting =  meetingRepository.save(meeting);

        meetingOrchestrationService.createMeeting(savedMeeting);
        saveParticipantsForMeetingId(String.valueOf(savedMeeting.getMeetingId()),requestDto.getParticipantsMail());

        return savedMeeting;



    }
    @Override
    public List<MeetingByUserRespDto> getMeetingByUser(String emailId) throws Exception{
        List<Meeting>meetingList=meetingRepository.findByOwner(emailId);

        if(meetingList.isEmpty()){
            throw new UserErrorException("No meeting has been scheduled");
        }
        List<MeetingByUserRespDto>meetingByUserRespDtoList=new ArrayList<>();
        for(Meeting meeting: meetingList){
            MeetingByUserRespDto meetingByUserRespDto=new MeetingByUserRespDto();
            meetingByUserRespDto.setMeetingId(meeting.getMeetingId());
            meetingByUserRespDto.setMeetingTitle(meeting.getMeetingTitle());
            meetingByUserRespDto.setMeetingDesc(meeting.getMeetingDesc());
            meetingByUserRespDto.setMeetingAgenda(meeting.getMeetingAgenda());
            meetingByUserRespDto.setMeetingStartTime(meeting.getMeetingStartTime());
            meetingByUserRespDto.setMeetingEndTime(meeting.getMeetingEndTime());
            meetingByUserRespDto.setMeetingType(meeting.getMeetingType());
            meetingByUserRespDto.setOwner(meeting.getOwner());
            meetingByUserRespDto.setCreatedOn(meeting.getCreatedOn());
            meetingByUserRespDto.setUpdatedOn(meeting.getUpdatedOn());
            byte[]participantsBlob=meeting.getParticipants();
            String participants=new String(participantsBlob);
            String[] participantsArray=participants.split(",");
            meetingByUserRespDto.setParticipantsMail(Arrays.asList(participantsArray));
            meetingByUserRespDtoList.add(meetingByUserRespDto);
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
