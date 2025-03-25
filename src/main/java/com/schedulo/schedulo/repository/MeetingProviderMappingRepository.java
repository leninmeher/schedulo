package com.schedulo.schedulo.repository;

import com.schedulo.schedulo.entity.ProviderMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingProviderMappingRepository extends JpaRepository<ProviderMeeting, Long> {

}
