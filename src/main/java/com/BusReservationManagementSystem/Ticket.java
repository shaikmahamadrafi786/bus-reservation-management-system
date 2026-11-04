package com.BusReservationManagementSystem;

import java.time.LocalDateTime;

public class Ticket {
    private String ticketId;
    private Passenger passenger;
    private Bus bus;
    private int seatNumber;
    private double fare;
    private LocalDateTime bookedAt;
    public Ticket() { }
    public Ticket(String ticketId, Passenger passenger, Bus bus, int seatNumber, double fare, LocalDateTime bookedAt) {
        this.ticketId = Person.requireText(ticketId, "ticket ID");
        this.passenger = passenger;
        this.bus = bus;
        this.seatNumber = seatNumber;
        this.fare = fare;
        this.bookedAt = bookedAt;
    }
    public String getTicketId() { return ticketId; }
    public void setTicketId(String v) { ticketId = Person.requireText(v, "ticket ID"); }
    public Passenger getPassenger() { return passenger; }
    public void setPassenger(Passenger v) { passenger = v; }
    public Bus getBus() { return bus; }
    public void setBus(Bus v) { bus = v; }
    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int v) { seatNumber = v; }
    public double getFare() { return fare; }
    public void setFare(double v) { fare = v; }
    public LocalDateTime getBookedAt() { return bookedAt; }
    public void setBookedAt(LocalDateTime v) { bookedAt = v; }
    @Override public String toString() { return ticketId + " | " + passenger.getName() + " | " + bus.getBusNumber() + " | seat " + seatNumber + " | " + String.format("%.2f", fare) + " | " + bookedAt; }
}