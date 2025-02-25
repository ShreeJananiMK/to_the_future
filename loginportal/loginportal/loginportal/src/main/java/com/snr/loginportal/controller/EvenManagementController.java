package com.snr.loginportal.controller;

import com.snr.loginportal.model.EventManagement;
import com.snr.loginportal.service.EventManagementService;
import com.snr.loginportal.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api")
public class EvenManagementController {

    @Autowired
    EventManagementService eventManagementService;

    private final Logger logger = LoggerFactory.getLogger(EvenManagementController.class);

    @PostMapping("/events")
    public ResponseEntity<?> addEvents(@RequestBody EventManagement eventManagement){
        logger.info("The Input data : ---- : {}", eventManagement);
        if(eventManagement!=null){
            if(eventManagementService.isEventNameExist(eventManagement.getEventName())){
                return new ResponseEntity<>(new ApiResponse(HttpStatus.FOUND,"Event name already exists"),HttpStatus.FOUND);
            }
           else  {
               eventManagementService.addEvents(eventManagement);
               return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"Events added successfully"),HttpStatus.OK);}

        }
        else return new ResponseEntity<>(new ApiResponse(HttpStatus.CONFLICT,"Incorrect data"),HttpStatus.CONFLICT);

    }

    @GetMapping("/events")
    public ResponseEntity<?> getEvents(Map<String, String> requestParams, Pageable pageable){
        Page<EventManagement> result = eventManagementService.getEvents(requestParams,pageable);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,result),HttpStatus.OK);
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<?> deleteEvents(@PathVariable("id") Long eventId){
        logger.info("The Input Id : ---- : {}", eventId);
        if(eventManagementService.isEventIdExist(eventId)){
            boolean result = !eventManagementService.deleteEventsById(eventId);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, result),HttpStatus.OK);
        }
        else return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,"Event not found"), HttpStatus.NOT_FOUND);

    }
}
