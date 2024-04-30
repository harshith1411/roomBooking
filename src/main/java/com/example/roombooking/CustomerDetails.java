package com.example.roombooking;

public class CustomerDetails {

    private int id;
    private String name;
    private String email;

    public CustomerDetails(int id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

