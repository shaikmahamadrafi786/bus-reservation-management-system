package com.portfolioproject.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String userid;
    private String name;
    private String email;

    private List<Holding> holdings;

 
    // Default constructor
    // Required by Jackson while reading JSON
    public User() {

        holdings = new ArrayList<>();
    }


    // Parameterized constructor
    public User(String userid, String name, String email) {

        this.userid = userid;
        this.name = name;
        this.email = email;

        holdings = new ArrayList<>();
    }


    // Add Holding
    public void addHolding(Holding holding) {

        holdings.add(holding);
    }


    // Display User
    public void display() {

        System.out.println("Userid: " + userid);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Number of Holdings: " + holdings.size());
    }


    // Display Holdings
    public void displayHoldings() {

        System.out.println("User ID: " + userid);
        System.out.println("User Name: " + name);

        if (holdings.isEmpty()) {

            System.out.println("No holdings available.");
            return;
        }

        System.out.println("Holdings:");

        for (Holding holding : holdings) {

            System.out.println(holding);
        }
    }


    // Getters and Setters

    public String getUserid() {

        return userid;
    }


    public void setUserid(String userid) {

        this.userid = userid;
    }


    public String getName() {

        return name;
    }


    public void setName(String name) {

        this.name = name;
    }


    public String getEmail() {

        return email;
    }


    public void setEmail(String email) {

        this.email = email;
    }


    public List<Holding> getHoldings() {

        return holdings;
    }


    public void setHoldings(List<Holding> holdings) {

        this.holdings = holdings;
    }
}