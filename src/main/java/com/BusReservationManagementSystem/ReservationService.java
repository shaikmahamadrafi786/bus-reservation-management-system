package com.BusReservationManagementSystem;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.Callable;

/** Executes reservation requests on worker threads; seat reservation is synchronized by Bus/Seat. */
public class ReservationService {
    public Callable<Ticket> reservationTask(DataManager data, String passengerId, String busId) {
        return () -> data.reserve(passengerId, busId);
    }
    public Callable<Boolean> cancellationTask(DataManager data, String ticketId) {
        return () -> data.cancel(ticketId);
    }

    public static String newTicketId() { return "T-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(); }
    static Ticket ticket(Passenger passenger, Bus bus, Seat seat) {
        return new Ticket(newTicketId(), passenger, bus, seat.getSeatNumber(), bus.getFare(), LocalDateTime.now());
    }
}