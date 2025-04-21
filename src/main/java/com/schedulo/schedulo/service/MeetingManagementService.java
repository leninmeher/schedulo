package com.schedulo.schedulo.service;

import com.schedulo.schedulo.entity.Meeting;
import com.schedulo.schedulo.model.CreateMeetingReqDto;
import com.schedulo.schedulo.model.MeetingByUserRespDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface MeetingManagementService {

    Meeting createMeeting(CreateMeetingReqDto reqDto) throws Exception;
    CompletableFuture<List<MeetingByUserRespDto>> getMeetingByUser(String emailId,String status)throws Exception;
    List<MeetingByUserRespDto> getMeetingByUserParticipants(String emailId,String status)throws Exception;
    List<MeetingByUserRespDto> getMeetingByUserOwner(String emailId,String status)throws Exception;


}
