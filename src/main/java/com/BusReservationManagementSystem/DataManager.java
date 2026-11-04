package com.BusReservationManagementSystem;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** In-memory application data: maps support ID lookup, lists preserve display order, sets enforce unique passenger IDs. */
public class DataManager {
    private Map<String, Bus> buses = new HashMap<>();
    private Map<String, Passenger> passengers = new HashMap<>();
    private List<Ticket> tickets = new ArrayList<>();
    private Set<String> passengerIds = new HashSet<>();
    private final JsonManager jsonManager = new JsonManager();

    public DataManager() { seedBuses(); }
    private void seedBuses() {
        addBus(new Bus("B101", "KA-01-AB-1010", 4, new Route("R1", "Bengaluru", "Mysuru", LocalDateTime.now().plusDays(1)), 350));
        addBus(new Bus("B102", "KA-01-CD-2020", 4, new Route("R2", "Bengaluru", "Chennai", LocalDateTime.now().plusDays(1).plusHours(2)), 650));
        addBus(new Bus("B103", "KA-09-EF-3030", 3, new Route("R3", "Mysuru", "Bengaluru", LocalDateTime.now().plusDays(2)), 350));
    }
    private void addBus(Bus bus) { buses.put(bus.getBusId(), bus); }

    public synchronized void registerPassenger(Passenger passenger) {
        if (!passengerIds.add(passenger.getPassengerId())) throw new IllegalArgumentException("Passenger ID already exists");
        passengers.put(passenger.getPassengerId(), passenger);
    }
    public synchronized Passenger getPassenger(String id) { return passengers.get(id); }
    public synchronized Bus getBus(String id) { return buses.get(id); }
    public synchronized List<Bus> getBuses() {
        return buses.values().stream().sorted(Comparator.comparing(Bus::getBusNumber, String.CASE_INSENSITIVE_ORDER)).toList();
    }
    public synchronized List<Ticket> getTickets() { return List.copyOf(tickets); }
    public synchronized List<Passenger> getPassengers() { return List.copyOf(passengers.values()); }
    public synchronized List<Bus> searchRoute(String source, String destination) {
        String from = source.trim(), to = destination.trim();
        return getBuses().stream().filter(b -> b.getRoute() != null
                && b.getRoute().getSource().equalsIgnoreCase(from)
                && b.getRoute().getDestination().equalsIgnoreCase(to)).toList();
    }

    /** Synchronized booking ensures validation, seat allocation, and ticket creation happen as one operation. */
    public synchronized Ticket reserve(String passengerId, String busId) {
        Passenger passenger = passengers.get(passengerId);
        if (passenger == null) throw new IllegalArgumentException("Passenger not found. Register first.");
        Bus bus = buses.get(busId);
        if (bus == null) throw new IllegalArgumentException("Bus not found.");
        Seat seat = bus.reserveFirstAvailable();
        if (seat == null) throw new IllegalStateException("No seats are available on this bus.");
        Ticket ticket = ReservationService.ticket(passenger, bus, seat);
        tickets.add(ticket);
        return ticket;
    }

    public synchronized boolean cancel(String ticketId) {
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            if (ticket.getTicketId().equalsIgnoreCase(ticketId.trim())) {
                Bus bus = buses.get(ticket.getBus().getBusId());
                Seat seat = bus == null ? null : bus.findSeat(ticket.getSeatNumber());
                if (seat == null) throw new IllegalStateException("The ticket seat could not be found.");
                seat.release();
                tickets.remove(i);
                return true;
            }
        }
        return false;
    }

    public synchronized void saveData(Path file) throws IOException {
        jsonManager.save(file, new JsonManager.DataSnapshot(getBuses(), getPassengers(), getTickets()));
    }

    /** Load into temporary collections, validate relationships, then replace current state together. */
    public synchronized void loadData(Path file) throws IOException {
        JsonManager.DataSnapshot snapshot = jsonManager.load(file);
        Map<String, Bus> loadedBuses = new HashMap<>();
        for (Bus bus : snapshot.getBuses()) {
            if (bus.getBusId() == null || bus.getSeats() == null || loadedBuses.putIfAbsent(bus.getBusId(), bus) != null)
                throw new IOException("Saved data contains an invalid or duplicate bus.");
        }
        Map<String, Passenger> loadedPassengers = new HashMap<>();
        for (Passenger passenger : snapshot.getPassengers()) {
            if (passenger.getPassengerId() == null || loadedPassengers.putIfAbsent(passenger.getPassengerId(), passenger) != null)
                throw new IOException("Saved data contains an invalid or duplicate passenger.");
        }
        List<Ticket> loadedTickets = new ArrayList<>();
        Set<String> occupied = new HashSet<>();
        for (Ticket stored : snapshot.getTickets()) {
            String passengerId = stored.getPassenger() == null ? null : stored.getPassenger().getPassengerId();
            String busId = stored.getBus() == null ? null : stored.getBus().getBusId();
            Passenger passenger = loadedPassengers.get(passengerId);
            Bus bus = loadedBuses.get(busId);
            if (passenger == null || bus == null) throw new IOException("Saved ticket references missing passenger or bus.");
            Seat seat = bus.findSeat(stored.getSeatNumber());
            if (seat == null || !occupied.add(busId + ":" + stored.getSeatNumber()))
                throw new IOException("Saved tickets contain an invalid or duplicate seat.");
            seat.setAvailable(false);
            loadedTickets.add(new Ticket(stored.getTicketId(), passenger, bus, stored.getSeatNumber(), stored.getFare(), stored.getBookedAt()));
        }
        buses = loadedBuses;
        passengers = loadedPassengers;
        passengerIds = new HashSet<>(loadedPassengers.keySet());
        tickets = loadedTickets;
    }
}
