package com.schedulo.schedulo.repository;


import com.schedulo.schedulo.entity.ProviderMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingProviderMappingRepository extends JpaRepository<ProviderMeeting, Long> {


}
