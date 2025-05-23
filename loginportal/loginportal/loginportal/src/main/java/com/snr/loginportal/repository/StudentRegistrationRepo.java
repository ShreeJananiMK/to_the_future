package com.snr.loginportal.repository;

import com.snr.loginportal.dto.RegistrationCountProjection;
import com.snr.loginportal.dto.StatusProjection;
import com.snr.loginportal.model.StudentRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
            "FROM student_info " +
            "WHERE event_name = ",
            nativeQuery = true)
    StatusProjection getStatus();

    @Query("SELECT s.eventName AS eventName, COUNT(s) AS count FROM StudentRegistration s GROUP BY s.eventName")
    List<RegistrationCountProjection> studentRegistrationCount();

    @Query(value = "SELECT COUNT(*) FROM student_info " +
            "WHERE event_name = ? ", nativeQuery = true)
    int checkStudentCount(String eventName);

    @Modifying
    @Query(value = "UPDATE student_info s SET s.event_id = null WHERE s.event_id = ?", nativeQuery = true)
    void updateEventID(Long eventId);

}
