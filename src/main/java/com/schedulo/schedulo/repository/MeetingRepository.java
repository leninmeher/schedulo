package com.schedulo.schedulo.repository;

import com.schedulo.schedulo.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}
