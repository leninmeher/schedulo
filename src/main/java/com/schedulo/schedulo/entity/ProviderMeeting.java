package com.schedulo.schedulo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MEETING_PROVIDER_MAPPING")
public class ProviderMeeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MEETING_PROVIDER_MAPPING_ID")
    private Long providerMeetingMappingId;

    @Column(name="MEETING_ID")
    private String meetingId;

    @Column(name="PROVIDER_MEETING_ID")
    private String providerMeetingId;

    @Column(name="PROVIDER_ID")
    private String providerId;

    @Column(name="MEETING_START_URL")
    private String meetingStartUrl;

    @Column(name="MEETING_JOIN_URL")
    private String meetingJoinUrl;

    @Column(name="MEETING_PASSWORD")
    private String meetingPassword;

    @Column(name="MEETING_START_TIME")
    private LocalDateTime meetingStartTime;

    @Column(name="CREATED_ON")
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(name="MEETING_STATUS")
    private String meetingStatus;
}
