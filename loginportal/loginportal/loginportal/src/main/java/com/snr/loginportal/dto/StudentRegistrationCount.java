package com.snr.loginportal.dto;

public class StudentRegistrationCount {

    private String eventName;

    private Long count;

    public StudentRegistrationCount(String eventName, Long count) {
        this.eventName = eventName;
        this.count = count;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "StudentRegistrationCount{" +
                "eventName='" + eventName + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}

