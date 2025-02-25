package com.snr.loginportal.dto;

public interface StatusProjection {

    int getTotalParticipants();

    int getApprovedStatus();

    int getPendingStatus();

    int getRejectedStatus();
}
