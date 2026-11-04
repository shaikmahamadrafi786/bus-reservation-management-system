package com.portfolioproject.model;

public class MutualFund extends Asset {

    private double nav;

    // Default constructor - required for Jackson
    public MutualFund() {
        super();
    }

    // Parameterized constructor
    public MutualFund(String assetId,
                      String assetName,
                      double purchasePrice,
                      double nav) {

        super(assetId, assetName, purchasePrice);
        this.nav = nav;
    }

    // Method overriding
    @Override
    public double calculateCurrentValue() {
        return nav;
    }

    // Getter
    public double getNav() {
        return nav;
    }

    // Setter
    public void setNav(double nav) {
        this.nav = nav;
    }

    @Override
    public String toString() {
        return "MutualFund{" +
                "Asset ID='" + getAssetId() + '\'' +
                ", Asset Name='" + getAssetName() + '\'' +
                ", Purchase Price=" + getPurchasePrice() +
                ", NAV=" + nav +
                '}';
    }
}