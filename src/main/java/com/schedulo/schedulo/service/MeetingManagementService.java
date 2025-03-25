package com.schedulo.schedulo.service;

import com.schedulo.schedulo.entity.Meeting;
import com.schedulo.schedulo.model.CreateMeetingReqDto;
import com.schedulo.schedulo.model.MeetingByUserRespDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeetingManagementService {

    Meeting createMeeting(CreateMeetingReqDto reqDto) throws Exception;
    List<MeetingByUserRespDto> getMeetingByUser(String emailId)throws Exception;
}
