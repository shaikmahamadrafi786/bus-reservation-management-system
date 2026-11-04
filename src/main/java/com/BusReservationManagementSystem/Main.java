package com.BusReservationManagementSystem;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** Console controller: owns input/output and delegates application work to DataManager/services. */
public class Main {
    private static final Path DATA_FILE = Path.of("bus-reservation-data.json");

    public static void main(String[] args) {
        DataManager data = new DataManager();
        ReservationService reservations = new ReservationService();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try (Scanner scanner = new Scanner(System.in)) {
            Thread startup = new Thread(() -> System.out.println("Reservation workers are ready."), "reservation-startup");
            startup.start();
            startup.join();
            boolean running = true;
            while (running) {
                showMenu();
                int choice = readInt(scanner, "Choice: ");
                switch (choice) {
                    case 1 -> showBuses(data);
                    case 2 -> searchRoute(scanner, data);
                    case 3 -> showTickets(data);
                    case 4 -> bookSeat(scanner, data, reservations, executor);
                    case 5 -> saveData(data);
                    case 6 -> loadData(data);
                    case 7 -> registerPassenger(scanner, data);
                    case 8 -> cancelTicket(scanner, data, reservations, executor);
                    case 9 -> searchPassenger(scanner, data);
                    case 0 -> { System.out.println("Goodbye!"); running = false; }
                    default -> System.out.println("Invalid choice. Select 0 to 9.");
                }

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Application interrupted.");
        } finally {
            executor.shutdown();
        }
    }

    private static void showMenu() {
        System.out.println("\n=== BUS RESERVATION MANAGEMENT SYSTEM ===");
        System.out.println("1. View buses\n2. Search route\n3. Show tickets\n4. Book a seat");
        System.out.println("5. Save data\n6. Load data\n7. Register passenger\n8. Cancel ticket\n9. Search passenger\n0. Exit");
    }

    private static void showBuses(DataManager data) {
        List<Bus> buses = data.getBuses();
        if (buses.isEmpty()) { System.out.println("No buses available."); return; }
        buses.forEach(System.out::println);
    }

    private static void searchRoute(Scanner scanner, DataManager data) {
        String source = readText(scanner, "Source: ");
        String destination = readText(scanner, "Destination: ");
        List<Bus> matches = data.searchRoute(source, destination);
        if (matches.isEmpty()) System.out.println("No buses found for that route.");
        else matches.forEach(System.out::println);
    }

    private static void showTickets(DataManager data) {
        List<Ticket> tickets = data.getTickets().stream()
                .sorted(Comparator.comparing(Ticket::getBookedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
        if (tickets.isEmpty()) { System.out.println("No tickets booked."); return; }
        tickets.forEach(System.out::println);
    }

    private static void bookSeat(Scanner scanner, DataManager data, ReservationService service, ExecutorService executor) {
        String passengerId = readText(scanner, "Passenger ID (register first): ");
        String busId = readText(scanner, "Bus ID: ");
        Callable<Ticket> task = service.reservationTask(data, passengerId, busId);
        Future<Ticket> result = executor.submit(task);
        try {
            System.out.println("Booking confirmed: " + result.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Booking interrupted; check ticket list before retrying.");
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            System.out.println("Booking failed: " + (cause == null ? e.getMessage() : cause.getMessage()));
        }
    }

    private static void registerPassenger(Scanner scanner, DataManager data) {
        String id = readText(scanner, "Passenger ID: ");
        String name = readText(scanner, "Name: ");
        String phone = readText(scanner, "Phone: ");
        try {
            Passenger passenger = new Passenger(name, phone, id);
            data.registerPassenger(passenger);
            System.out.println("Registered: " + passenger.displayDetails());
        } catch (IllegalArgumentException e) { System.out.println("Registration failed: " + e.getMessage()); }
    }

    private static void cancelTicket(Scanner scanner, DataManager data, ReservationService service, ExecutorService executor) {
        String ticketId = readText(scanner, "Ticket ID to cancel: ");
        Future<Boolean> result = executor.submit(service.cancellationTask(data, ticketId));
        try {
            if (result.get()) System.out.println("Ticket cancelled and seat released.");
            else System.out.println("Ticket not found.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Cancellation interrupted; check the ticket list before retrying.");
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            System.out.println("Cancellation failed: " + (cause == null ? e.getMessage() : cause.getMessage()));
        }
    }

    private static void searchPassenger(Scanner scanner, DataManager data) {
        String passengerId = readText(scanner, "Passenger ID: ");
        Passenger passenger = data.getPassenger(passengerId);
        if (passenger == null) System.out.println("Passenger not found.");
        else System.out.println(passenger.displayDetails());
    }

    private static void saveData(DataManager data) {
        try { data.saveData(DATA_FILE); System.out.println("Data saved to " + DATA_FILE.toAbsolutePath()); }
        catch (IOException e) { System.out.println("Could not save data: " + e.getMessage()); }
    }

    private static void loadData(DataManager data) {
        try { data.loadData(DATA_FILE); System.out.println("Data loaded from " + DATA_FILE.toAbsolutePath()); }
        catch (IOException e) { System.out.println("Could not load data: " + e.getMessage()); }
    }

    private static String readText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Please enter a value.");
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try { return Integer.parseInt(value); }
            catch (NumberFormatException e) { System.out.println("Enter a whole number."); }
        }
    }
}