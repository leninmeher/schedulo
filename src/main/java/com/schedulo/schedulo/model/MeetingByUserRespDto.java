package com.schedulo.schedulo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeetingByUserRespDto {

    private Long meetingId;

    private String meetingTitle;

    private String meetingDesc;

    private String meetingAgenda;

    private LocalDateTime meetingStartTime;

    private LocalDateTime meetingEndTime;

    private String meetingType;

    private String owner;

    private List<String> participantsMail;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
