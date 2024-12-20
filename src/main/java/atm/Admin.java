package atm;

import java.sql.*;

public class Admin {
    private String adminId;
    private String password;

    public Admin(String adminId, String password) {
        this.adminId = adminId;
        this.password = password;
    }

    public static Admin authenticate(String adminId, String password) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM admins WHERE admin_id = ? AND password = ?");
        ps.setString(1, adminId);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Admin(rs.getString("admin_id"), rs.getString("password"));
        }
        return null;
    }

    public void viewUsers() throws SQLException {
        Connection con = DBConnection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        while (rs.next()) {
            System.out.println("User ID: " + rs.getString("user_id") + ", Name: " + rs.getString("name"));
        }
    }

    public void viewTransactions() throws SQLException {
        Connection con = DBConnection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM transactions");

        while (rs.next()) {
            System.out.println("Transaction ID: " + rs.getInt("transaction_id") + ", Account ID: " + rs.getInt("account_id") + ", Amount: " + rs.getDouble("amount"));
        }
    }
}
