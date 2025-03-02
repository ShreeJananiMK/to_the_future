package com.snr.loginportal.dto;

import java.util.Date;

public class CertificateDetails {

    private String studentName;

    private String eventName;

    private String  date;

    private String coordinatorName;

    private String collegeName;

    private String email;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String  getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCoordinatorName() {
        return coordinatorName;
    }

    public void setCoordinatorName(String coordinatorName) {
        this.coordinatorName = coordinatorName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CertificateDetails{" +
                "studentName='" + studentName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", date='" + date + '\'' +
                ", coordinatorName='" + coordinatorName + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
