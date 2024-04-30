package com.example.roombooking;

import java.util.List;

public class RoomDetails {
    private int roomId;
    private String roomName;
    private int capacity;
    private List<Booked> bookings;

    // Constructor, getters, and setters


    public RoomDetails() {
    }

    public RoomDetails(int roomId, String roomName, int capacity, List<Booked> bookings) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.bookings = bookings;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Booked> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booked> bookings) {
        this.bookings = bookings;
    }
}
