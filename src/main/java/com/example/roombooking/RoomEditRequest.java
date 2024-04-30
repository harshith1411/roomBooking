package com.example.roombooking;

public class RoomEditRequest {

    private int roomId;
    private String roomName;
    private int roomCapacity;

    public RoomEditRequest(int roomId, String roomName, int roomCapacity) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
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

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }
}
