package com.example.roombooking;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;

@Entity
public class Booked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingID;

    private LocalDate dateOfBooking;
    private String timeFrom;
    private String timeTo;
    private String purpose;

    @Embedded
    private UserDisplay user;
//    private int userID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;

    public Booked() {
    }


    public Booked(int bookingID, LocalDate dateOfBooking, String timeFrom, String timeTo, String purpose, int userID, UserDisplay user, Room room) {
        this.bookingID = bookingID;
        this.dateOfBooking = dateOfBooking;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.purpose = purpose;
//        this.userID = userID;
        this.user = user;
        this.room = room;
    }



    public void setBookedRoom(Room room) {
        this.room = room;
        room.getBookings().add(this); // Add this booking to the room's list
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public LocalDate getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(LocalDate dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
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
//    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public UserDisplay getUser() {
        return user;
    }

    public void setUser(UserDisplay user) {
        this.user = user;
    }
}
