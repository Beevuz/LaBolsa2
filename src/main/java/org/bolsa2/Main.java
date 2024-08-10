package org.example;

// Import necessary classes
import org.bolsa2.equity.Stock;
import org.bolsa2.market.Market;
import org.bolsa2.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Define the main class
public class Main {
    // Define instance variables
    private JFrame frame; // The main window of the application
    private JTextField nameField; // The text field for the user's name
    private JTextField balanceField; // The text field for the user's initial balance
    private JTextField symbolField; // The text field for the stock symbol
    private JTextField quantityField; // The text field for the quantity of the stock to buy
    private JLabel balanceLabel; // The label to display the user's current balance
    private JLabel messageLabel; // The label to display any error messages
    private Market market; // The market where the user can buy and sell stocks
    private User user; // The user's account information
    private JPanel panel; // The panel to hold the GUI components
    private JButton startButton; // The button to start the application
    private JButton buyButton; // The button to buy a stock
    private JButton checkBalanceButton; // The button to check the user's balance
    private JButton checkStockPriceButton; // The button to check the price of a stock
    private JButton exitButton; // The button to exit the application
    private JButton backButton; // The button to go back to the main menu
    private JComboBox<Object> stockComboBox; // The combo box to select a stock to buy
    private JLabel stockCounterLabel; // The label to display the stock counter and price

    // Main method
    public static void main(String[] args) {
        // Create a new instance of the Main class and call the createAndShowGUI method
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main().createAndShowGUI();
            }
        });
    }

    // Method to create and show the GUI
    private void createAndShowGUI() {
        // Create a new Market instance and add two stocks to it
        market = new Market();
        market.addStock(new Stock("AAPL", 100.0));
        market.addStock(new Stock("GOOG", 500.0));

        // Create a new JFrame instance and set its default close operation
        frame = new JFrame("Stock Market Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size and layout of the frame
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Create a new JPanel instance and set its layout
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add labels and text fields to the panel to collect user input
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel balanceLabel1 = new JLabel("Initial Balance:");
        balanceField = new JTextField(20);
        panel.add(balanceLabel1);
        panel.add(balanceField);

        // Add a start button to the panel
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the user's name and initial balance from the text fields
                String name = nameField.getText();
                double balance = Double.parseDouble(balanceField.getText());

                // Create a new User instance and set its balance
                user = new User(name, balance);

                // Update the balance label
                balanceLabel = new JLabel("Your current balance is: " + user.getBalance());

                // Clear the panel and add new components to it
                panel.removeAll();
                panel.add(new JLabel("What would you like to do?"));
                panel.add(buyButton = new JButton("Buy stock"));
                buyButton.addActionListener(new BuyStockAction());
                panel.add(checkBalanceButton = new JButton("Check balance"));
                checkBalanceButton.addActionListener(new CheckBalanceAction());
                panel.add(exitButton = new JButton("Exit"));
                exitButton.addActionListener(new ExitAction());
                panel.add(stockCounterLabel = new JLabel("Stock Counter: 0, Price: $0.00")); // Add the stock counter label
                panel.revalidate();
                panel.repaint();

                // Update the frame
                frame.revalidate();
                frame.repaint();
            }
        });
        panel.add(startButton);

        // Add the panel to the frame
        frame.add(panel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to handle the "Buy stock" button click event
    private class BuyStockAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Clear the panel and add new components to it
            panel.removeAll();
            panel.add(new JLabel("Please select a stock to buy:"));
            stockComboBox = new JComboBox<>();
            for (Stock stock : market.getStocks()) {
                // Add each stock to the combo box with its symbol and price
                stockComboBox.addItem(stock.getSymbol() + " - $" + stock.getPrice());
            }
            panel.add(stockComboBox);
            panel.add(new JLabel("Please enter the quantity of the stock you would like to buy:"));
            quantityField = new JTextField(10);
            panel.add(quantityField);
            buyButton = new JButton("Buy");
            buyButton.addActionListener(new BuyAction());
            panel.add(buyButton);
            panel.add(backButton = new JButton("Back"));
            backButton.addActionListener(new BackAction());
            panel.revalidate();
            panel.repaint();

            // Update the frame
            frame.revalidate();
            frame.repaint();
        }
    }

    // Method to handle the "Buy" button click event
    private class BuyAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Get the selected stock and quantity from the combo box and text field
            String selectedItem = (String) stockComboBox.getSelectedItem();
            String[] parts = selectedItem.split(" ");
            String symbol = parts[0].trim().toUpperCase();
            String quantity = quantityField.getText();

            // Get the selected stock from the market
            Stock stock = market.getStock(symbol);

            // Check if the stock exists
            if (stock != null) {
                // If the stock exists, buy it and update the user's balance
                user.buyStock(stock, Double.parseDouble(quantity));
                balanceLabel = new JLabel("Your current balance is: " + user.getBalance());

                // Update the stock counter and price
                int stockCounter = market.getStockCounter(symbol);
                double stockPrice = stock.getPrice();
                stockCounterLabel.setText("Stock Counter: " + stockCounter + ", Price: $" + stockPrice);

                // Clear the panel and add new components to it
                panel.removeAll();
                panel.add(balanceLabel);
                panel.add(new JLabel("What would you like to do?"));
                panel.add(buyButton = new JButton("Buy stock"));
                buyButton.addActionListener(new BuyStockAction());
                panel.add(checkBalanceButton = new JButton("Check balance"));
                checkBalanceButton.addActionListener(new CheckBalanceAction());
                panel.add(exitButton = new JButton("Exit"));
                exitButton.addActionListener(new ExitAction());
                panel.add(stockCounterLabel);
                panel.revalidate();
                panel.repaint();

                // Update the frame
                frame.revalidate();
                frame.repaint();
            } else {
                // If the stock does not exist, show an error message
                messageLabel = new JLabel("Sorry, we do not have a stock with that symbol.");
                panel.removeAll();
                panel.add(messageLabel);
                panel.add(backButton = new JButton("Back"));
                backButton.addActionListener(new BackAction());
                panel.revalidate();
                panel.repaint();

                // Update the frame
                frame.revalidate();
                frame.repaint();
            }
        }
    }

    // Method to handle the "Back" button click event
    private class BackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Clear the panel and add new components to it
            panel.removeAll();
            panel.add(new JLabel("What would you like to do?"));
            panel.add(buyButton = new JButton("Buy stock"));
            buyButton.addActionListener(new BuyStockAction());
            panel.add(checkBalanceButton = new JButton("Check balance"));
            checkBalanceButton.addActionListener(new CheckBalanceAction());
            panel.add(exitButton = new JButton("Exit"));
            exitButton.addActionListener(new ExitAction());
            panel.revalidate();
            panel.repaint();

            // Update the frame
            frame.revalidate();
            frame.repaint();
        }
    }

    // Method to handle the "Check balance" button click event
    private class CheckBalanceAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Update the balance label
            balanceLabel = new JLabel("Your current balance is: " + user.getBalance());

            // Clear the panel and add new components to it
            panel.removeAll();
            panel.add(balanceLabel);
            panel.add(backButton = new JButton("Back"));
            backButton.addActionListener(new BackAction());
            panel.revalidate();
            panel.repaint();

            // Update the frame
            frame.revalidate();
            frame.repaint();
        }
    }
    // Method to handle the "Exit" button click event
    private class ExitAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Exit the application
            System.exit(0);
        }
    }
}