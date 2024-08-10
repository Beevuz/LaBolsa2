package org.bolsa2.user;

import org.bolsa2.equity.Stock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Define the User class
public class User {
    // Define instance variables
    private String name;
    private double balance;
    private Map<Stock, Integer> stocks; // Map to store the user's stocks

    // Constructor
    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.stocks = new HashMap<>(); // Initialize the map to store the user's stocks
    }

    // Method to get the user's name
    public String getName() {
        return name;
    }

    // Method to get the user's balance
    public double getBalance() {
        return balance;
    }

    // Method to get the user's stocks
    public List<Stock> getStocks() {
        return new ArrayList<>(stocks.keySet()); // Return a list of the user's stocks
    }

    // Method to get the quantity of a stock held by the user
    public String getStockQuantity(Stock stock) {
        return stocks.getOrDefault(stock, 0) + " shares of " + stock.getSymbol();
    }

    // Method to buy a stock
    public void buyStock(Stock stock, int quantity) {
        // Check if the user has enough balance to buy the stock
        if (balance >= stock.getPrice() * quantity) {
            // Subtract the cost of the stock from the user's balance
            balance -= stock.getPrice() * quantity;
            // Add the stock to the user's inventory
            stocks.put(stock, quantity);
        } else {
            // Show an error message if the user does not have enough balance
            System.out.println("You do not have enough balance to buy this stock.");
        }
    }
}