package com.schedulo.schedulo.client;

import com.schedulo.schedulo.model.ZoomCreateMeetingRequestDto;
import com.schedulo.schedulo.model.ZoomCreateMeetingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "zoomClient", url = "https://api.zoom.us/v2")
public interface ZoomClient {

    @PostMapping(value="users/me/meetings", consumes = "application/json")
    ZoomCreateMeetingResponseDto createZoomMeeting(@RequestHeader HttpHeaders headers,
                                                   @RequestBody ZoomCreateMeetingRequestDto requestDto);

}
