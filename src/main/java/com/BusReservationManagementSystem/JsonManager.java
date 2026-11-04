package com.BusReservationManagementSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonManager {

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(SerializationFeature.INDENT_OUTPUT);

    public void save(Path file, DataSnapshot snapshot) throws IOException {
        Path absolutePath = file.toAbsolutePath();

        if (absolutePath.getParent() != null) {
            Files.createDirectories(absolutePath.getParent());
        }

        mapper.writeValue(absolutePath.toFile(), snapshot);
    }

    public DataSnapshot load(Path file) throws IOException {
        DataSnapshot snapshot =
                mapper.readValue(file.toFile(), DataSnapshot.class);

        if (snapshot == null) {
            return new DataSnapshot();
        }

        return snapshot;
    }

    public static class DataSnapshot {

        private List<Bus> buses = new ArrayList<>();
        private List<Passenger> passengers = new ArrayList<>();
        private List<Ticket> tickets = new ArrayList<>();

        public DataSnapshot() {
        }

        public DataSnapshot(
                List<Bus> buses,
                List<Passenger> passengers,
                List<Ticket> tickets) {
            this.buses = buses;
            this.passengers = passengers;
            this.tickets = tickets;
        }

        public List<Bus> getBuses() {
            return buses;
        }

        public void setBuses(List<Bus> buses) {
            this.buses = (buses == null) ? new ArrayList<>() : buses;
        }

        public List<Passenger> getPassengers() {
            return passengers;
        }

        public void setPassengers(List<Passenger> passengers) {
            this.passengers =
                    (passengers == null) ? new ArrayList<>() : passengers;
        }

        public List<Ticket> getTickets() {
            return tickets;
        }

        public void setTickets(List<Ticket> tickets) {
            this.tickets =
                    (tickets == null) ? new ArrayList<>() : tickets;
        }
    }
}