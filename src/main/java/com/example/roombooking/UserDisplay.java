package com.example.roombooking;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;

public class UserDisplay {
    private int UserId;

    public UserDisplay() {
    }

    public UserDisplay(int userId) {
        UserId = userId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
