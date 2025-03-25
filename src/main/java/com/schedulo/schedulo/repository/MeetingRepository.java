package com.schedulo.schedulo.repository;

import com.schedulo.schedulo.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, String> {

    List<Meeting> findByOwner(String email);
}
