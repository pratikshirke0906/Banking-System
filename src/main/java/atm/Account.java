package atm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    private int accountId;
    private String userId;
    private double balance;

    public Account(int accountId, String userId, double balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    // Fetch account details by user ID
    public static Account getAccountByUserId(String userId) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts WHERE user_id = ?");
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int accountId = rs.getInt("account_id");
            double balance = rs.getDouble("balance");
            return new Account(accountId, userId, balance);
        }
        return null;  // No account found
    }

    // Deposit method
    public void deposit(double amount) throws SQLException {
        this.balance += amount;
        updateBalance();
    }

    // Withdraw method
    public void withdraw(double amount) throws SQLException {
        if (this.balance >= amount) {
            this.balance -= amount;
            updateBalance();
        } else {
            throw new SQLException("Insufficient funds.");
        }
    }

    // Update balance in the database
    private void updateBalance() throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE accounts SET balance = ? WHERE account_id = ?");
        ps.setDouble(1, this.balance);
        ps.setInt(2, this.accountId);
        ps.executeUpdate();
    }

    // Transfer method
    public static void transfer(int senderAccountId, String recipientUserId, double amount) throws SQLException {
        Connection con = DBConnection.getConnection();
        
        // Fetch the recipient account
        PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts WHERE user_id = ?");
        ps.setString(1, recipientUserId);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            int recipientAccountId = rs.getInt("account_id");
            double recipientBalance = rs.getDouble("balance");
            
            // Withdraw amount from sender
            ps = con.prepareStatement("SELECT balance FROM accounts WHERE account_id = ?");
            ps.setInt(1, senderAccountId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                double senderBalance = rs.getDouble("balance");
                if (senderBalance >= amount) {
                    // Deduct from sender
                    ps = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_id = ?");
                    ps.setDouble(1, amount);
                    ps.setInt(2, senderAccountId);
                    ps.executeUpdate();
                    
                    // Add to recipient
                    ps = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_id = ?");
                    ps.setDouble(1, amount);
                    ps.setInt(2, recipientAccountId);
                    ps.executeUpdate();
                    
                    System.out.println("Transfer successful.");
                } else {
                    throw new SQLException("Insufficient funds for transfer.");
                }
            }
        } else {
            throw new SQLException("Recipient account not found.");
        }
    }
}
