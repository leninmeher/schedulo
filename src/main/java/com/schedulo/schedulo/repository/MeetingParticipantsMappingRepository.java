package com.schedulo.schedulo.repository;

import com.schedulo.schedulo.entity.ParticipantsMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingParticipantsMappingRepository extends JpaRepository<ParticipantsMeeting,Long> {

}
