package atm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String userId;
    private String name;
    private String pin;

    public User(String userId, String name, String pin) {
        this.userId = userId;
        this.name = name;
        this.pin = pin;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    // Authenticate user based on userId and pin
    public static User authenticate(String userId, String pin) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE user_id = ? AND pin = ?");
        ps.setString(1, userId);
        ps.setString(2, pin);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String name = rs.getString("name");
            return new User(userId, name, pin);  // User authenticated successfully
        } else {
            return null;  // Authentication failed
        }
    }

    // Update profile
    public void updateProfile(String newName) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE users SET name = ? WHERE user_id = ?");
        ps.setString(1, newName);
        ps.setString(2, userId);
        ps.executeUpdate();
    }
}
