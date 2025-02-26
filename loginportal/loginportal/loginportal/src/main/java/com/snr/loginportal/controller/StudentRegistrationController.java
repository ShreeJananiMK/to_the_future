package com.snr.loginportal.controller;

import com.snr.loginportal.dto.StatusProjection;
import com.snr.loginportal.model.StudentRegistration;
import com.snr.loginportal.service.EventManagementService;
import com.snr.loginportal.service.StudentRegistrationService;
import com.snr.loginportal.service.implement.EventManagementServiceImpl;
import com.snr.loginportal.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH })
@RestController
@RequestMapping("/api")
public class StudentRegistrationController {

    @Autowired
    StudentRegistrationService studentRegistrationService;

    @Autowired
    EventManagementService eventManagementService;

    private final Logger logger = LoggerFactory.getLogger(StudentRegistrationController.class);
    @PostMapping("/registrations")
    public ResponseEntity<?> addStudentDetails(@RequestBody StudentRegistration studentRegistration){
        if(studentRegistration != null){
            logger.info("The Input data : ---- : {}",studentRegistration);
            if(eventManagementService.isEventNameExist(studentRegistration.getEventName())) {
                if (studentRegistrationService.isARegisteredStudent(studentRegistration.getEventName(), studentRegistration.getEmail())) {

                    studentRegistrationService.addStudentDetails(studentRegistration);
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Registered successfully"), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.ALREADY_REPORTED, "Student already registered for the event :" + studentRegistration.getEventName()), HttpStatus.ALREADY_REPORTED);
                }
            }
            else return new ResponseEntity<>(new ApiResponse(HttpStatus.CONFLICT,"Event does not exist"), HttpStatus.CONFLICT);
        }
        else return new ResponseEntity<>(new ApiResponse(HttpStatus.CONFLICT,"Data insufficient"), HttpStatus.CONFLICT);
    }

    @GetMapping("/registrations")
    public ResponseEntity<?> getStudentDetails(@RequestParam Map<String, String> requestParams, Pageable pageable){
        Page<StudentRegistration> result = studentRegistrationService.getStudentDetails(requestParams, pageable);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, result), HttpStatus.OK);
    }

    @PatchMapping("/registrations/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id, @RequestParam String status){
        if(studentRegistrationService.studentExists(id)) {
            logger.info("Student Id : ---- : {}",studentRegistrationService.studentExists(id));
            String result = studentRegistrationService.updateStatus(id, status);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, result), HttpStatus.OK);
        }
        else return new ResponseEntity<>(new ApiResponse(HttpStatus.CONFLICT, "Invalid Id"), HttpStatus.CONFLICT);
    }

    @GetMapping("/registrations/status")
    public ResponseEntity<?> getStatus(){
        StatusProjection result = studentRegistrationService.getStatus();
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,result), HttpStatus.OK);
    }

}

