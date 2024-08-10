package org.bolsa2.market;

import org.bolsa2.equity.Stock;
import java.util.ArrayList;
import java.util.List;

public class Market {
    private List<Stock> stocks;

    public Market() {
        stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public Stock getStock(String symbol) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equals(symbol)) {
                return stock;
            }
        }
        return null;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public int getStockCounter(String symbol) {
        int stockCounter = 0;
        for (Stock stock : stocks) {
            if (stock.getSymbol().equals(symbol)) {
                stockCounter = stock.getQuantity();
            }
        }
        return stockCounter;
    }
}