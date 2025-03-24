package com.schedulo.schedulo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "meetings")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MEETING_ID")
    private Long meetingId;

    @Column(name="TITLE")
    private String meetingTitle;

    @Column(name="DESCRIPTION")
    private String meetingDesc;

    @Column(name="AGENDA")
    private String meetingAgenda;

    @Column(name="START_TIME")
    private LocalDateTime meetingStartTime;

    @Column(name="END_TIME")
    private LocalDateTime meetingEndTime;

    @Column(name="TYPE")
    private String meetingType;

    @Column(name="OWNER")
    private String Owner;

    @Lob
    @Column(name="PARTICIPANTS", columnDefinition = "BYTEA")
    @JdbcTypeCode(Types.BINARY)
    private byte[] participants;

    @Column(name="CREATED_ON")
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(name="UPDATED_ON")
    private LocalDateTime updatedOn;

    @Transient
    private List<String> participantsMail;

}
