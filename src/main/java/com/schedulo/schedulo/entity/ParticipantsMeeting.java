package com.schedulo.schedulo.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name="MEETING_PARTICIPANTS_MAPPING")
public class ParticipantsMeeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="MEETING_ID")
    private String meetingId;

    @Column(name = "PARTICIPANTS_MAILID")
    private String participantsMailId;

    @Column(name="CREATED_ON")
    private LocalDateTime createdOn = LocalDateTime.now();

}
