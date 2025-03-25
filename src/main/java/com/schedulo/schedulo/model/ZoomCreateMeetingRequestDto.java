package com.schedulo.schedulo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ZoomCreateMeetingRequestDto {
    private String topic;
    private String agenda;

    @JsonProperty(value="start_time")
    private String startTime;
    private Long duration;
    private Long type;
    private String timezone;
}
