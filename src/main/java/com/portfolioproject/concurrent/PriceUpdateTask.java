package com.portfolioproject.concurrent;

import com.portfolioproject.model.Stock;

public class PriceUpdateTask implements Runnable 
{

    private String stockSymbol;
    public PriceUpdateTas stockForUpdate)
    {
        this.stockSymbol = stockForUpdate;
    }
@Override
    public void run() 
    {
        System.out.println("Updating price for " + stockSymbol +
                " - Thread: " +Thread.currentThread().getName());

        System.out.println("Price updated for " + stockSymbol +
                " - Thread: " +Thread.currentThread().getName());
    }
}