package com.example.roombooking;

public class AddRoom {
    private String roomName;
    private int roomCapacity;

    public AddRoom() {
    }

    public AddRoom(String roomName, int capacity) {
        this.roomName = roomName;
        this.roomCapacity = capacity;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }
}
