package com.schedulo.schedulo.service.impl;

import com.schedulo.schedulo.client.ZoomAuthClient;
import com.schedulo.schedulo.client.ZoomClient;
import com.schedulo.schedulo.entity.Meeting;
import com.schedulo.schedulo.entity.ProviderMeeting;
import com.schedulo.schedulo.model.ZoomCreateMeetingRequestDto;
import com.schedulo.schedulo.model.ZoomCreateMeetingResponseDto;
import com.schedulo.schedulo.repository.MeetingProviderMappingRepository;
import com.schedulo.schedulo.service.MeetingOrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZoomMeetingOrchestration implements MeetingOrchestrationService {

    @Autowired
    private ZoomAuthClient zoomAuthClient;

    @Autowired
    private MeetingProviderMappingRepository meetingProviderMappingRepository;

    @Autowired
    private ZoomClient zoomClient;

    @Value("${zoom.clientId}")
    private String clientId;

    @Value("${zoom.clientSecret}")
    private String clientSecret;

    @Value("${zoom.accountId}")
    private String accountId;

    private String zoomAccessToken;

    private void setZoomCredentials() throws Exception{
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/x-www-form-urlencoded");
        headers.set("Authorization", "Basic "+encodedCredentials);

        Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "account_credentials");
        formParams.put("account_id", accountId);
        Map<String, String> zoomCred = zoomAuthClient.getZoomCred(headers, formParams);

        if(zoomCred.containsKey("access_token")){
            this.zoomAccessToken = zoomCred.get("access_token");
        }else{
            //TODO: THROW EXCEPTION
        }
    }



    @Override
    public void createMeeting(Meeting meeting) throws Exception{
        try{
            setZoomCredentials();
            ZoomCreateMeetingRequestDto zoomCreateMeetingRequestDto = new ZoomCreateMeetingRequestDto();
            zoomCreateMeetingRequestDto.setAgenda(meeting.getMeetingAgenda());
            zoomCreateMeetingRequestDto.setTopic(meeting.getMeetingTitle());
            zoomCreateMeetingRequestDto.setTimezone("Asia/Calcutta");
            if("S".equalsIgnoreCase(meeting.getMeetingType())){
                zoomCreateMeetingRequestDto.setType(2l);
            } else if ("C".equalsIgnoreCase(meeting.getMeetingType())) {
                zoomCreateMeetingRequestDto.setType(1l);
            }else{
                //TODO: Throw exception
            }


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String formatted = meeting.getMeetingStartTime().format(formatter);
            zoomCreateMeetingRequestDto.setStartTime(formatted);

            Long durationInMins = Duration.between(meeting.getMeetingStartTime(), meeting.getMeetingEndTime()).toMinutes();
            zoomCreateMeetingRequestDto.setDuration(durationInMins);


            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer "+this.zoomAccessToken);
            headers.set("Content-Type", "application/json");

            ZoomCreateMeetingResponseDto responseDto = zoomClient.createZoomMeeting(
                    headers, zoomCreateMeetingRequestDto
            );

            saveZoomMeeting(responseDto, meeting.getMeetingId());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void saveZoomMeeting(ZoomCreateMeetingResponseDto responseDto, Long meetingId){
        ProviderMeeting providerMeeting = new ProviderMeeting();
        providerMeeting.setProviderMeetingId(responseDto.getMeetingId());
        providerMeeting.setMeetingPassword(responseDto.getPassword());
        providerMeeting.setMeetingStartUrl(responseDto.getStartUrl());
        providerMeeting.setMeetingJoinUrl(responseDto.getJoinUrl());
        providerMeeting.setMeetingId(String.valueOf(meetingId));

        ZonedDateTime utcZonedDateTime = ZonedDateTime.parse(responseDto.getStartTime(), DateTimeFormatter.ISO_DATE_TIME);

        // Convert to IST (Indian Standard Time: UTC +05:30)
        ZonedDateTime istZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));

        providerMeeting.setMeetingStartTime(istZonedDateTime.toLocalDateTime());

        meetingProviderMappingRepository.save(providerMeeting);

    }

}
