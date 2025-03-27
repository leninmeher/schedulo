package com.schedulo.schedulo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateMeetingReqDto {

    @NotBlank(message = "Meeting title cannot be empty")
    private String meetingTitle;

    private String meetingDesc;

    private String meetingAgenda;

    @NotNull(message = "Start time is required")
    private LocalDateTime meetingStartTime;

    @NotNull(message = "End time is required")
    private LocalDateTime meetingEndTime;

    @NotBlank(message = "Meeting type cannot be empty")
    private String meetingType;

    @NotBlank(message = "Owner cannot be empty")
    private String owner;

    @NotEmpty(message = "Participants list cannot be empty")
    private List<String> participantsMail;

    private String status;
}
