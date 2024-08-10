package org.bolsa2;

public class Stock {
    private String symbol;
    private double price;
    private int quantity;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
        this.quantity = 0;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}