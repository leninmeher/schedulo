package com.schedulo.schedulo.scheduler;

import com.schedulo.schedulo.entity.Meeting;
import com.schedulo.schedulo.enums.MeetingStatus;
import com.schedulo.schedulo.enums.MeetingType;
import com.schedulo.schedulo.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class StatusScheduler {

    @Autowired
    private MeetingRepository meetingRepository;

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    @Scheduled(fixedRate = 60000) // Runs every 1 minute
    public void updateScheduledMeetings() {
        executor.submit(() -> {
            meetingRepository.setLastFiveMinutesScheduledMeeting(MeetingStatus.ONGOING.getCode());
            meetingRepository.setLastFiveMinutesCompletedMeeting(MeetingStatus.COMPLETED.getCode());
        });
    }

}
