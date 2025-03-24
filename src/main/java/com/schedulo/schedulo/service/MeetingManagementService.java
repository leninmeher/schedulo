package com.schedulo.schedulo.service;

import com.schedulo.schedulo.entity.Meeting;
import com.schedulo.schedulo.model.CreateMeetingReqDto;
import org.springframework.stereotype.Service;

@Service
public interface MeetingManagementService {

    Meeting createMeeting(CreateMeetingReqDto reqDto) throws Exception;
}
