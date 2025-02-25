package com.snr.loginportal.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "student_info")
public class StudentRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    private EventManagement eventId;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "college_name")
    private String collegeName;

    @Column(name = "department")
    private String department;

    @Column(name = "year")
    private int year;

    @Column(name = "email")
    private String email;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "status")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventManagement getEventId() {
        return eventId;
    }

    public void setEventId(EventManagement eventId) {
        this.eventId = eventId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StudentRegistration{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", studentName='" + studentName + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", department='" + department + '\'' +
                ", year=" + year +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", status='" + status + '\'' +
                '}';
    }
}
