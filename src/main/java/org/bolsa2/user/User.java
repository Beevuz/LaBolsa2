package org.bolsa2;

public class User {
    private String name;
    private double balance;

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void buyStock(Stock stock, double quantity) {
        if (balance >= stock.getPrice() * quantity) {
            balance -= stock.getPrice() * quantity;
            System.out.println("You have bought " + quantity + " shares of " + stock.getSymbol() + " stock.");
        } else {
            System.out.println("You do not have enough balance to buy " + quantity + " shares of " + stock.getSymbol() + " stock.");
        }
    }
}