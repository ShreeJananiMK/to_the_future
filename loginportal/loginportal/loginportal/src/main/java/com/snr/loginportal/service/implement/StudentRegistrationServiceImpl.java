package com.snr.loginportal.service.implement;

import com.snr.loginportal.config.WebConfig;
import com.snr.loginportal.dto.StatusProjection;
import com.snr.loginportal.model.EventManagement;
import com.snr.loginportal.model.StudentRegistration;
import com.snr.loginportal.repository.StudentRegistrationRepo;
import com.snr.loginportal.service.EventManagementService;
import com.snr.loginportal.service.StudentRegistrationService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentRegistrationServiceImpl implements StudentRegistrationService {
    private final Logger logger = LoggerFactory.getLogger(StudentRegistrationServiceImpl.class);

    @Autowired
    StudentRegistrationRepo studentRegistrationRepo;

    @Autowired
    EventManagementService eventManagementService;

    @Autowired
    WebConfig webConfig;

    @Override
    public void addStudentDetails(StudentRegistration studentRegistration) {
        EventManagement result1 = new EventManagement();
        logger.info("event : ---- : {}",eventManagementService.getEvent(studentRegistration.getEventName()));
            EventManagement result2 = eventManagementService.getEvent(studentRegistration.getEventName());
            result1.setId(result2.getId());
            result1.setCreatedAt(result2.getCreatedAt());
            result1.setEventName(result2.getEventName());
            result1.setMessage(result2.getMessage());
            result1.setImage(result2.getImage());
            result1.setEventCoordinator(result2.getEventCoordinator());
            studentRegistration.setEventId(result1);
            studentRegistration.setStatus("pending");
            logger.info("The Input saved data : ---- : {}",studentRegistration);
            studentRegistrationRepo.save(studentRegistration);
            logger.info("The Input saved data : ---- : {}",studentRegistrationRepo.save(studentRegistration));
    }

    @Override
    public boolean isARegisteredStudent(String eventName, String email) {
        logger.info("The Input values  : ---- : {}", email);
        logger.info("The Input  : ---- : {}",studentRegistrationRepo.isARegisteredStudent(email));
        return studentRegistrationRepo.isARegisteredStudent(email) == null;
    }

    @Override
    public Page<StudentRegistration> getStudentDetails(Map<String, String> requestParams, Pageable pageable) {
        Pageable resolvedPageable = webConfig.resolvePageable(requestParams, pageable);
        return studentRegistrationRepo.findAll(resolvedPageable);
    }

    @Override
    public boolean studentExists(Long id) {
        return studentRegistrationRepo.existsById(id);
    }

    @Transactional
    @Override
    public String updateStatus(Long id, String status) {
        studentRegistrationRepo.updateStatus(id, status);
        return "Updated successfully";
    }

    @Override
    public StatusProjection getStatus() {
      return studentRegistrationRepo.getStatus();
    }
}
