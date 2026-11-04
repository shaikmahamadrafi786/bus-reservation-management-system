package com.BusReservationManagementSystem;

public abstract class Person {
    private String name;
    private String phone;

    protected Person() { }
    protected Person(String name, String phone) {
        this.name = requireText(name, "name");
        this.phone = requireText(phone, "phone");
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = requireText(name, "name"); }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = requireText(phone, "phone"); }
    public abstract String displayDetails();

    protected static String requireText(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " is required");
        return value.trim();
    }
}