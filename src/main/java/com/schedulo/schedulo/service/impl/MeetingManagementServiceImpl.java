package com.schedulo.schedulo.service.impl;

import com.schedulo.schedulo.entity.Meeting;
import com.schedulo.schedulo.enums.MeetingType;
import com.schedulo.schedulo.model.CreateMeetingReqDto;
import com.schedulo.schedulo.repository.MeetingRepository;
import com.schedulo.schedulo.service.MeetingManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MeetingManagementServiceImpl implements MeetingManagementService {

    @Autowired
    private MeetingRepository meetingRepository;

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

        return meetingRepository.save(meeting);

    }
}
