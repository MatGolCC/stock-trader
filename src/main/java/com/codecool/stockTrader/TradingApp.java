package com.codecool.stockTrader;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Provides a command line interface for stock trading.
 **/
public class TradingApp {
    private final Trader trader;
    private final Loggable logger;

    public TradingApp(Trader trader, FileLogger logger) {
        this.trader = trader;
        this.logger = logger;
    }

    public static void main(String[] args) {
        StockAPIService stockAPIService = new StockAPIService();
        FileLogger logger = new FileLogger();
        Trader trader = new Trader(stockAPIService, logger);
        TradingApp app = new TradingApp(trader, logger);
        app.start();
    }

    public void start() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter a stock symbol (for example aapl):");
        String symbol = keyboard.nextLine();
        System.out.println("Enter the maximum price you are willing to pay: ");
        double price;
        try {
            price = keyboard.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Enter a number");
            return;
        }

        try {
            boolean purchased = trader.buy(symbol, price);
            if (purchased) {
                logger.log("Purchased stock!");
            } else {
                logger.log("Couldn't buy the stock at that price.");
            }
        } catch (Exception e) {
            logger.log("There was an error while attempting to buy the stock: " + e.getMessage());
        }
    }
}
