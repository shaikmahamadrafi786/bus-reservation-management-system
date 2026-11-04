 package com.portfolioproject.model;

public class Holding {

    private String holdingId;
    private Asset asset;
    private int quantity;

    // Default constructor - required for Jackson
    public Holding() {
    }

    // Parameterized constructor
    public Holding(String holdingId, Asset asset, int quantity) {
        this.holdingId = holdingId;
        this.asset = asset;
        this.quantity = quantity;
    }

    // Getters
    public String getHoldingId() {
        return holdingId;
    }

    public Asset getAsset() {
        return asset;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters - required for Jackson
    public void setHoldingId(String holdingId) {
        this.holdingId = holdingId;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Calculate total current value
    public double getCurrentValue() {
        return asset.calculateCurrentValue() * quantity;
    }

    @Override
    public String toString() {
        return "Holding{" +
                "Holding ID='" + holdingId + '\'' +
                ", Asset=" + asset +
                ", Quantity=" + quantity +
                ", Current Value=" + getCurrentValue() +
                '}';
    }
}