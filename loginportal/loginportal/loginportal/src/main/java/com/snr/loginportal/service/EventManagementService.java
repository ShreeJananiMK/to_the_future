package com.snr.loginportal.service;

import com.snr.loginportal.model.EventManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface EventManagementService {


    void addEvents(EventManagement eventManagement);

    Page<EventManagement> getEvents(Map<String, String> requestParams, Pageable pageable);

    boolean deleteEventsById(Long eventId);

    boolean isEventNameExist(String eventName);

    boolean isEventIdExist(Long eventId);

    EventManagement getEvent (String eventName);
}
