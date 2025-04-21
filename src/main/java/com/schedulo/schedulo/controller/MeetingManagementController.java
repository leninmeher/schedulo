package com.schedulo.schedulo.controller;

import com.schedulo.schedulo.entity.Meeting;
import com.schedulo.schedulo.enums.MeetingStatus;
import com.schedulo.schedulo.exception.UserErrorException;
import com.schedulo.schedulo.model.CreateMeetingReqDto;
import com.schedulo.schedulo.model.MeetingByUserRespDto;
import com.schedulo.schedulo.service.MeetingManagementService;
import com.schedulo.schedulo.utils.CustomResponse;
import com.schedulo.schedulo.utils.authentication.JwtAuthenticationFilter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class MeetingManagementController {

    @Autowired
    private MeetingManagementService meetingManagementService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @PostMapping("/meeting/create")
    public ResponseEntity<CustomResponse> createMeeting(@Valid @RequestBody CreateMeetingReqDto request,
                                                     @RequestHeader HttpHeaders httpHeaders){
        Meeting responseDto;
        try{
            String userMail = jwtAuthenticationFilter.getEmailFromToken(httpHeaders.get("Authorization").get(0));
            request.setOwner(userMail);
            responseDto = meetingManagementService.createMeeting(request);
        }catch(Exception e){
            return new ResponseEntity<>(new CustomResponse(500, e.getMessage()), HttpStatusCode.valueOf(500));
        }

        return new ResponseEntity<>(new CustomResponse(200, responseDto), HttpStatusCode.valueOf(200));
    }

//    @GetMapping("/meeting/getMeetings")
//    public ResponseEntity<CustomResponse>getMeetingByUser(
//                                                          @RequestHeader HttpHeaders httpHeaders) throws Exception {
//       List<MeetingByUserRespDto>meetingList;
//        try {
//            String userMail = jwtAuthenticationFilter.getEmailFromToken(httpHeaders.get("Authorization").get(0));
//           // meetingList=meetingManagementService.getMeetingByUser(userMail);
//            CompletableFuture<List<MeetingByUserRespDto>>meetingList1=meetingManagementService.getMeetingByUser(userMail);
//            meetingList=meetingList1.get();
//        }catch (Exception e){
//            return new ResponseEntity<>(new CustomResponse(500, e.getMessage()), HttpStatusCode.valueOf(500));
//        }
//        return new ResponseEntity<>(new CustomResponse(200, meetingList), HttpStatusCode.valueOf(200));
//
//    }

    @GetMapping("/meeting/getScheduledMeetings")
    public ResponseEntity<CustomResponse>getScheduledMeetings(
            @RequestHeader HttpHeaders httpHeaders) throws Exception {
        List<MeetingByUserRespDto>meetingList;
        try {
            String userMail = jwtAuthenticationFilter.getEmailFromToken(httpHeaders.get("Authorization").get(0));
            // meetingList=meetingManagementService.getMeetingByUser(userMail);
            CompletableFuture<List<MeetingByUserRespDto>>meetingList1=meetingManagementService.getMeetingByUser(userMail,MeetingStatus.SCHEDULED.getCode());
            meetingList=meetingList1.get();
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponse(500, e.getMessage()), HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(new CustomResponse(200, meetingList), HttpStatusCode.valueOf(200));

    }
    @GetMapping("/meeting/getCompletedMeetings")
    public ResponseEntity<CustomResponse>getCompletedMeetings(
            @RequestHeader HttpHeaders httpHeaders) throws Exception {
        List<MeetingByUserRespDto>meetingList;
        try {
            String userMail = jwtAuthenticationFilter.getEmailFromToken(httpHeaders.get("Authorization").get(0));
            // meetingList=meetingManagementService.getMeetingByUser(userMail);
            CompletableFuture<List<MeetingByUserRespDto>>meetingList1=meetingManagementService.getMeetingByUser(userMail, MeetingStatus.COMPLETED.getCode());
            meetingList=meetingList1.get();
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponse(500, e.getMessage()), HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(new CustomResponse(200, meetingList), HttpStatusCode.valueOf(200));

    }
    @GetMapping("/meeting/getCancelledMeetings")
    public ResponseEntity<CustomResponse>getCancelledMeetings(
            @RequestHeader HttpHeaders httpHeaders) throws Exception {
        List<MeetingByUserRespDto>meetingList;
        try {
            String userMail = jwtAuthenticationFilter.getEmailFromToken(httpHeaders.get("Authorization").get(0));
            // meetingList=meetingManagementService.getMeetingByUser(userMail);
            CompletableFuture<List<MeetingByUserRespDto>>meetingList1=meetingManagementService.getMeetingByUser(userMail,MeetingStatus.CANCELLED.getCode());
            meetingList=meetingList1.get();
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponse(500, e.getMessage()), HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(new CustomResponse(200, meetingList), HttpStatusCode.valueOf(200));

    }
}
