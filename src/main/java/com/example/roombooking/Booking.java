package com.example.roombooking;

import java.time.LocalDate;


public class Booking {
    private int userID;
    private int roomID;
    private String roomName;
    private LocalDate DateOfBooking;
    private String timeFrom;
    private String timeTo;
    private String purpose;

    public Booking(int userID, int roomID, String roomName, LocalDate bookingDate, String timeFrom, String timeTo, String purpose) {
        this.userID = userID;
        this.roomID = roomID;
        this.roomName = roomName;
        this.DateOfBooking = bookingDate;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.purpose = purpose;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public LocalDate getDateOfBooking() {
        return DateOfBooking;
    }

    public void setDateOfBooking(LocalDate dateOfBooking) {
        this.DateOfBooking = dateOfBooking;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
