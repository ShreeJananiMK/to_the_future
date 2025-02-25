package com.snr.loginportal.repository;

import com.snr.loginportal.dto.StatusProjection;
import com.snr.loginportal.model.StudentRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface StudentRegistrationRepo extends JpaRepository<StudentRegistration, Long> {
    @Query(value = "select event_name from student_info where email =?1",nativeQuery = true)
    String isARegisteredStudent(String email);

    @Modifying
    @Query(value = "update student_info set status = :status where id = :id", nativeQuery = true)
    void updateStatus(@Param("id") Long id, @Param("status") String status);

    @Query(value = "SELECT " +
            "COUNT(*) AS totalParticipants, " +
            "SUM(CASE WHEN status = 'approved' THEN 1 ELSE 0 END) AS approvedStatus, " +
            "SUM(CASE WHEN status = 'pending' THEN 1 ELSE 0 END) AS pendingStatus, " +
            "SUM(CASE WHEN status = 'rejected' THEN 1 ELSE 0 END) AS rejectedStatus " +
            "FROM student_info",
            nativeQuery = true)
    StatusProjection getStatus();

}
