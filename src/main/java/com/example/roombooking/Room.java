package com.example.roombooking;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roomId;

    private int capacity;
    private String roomName;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Booked> bookings = new ArrayList<>();

    public Room(int roomId, int capacity, String roomName, List<Booked> bookings) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.roomName = roomName;
        this.bookings = bookings;
    }

    public Room() {
    }

    public List<Booked> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booked> bookings) {
        this.bookings = bookings;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


}
