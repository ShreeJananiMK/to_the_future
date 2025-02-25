package com.snr.loginportal.service;

import com.snr.loginportal.dto.StatusProjection;
import com.snr.loginportal.model.StudentRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface StudentRegistrationService {

    void addStudentDetails(StudentRegistration studentRegistration);

    boolean isARegisteredStudent(String eventName, String email);

    Page<StudentRegistration> getStudentDetails(Map<String, String> requestParams, Pageable pageable);

    boolean studentExists(Long id);

    String updateStatus(Long id, String status);

    StatusProjection getStatus();
}
