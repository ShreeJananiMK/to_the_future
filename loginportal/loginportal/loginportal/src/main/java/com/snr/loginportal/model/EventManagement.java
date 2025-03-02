package com.snr.loginportal.model;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "event_management")
public class EventManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private long id;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "coordinator")
    private String eventCoordinator;

    @Column(name = "description")
    private String message;

    @Lob  // Use Large Object for big data
    @Column(name= "image_data",columnDefinition = "LONGTEXT")
    private String image;

    @Column(name = "created_at")
    private LocalDate createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventCoordinator() {
        return eventCoordinator;
    }

    public void setEventCoordinator(String eventCoordinator) {
        this.eventCoordinator = eventCoordinator;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "EventManagement{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", eventCoordinator='" + eventCoordinator + '\'' +
                ", message='" + message + '\'' +
                ", image='" + image + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
