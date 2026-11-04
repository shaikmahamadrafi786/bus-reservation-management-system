package com.BusReservationManagementSystem;

public class Passenger extends Person {
    private String passengerId;
    public Passenger() { }
    public Passenger(String name, String phone, String passengerId) {
        super(name, phone);
        this.passengerId = requireText(passengerId, "passenger ID");
    }
    public String getPassengerId() { return passengerId; }
    public void setPassengerId(String passengerId) { this.passengerId = requireText(passengerId, "passenger ID"); }
    @Override public String displayDetails() { return passengerId + " | " + getName() + " | " + getPhone(); }
    @Override public String toString() { return displayDetails(); }
}