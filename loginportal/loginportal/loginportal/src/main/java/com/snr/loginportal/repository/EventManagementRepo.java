package com.snr.loginportal.repository;

import com.snr.loginportal.model.EventManagement;
import com.snr.loginportal.service.EventManagementService;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Repository
public interface EventManagementRepo extends JpaRepository<EventManagement, Long> {

    @Query(value = "select exists (select 1 from event_management where event_name =?1)", nativeQuery = true)
    Long isEventNameExist (String eventName);


    @Query(value = "select exists(select 1 from event_management where event_id =?1)", nativeQuery = true)
    Long isEventIdExist (Long eventId);

    @Query(value = "select * from event_management where event_name =?1", nativeQuery = true)
    EventManagement getEvent (String eventName);
}
