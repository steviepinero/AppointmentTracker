package com.spinero.company_appointment_tracker;

public class Customer {
    private int id; // Auto-generated
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int division; // First-level division
    private int country;

    // Getters and setters for each field
    //

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDivisionId() {
        return division;
    }

    public void setDivisionId(int division) {
        this.division = division;
    }

    public int getCountryId() {
        return country;
    }

    public void setCountryId(int country) {
        this.country = country;
    }
}

