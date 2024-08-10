package org.example;

import org.bolsa2.equity.Stock;
import org.bolsa2.market.Market;
import org.bolsa2.user.User;
import javax.swing.*;
import java.awt.*;

public class Main {
    private JFrame frame;
    private JTextField nameField;
    private JTextField balanceField;
    private JTextField symbolField;
    private JTextField quantityField;
    private JLabel balanceLabel;
    private JLabel messageLabel;
    private Market market;
    private User user;
    private JPanel panel;
    private JButton startButton;
    private JButton buyButton;
    private JButton checkBalanceButton;
    private JButton checkStockPriceButton;
    private JButton exitButton;
    private JButton backButton;
    private JComboBox<Object> stockComboBox;
    private JLabel stockCounterLabel;

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

        // ... rest of the code ...
    }
}