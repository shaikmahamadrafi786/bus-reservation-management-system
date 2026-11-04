package com.BusReservationManagementSystem;

import java.time.LocalDateTime;

public class Route {
    private String routeId;
    private String source;
    private String destination;
    private LocalDateTime departure;
    public Route() { }
    public Route(String routeId, String source, String destination, LocalDateTime departure) {
        this.routeId = Person.requireText(routeId, "route ID");
        this.source = Person.requireText(source, "source");
        this.destination = Person.requireText(destination, "destination");
        this.departure = departure;
    }
    public String getRouteId() { return routeId; }
    public void setRouteId(String value) { routeId = Person.requireText(value, "route ID"); }
    public String getSource() { return source; }
    public void setSource(String value) { source = Person.requireText(value, "source"); }
    public String getDestination() { return destination; }
    public void setDestination(String value) { destination = Person.requireText(value, "destination"); }
    public LocalDateTime getDeparture() { return departure; }
    public void setDeparture(LocalDateTime value) { departure = value; }
    @Override public String toString() { return routeId + ": " + source + " -> " + destination + " (" + departure + ")"; }
}