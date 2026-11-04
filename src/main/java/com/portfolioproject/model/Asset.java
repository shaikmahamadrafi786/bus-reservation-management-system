package com.portfolioproject.model;


public abstract class Asset {

    private String assetId;
    private String assetName;
    private double purchasePrice;

    // Default constructor - required for Jackson
    public Asset() {
    }

    // Parameterized constructor
    public Asset(String assetId, String assetName, double purchasePrice) {
        this.assetId = assetId;
        this.assetName = assetName;
        this.purchasePrice = purchasePrice;
    }

    // Getters
    public String getAssetId() {
        return assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    // Setters - required for Jackson
    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    // Abstract method
    public abstract double calculateCurrentValue();
  }