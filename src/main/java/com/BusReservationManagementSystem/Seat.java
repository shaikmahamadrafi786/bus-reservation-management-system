package com.BusReservationManagementSystem;

public class Seat {
    private int seatNumber;
    private boolean available = true;
    public Seat() { }
    public Seat(int seatNumber) {
        if (seatNumber < 1) throw new IllegalArgumentException("Seat number must be positive");
        this.seatNumber = seatNumber;
    }
    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int value) { if (value < 1) throw new IllegalArgumentException("Seat number must be positive"); seatNumber = value; }
    public synchronized boolean isAvailable() { return available; }
    public synchronized void setAvailable(boolean value) { available = value; }
    public synchronized boolean reserve() {
        if (!available) return false;
        available = false;
        return true;
    }
    public synchronized void release() { available = true; }
    @Override public String toString() { return "Seat " + seatNumber + (isAvailable() ? " (available)" : " (reserved)"); }
}