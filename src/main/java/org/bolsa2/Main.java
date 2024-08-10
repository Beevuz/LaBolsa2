package org.bolsa2;


import org.bolsa2.equity.Stock;
import org.bolsa2.market.Market;
import org.bolsa2.user.User;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main {
    private JFrame frame;
    private JTextField nameField;
    private JTextField balanceField;
    private JTextField quantityField;
    private JLabel balanceLabel;
    private JLabel messageLabel;
    private Market market;
    private User user;
    private JPanel panel;
    private JButton startButton;
    private JButton buyButton;
    private JButton checkBalanceButton;
    private JButton checkInventoryButton;
    private JButton exitButton;
    private JButton backButton;
    private JComboBox<Object> stockComboBox;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main().createAndShowGUI();
            }
        });
    }


    private void createAndShowGUI() {
        market = new Market();
        market.addStock(new Stock("AAPL", 100.0));
        market.addStock(new Stock("GOOG", 500.0));


        frame = new JFrame("Stock Market Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());


        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        addNameField(panel);
        addBalanceField(panel);
        addStartButton(panel);


        frame.add(panel, BorderLayout.CENTER);


        frame.setVisible(true);
    }


    private void addNameField(JPanel panel) {
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        panel.add(nameLabel);
        panel.add(nameField);
    }


    private void addBalanceField(JPanel panel) {
        JLabel balanceLabel1 = new JLabel("Initial Balance:");
        balanceField = new JTextField(20);
        panel.add(balanceLabel1);
        panel.add(balanceField);
    }


    private void addStartButton(JPanel panel) {
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                double balance = Double.parseDouble(balanceField.getText());


                user = new User(name, balance);


                balanceLabel = new JLabel("Your current balance is: " + user.getBalance());


                addButtons(panel);
            }
        });
        panel.add(startButton);
    }


    private void addButtons(JPanel panel) {
        panel.removeAll();
        panel.add(new JLabel("What would you like to do?"));
        buyButton = new JButton("Buy stock");
        buyButton.addActionListener(new BuyStockAction());
        panel.add(buyButton);
        checkBalanceButton = new JButton("Check balance");
        checkBalanceButton.addActionListener(new CheckBalanceAction());
        panel.add(checkBalanceButton);
        checkInventoryButton = new JButton("Check inventory");
        checkInventoryButton.addActionListener(new CheckInventoryAction());
        panel.add(checkInventoryButton);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ExitAction());
        panel.add(exitButton);
        panel.revalidate();
        panel.repaint();


        frame.revalidate();
        frame.repaint();
    }


    private class BuyStockAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            panel.removeAll();
            panel.add(new JLabel("Please select a stock to buy:"));
            stockComboBox = new JComboBox<>();
            for (Stock stock : market.getStocks()) {
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


            frame.revalidate();
            frame.repaint();
        }
    }


    private class BuyAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedItem = (String) stockComboBox.getSelectedItem();
            String[] parts = selectedItem.split(" ");
            String symbol = parts[0].trim().toUpperCase();
            String quantity = quantityField.getText();


            Stock stock = market.getStock(symbol);


            if (stock != null) {
                user.buyStock(stock, (int) Double.parseDouble(quantity));
                balanceLabel = new JLabel("Your current balance is: " + user.getBalance());


                addButtons(panel);
            } else {
                messageLabel = new JLabel("Sorry, we do not have a stock with that symbol.");
                panel.removeAll();
                panel.add(messageLabel);
                panel.add(backButton = new JButton("Back"));
                backButton.addActionListener(new BackAction());
                panel.revalidate();
                panel.repaint();


                frame.revalidate();
                frame.repaint();
            }
        }
    }


    private class BackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            addButtons(panel);
        }
    }


    private class CheckBalanceAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            panel.removeAll();
            balanceLabel = new JLabel("Your current balance is: " + user.getBalance());
            panel.add(balanceLabel);
            panel.add(backButton = new JButton("Back"));
            backButton.addActionListener(new BackAction());
            panel.revalidate();
            panel.repaint();


            frame.revalidate();
            frame.repaint();
        }
    }


    private class CheckInventoryAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            panel.removeAll();
            panel.add(new JLabel("Your current inventory:"));
            for (Stock stock : user.getStocks()) {
                panel.add(new JLabel(stock.getSymbol() + ": " + user.getStockQuantity(stock)));
            }
            panel.add(backButton = new JButton("Back"));
            backButton.addActionListener(new BackAction());
            panel.revalidate();
            panel.repaint();


            frame.revalidate();
            frame.repaint();
        }
    }

//    private class RoundAction implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            round++;
//            roundLabel.setText("Round: " + round);
//
//
//            // Add an event that lowers or raises a stock's price
//            if (round % 2 == 0) {
//                // Lower the price of a random stock
//                Stock stock = market.getStocks().get((int) (Math.random() * market.getStocks().size()));
//                stock.setPrice(stock.getPrice() * 0.9);
//            } else {
//                // Raise the price of a random stock
//                Stock stock = market.getStocks().get((int) (Math.random() * market.getStocks().size()));
//                stock.setPrice(stock.getPrice() * 1.1);
//            }
//
//
//            // Update the prices of all stocks
//            for (Stock s : market.getStocks()) {
//                s.setPrice(s.getPrice());
//            }
//
//
//            // Update the GUI
//            panel.removeAll();
//            panel.add(new JLabel("The current prices are:"));
//            for (Stock stock : market.getStocks()) {
//                panel.add(new JLabel(stock.getSymbol() + ": $" + stock.getPrice()));
//            }
//            panel.add(backButton = new JButton("Back"));
//            backButton.addActionListener(new BackAction());
//            panel.revalidate();
//            panel.repaint();
//
//
//            frame.revalidate();
//            frame.repaint();
//        }
//    }
//



    private class ExitAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}

