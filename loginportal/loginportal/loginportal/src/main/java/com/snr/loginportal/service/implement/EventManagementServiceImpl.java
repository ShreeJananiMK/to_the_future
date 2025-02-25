package com.snr.loginportal.service.implement;

import com.snr.loginportal.config.WebConfig;;
import com.snr.loginportal.model.EventManagement;
import com.snr.loginportal.repository.EventManagementRepo;
import com.snr.loginportal.service.EventManagementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EventManagementServiceImpl implements EventManagementService {
    private final Logger logger = LoggerFactory.getLogger(EventManagementServiceImpl.class);

    @Autowired
    EventManagementRepo eventManagementRepo;

    @Autowired
    WebConfig webConfig;

    @Override
    public void addEvents(EventManagement eventManagement) {
        eventManagementRepo.save(eventManagement);
    }

    @Override
    public Page<EventManagement> getEvents(Map<String, String> requestParams, Pageable pageable) {
        Pageable resolvedPageable = webConfig.resolvePageable(requestParams, pageable);
        return eventManagementRepo.findAll(resolvedPageable);
    }

   /* @Override
    public Page getEvents(Map<String, String> requestParams, Pageable pageable) {
        Pageable resolvedPageable = webConfig.resolvePageable(requestParams, pageable);
        return eventManagementRepo.findAll(pageable);
    }*/

    @Override
    public boolean deleteEventsById(Long eventId) {
        eventManagementRepo.deleteById(eventId);
        return eventManagementRepo.isEventIdExist(eventId) == null;
    }

    @Override
    public boolean isEventNameExist(String eventName) {
        logger.info("The Input data : ---- : {}",eventName);
        logger.info("The Input data : ---- : {}",eventManagementRepo.isEventNameExist(eventName) == 1);
        return eventManagementRepo.isEventNameExist(eventName) == 1;
    }

    @Override
    public boolean isEventIdExist(Long eventId) {
        logger.info("The Input Id : ---- : {}", eventManagementRepo.isEventIdExist(eventId) == 1);
        return eventManagementRepo.isEventIdExist(eventId) == 1;
    }

    @Override
    public EventManagement getEvent(String eventName) {
        return eventManagementRepo.getEvent(eventName);
    }
}

