package com.portfolioproject.app;
public class Mainthread 
{
    public static void main(String[] args) 
    {
       PriceUpdateTask task1 = new PriceUpdateTask("AAPL");
        PriceUpdateTask task2 = new PriceUpdateTask("GOOG");
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        thread1.start();
        thread2.start();        
    }
}