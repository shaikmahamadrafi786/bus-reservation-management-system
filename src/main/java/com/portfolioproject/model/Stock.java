package com.portfolioproject.model;

public class Stock extends Asset {

    private double currentPrice;

    // Default constructor - required for Jackson
    public Stock() {
        super();
    }

    // Parameterized constructor
    public Stock(String assetId,
                 String assetName,
                 double purchasePrice,
                 double currentPrice) {

        super(assetId, assetName, purchasePrice);
        this.currentPrice = currentPrice;
    }

    // Method overriding
    @Override
    public double calculateCurrentValue() {
        return currentPrice;
    }

    // Getter
    public double getCurrentPrice() {
        return currentPrice;
    }

    // Setter
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "Asset ID='" + getAssetId() + '\'' +
                ", Asset Name='" + getAssetName() + '\'' +
                ", Purchase Price=" + getPurchasePrice() +
                ", Current Price=" + currentPrice +
                '}';
    }
}
