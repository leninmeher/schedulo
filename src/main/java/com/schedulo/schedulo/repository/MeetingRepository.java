package com.schedulo.schedulo.repository;

import com.schedulo.schedulo.entity.Meeting;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, String> {

    List<Meeting> findByOwnerAndStatus(String email,String status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE meetings\n" +
            "SET status = :status\n" +
            "WHERE status = 'S'\n" +
            "  AND START_TIME BETWEEN NOW() AND NOW() + INTERVAL '1 minutes'", nativeQuery = true)
    List<Meeting> setLastFiveMinutesScheduledMeeting(String status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE meetings\n" +
            "SET status = :status\n" +
            "WHERE status = 'S'\n" +
            "  AND END_TIME BETWEEN NOW() AND NOW() - INTERVAL '1 minutes'", nativeQuery = true)
    List<Meeting> setLastFiveMinutesCompletedMeeting(String status);

    List<Meeting> findByStatusAndMeetingIdIn(String status,List<Long> meetingId);
}
