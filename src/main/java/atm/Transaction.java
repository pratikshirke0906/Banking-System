package atm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private int transactionId;
    private int accountId;
    private String type;
    private double amount;
    private String timestamp;

    public Transaction(int transactionId, int accountId, String type, double amount, String timestamp) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId + ", Type: " + type + ", Amount: " + amount + ", Date: " + timestamp;
    }

    public static List<Transaction> getTransactionsByAccountId(int accountId) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM transactions WHERE account_id = ?");
        ps.setInt(1, accountId);
        ResultSet rs = ps.executeQuery();

        List<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            transactions.add(new Transaction(
                rs.getInt("transaction_id"),
                rs.getInt("account_id"),
                rs.getString("type"),
                rs.getDouble("amount"),
                rs.getString("timestamp")
            ));
        }
        return transactions;
    }
}
