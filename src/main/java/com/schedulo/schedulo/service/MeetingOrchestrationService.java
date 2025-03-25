package com.schedulo.schedulo.service;

import com.schedulo.schedulo.entity.Meeting;
import org.springframework.stereotype.Service;

@Service
public interface MeetingOrchestrationService {
    void createMeeting(Meeting meeting) throws Exception;
}
