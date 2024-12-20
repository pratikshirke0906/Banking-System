package atm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginScreen {
    private JFrame frame;
    private JTextField userIdField;
    private JPasswordField pinField;

    public LoginScreen() {
        frame = new JFrame("Banking System Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setBounds(10, 20, 80, 25);
        frame.add(userIdLabel);

        userIdField = new JTextField();
        userIdField.setBounds(100, 20, 165, 25);
        frame.add(userIdField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(10, 50, 80, 25);
        frame.add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(100, 50, 165, 25);
        frame.add(pinField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                String pin = new String(pinField.getPassword());
                try {
                    User user = User.authenticate(userId, pin);
                    if (user != null) {
                        new UserDashboard(user);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Authentication failed.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage());
                }
            }
        });

        frame.add(loginButton);

        frame.setVisible(true);
    }
}
