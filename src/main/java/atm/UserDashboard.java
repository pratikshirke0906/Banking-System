package atm;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class UserDashboard {
    private JFrame frame;
    private User user;

    public UserDashboard(User user) {
        this.user = user;
        frame = new JFrame("User Dashboard");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setBounds(10, 20, 150, 25);
        checkBalanceButton.addActionListener(e -> {
            try {
                Account account = Account.getAccountByUserId(user.getUserId());
                if (account != null) {
                    double balance = account.getBalance();
                    JOptionPane.showMessageDialog(frame, "Your balance is: " + balance);
                } else {
                    JOptionPane.showMessageDialog(frame, "Account not found.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        frame.add(checkBalanceButton);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(10, 60, 150, 25);
        depositButton.addActionListener(e -> {
            try {
                Account account = Account.getAccountByUserId(user.getUserId());
                if (account != null) {
                    String amountStr = JOptionPane.showInputDialog("Enter amount to deposit:");
                    double amount = Double.parseDouble(amountStr);
                    account.deposit(amount);
                    JOptionPane.showMessageDialog(frame, "Deposit successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Account not found.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        frame.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(10, 100, 150, 25);
        withdrawButton.addActionListener(e -> {
            try {
                Account account = Account.getAccountByUserId(user.getUserId());
                if (account != null) {
                    String amountStr = JOptionPane.showInputDialog("Enter amount to withdraw:");
                    double amount = Double.parseDouble(amountStr);
                    account.withdraw(amount);
                    JOptionPane.showMessageDialog(frame, "Withdrawal successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Account not found.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        frame.add(withdrawButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(10, 140, 150, 25);
        transferButton.addActionListener(e -> {
            try {
                Account account = Account.getAccountByUserId(user.getUserId());
                if (account != null) {
                    String recipientUserId = JOptionPane.showInputDialog("Enter recipient User ID:");
                    String amountStr = JOptionPane.showInputDialog("Enter amount to transfer:");
                    double amount = Double.parseDouble(amountStr);
                    Account.transfer(account.getAccountId(), recipientUserId, amount);
                    JOptionPane.showMessageDialog(frame, "Transfer successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Account not found.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        frame.add(transferButton);

        JButton transactionHistoryButton = new JButton("Transaction History");
        transactionHistoryButton.setBounds(10, 180, 180, 25);
        transactionHistoryButton.addActionListener(e -> {
            try {
                Account account = Account.getAccountByUserId(user.getUserId());
                if (account != null) {
                    List<Transaction> transactions = Transaction.getTransactionsByAccountId(account.getAccountId());
                    StringBuilder history = new StringBuilder("Transaction History:\n");
                    for (Transaction transaction : transactions) {
                        history.append(transaction.toString()).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, history.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "Account not found.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        frame.add(transactionHistoryButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(10, 220, 150, 25);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginScreen(); // Return to login screen
        });
        frame.add(logoutButton);

        frame.setVisible(true);
    }
}
