package com.schedulo.schedulo.repository;

import com.schedulo.schedulo.entity.ParticipantsMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingParticipantsMappingRepository extends JpaRepository<ParticipantsMeeting,Long> {
    List<ParticipantsMeeting> findByParticipantsMailId(String emailId);

}
