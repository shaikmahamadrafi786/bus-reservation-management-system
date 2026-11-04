package com.BusReservationManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class Bus {
    private String busId;
    private String busNumber;
    private int capacity;
    private Route route;
    private double fare;
    private List<Seat> seats = new ArrayList<>();
    public Bus() { }
    public Bus(String busId, String busNumber, int capacity, Route route, double fare) {
        this.busId = Person.requireText(busId, "bus ID");
        this.busNumber = Person.requireText(busNumber, "bus number");
        if (capacity < 1) throw new IllegalArgumentException("Capacity must be positive");
        if (!Double.isFinite(fare) || fare < 0) throw new IllegalArgumentException("Fare cannot be negative");
        this.capacity = capacity;
        this.route = route;
        this.fare = fare;
        for (int i = 1; i <= capacity; i++) seats.add(new Seat(i));
    }
    public String getBusId() { return busId; }
    public void setBusId(String v) { busId = Person.requireText(v, "bus ID"); }
    public String getBusNumber() { return busNumber; }
    public void setBusNumber(String v) { busNumber = Person.requireText(v, "bus number"); }
    public int getCapacity() { return capacity; }
    public void setCapacity(int v) { if (v < 1) throw new IllegalArgumentException("Capacity must be positive"); capacity = v; }
    public Route getRoute() { return route; }
    public void setRoute(Route v) { route = v; }
    public double getFare() { return fare; }
    public void setFare(double v) { if (!Double.isFinite(v) || v < 0) throw new IllegalArgumentException("Fare cannot be negative"); fare = v; }
    public List<Seat> getSeats() { return seats; }
    public void setSeats(List<Seat> v) { seats = v == null ? new ArrayList<>() : new ArrayList<>(v); }
    public synchronized Seat reserveFirstAvailable() {
        for (Seat seat : seats) if (seat.reserve()) return seat;
        return null;
    }
    public Seat findSeat(int number) { return seats.stream().filter(s -> s.getSeatNumber() == number).findFirst().orElse(null); }
    public long availableSeats() { return seats.stream().filter(Seat::isAvailable).count(); }
    @Override public String toString() { return busId + " | " + busNumber + " | " + route + " | seats " + availableSeats() + "/" + capacity + " | fare " + String.format("%.2f", fare); }
}