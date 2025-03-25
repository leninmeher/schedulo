package com.schedulo.schedulo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ZoomCreateMeetingResponseDto {

    @JsonProperty(value="start_time")
    private String startTime;

    @JsonProperty(value="start_url")
    private String startUrl;

    @JsonProperty(value="join_url")
    private String joinUrl;

    @JsonProperty(value="password")
    private String password;

    @JsonProperty(value="id")
    private String meetingId;
}
