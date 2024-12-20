package atm;

import java.sql.*;
import java.util.Scanner;

public class BankingSystem {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        BankingSystem bankingSystem = new BankingSystem();

        while (true) {
            System.out.println("Welcome to the Banking System.");
            System.out.println("1. User Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Create New Account");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    bankingSystem.login();
                    break;
                case 2:
                    bankingSystem.adminLogin();
                    break;
                case 3:
                    bankingSystem.createAccount();
                    break;
                case 4:
                    System.out.println("Thank you for using the Banking System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void login() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        User user = User.authenticate(userId, pin);

        if (user != null) {
            Account account = Account.getAccountByUserId(user.getUserId());
            if (account != null) {
                bankingMenu(account, user);
            } else {
                System.out.println("No account found for the user.");
            }
        } else {
            System.out.println("Authentication failed. Exiting.");
        }
    }

    public void adminLogin() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Admin ID: ");
        String adminId = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        Admin admin = Admin.authenticate(adminId, password);

        if (admin != null) {
            adminMenu(admin);
        } else {
            System.out.println("Admin authentication failed.");
        }
    }

    public void createAccount() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection con = DBConnection.getConnection();

        String userId;
        while (true) {
            System.out.print("Enter a new User ID: ");
            userId = scanner.nextLine();

            PreparedStatement checkUserId = con.prepareStatement("SELECT * FROM users WHERE user_id = ?");
            checkUserId.setString(1, userId);
            ResultSet rs = checkUserId.executeQuery();
            if (rs.next()) {
                System.out.println("User ID already exists. Please choose another one.");
            } else {
                break;
            }
        }

        System.out.print("Enter your Name: ");
        String name = scanner.nextLine();

        String pin;
        while (true) {
            System.out.print("Enter a new PIN (4 digits): ");
            pin = scanner.nextLine();
            if (pin.length() == 4 && pin.matches("\\d+")) {
                break;
            } else {
                System.out.println("Invalid PIN. Please enter a 4-digit PIN.");
            }
        }

        double initialDeposit;
        while (true) {
            System.out.print("Enter initial deposit amount (minimum $100): ");
            initialDeposit = scanner.nextDouble();
            if (initialDeposit >= 100) {
                break;
            } else {
                System.out.println("Invalid amount. Minimum deposit should be $100.");
            }
        }

        PreparedStatement ps = con.prepareStatement("INSERT INTO users (user_id, name, pin) VALUES (?, ?, ?)");
        ps.setString(1, userId);
        ps.setString(2, name);
        ps.setString(3, pin);
        ps.executeUpdate();

        ps = con.prepareStatement("INSERT INTO accounts (user_id, balance) VALUES (?, ?)");
        ps.setString(1, userId);
        ps.setDouble(2, initialDeposit);
        ps.executeUpdate();

        System.out.println("Account created successfully!");
    }

    public static void bankingMenu(Account account, User user) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Update Profile");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance(account);
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    System.out.println("Deposit successful. Your new balance is: " + account.getBalance());
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    System.out.println("Withdrawal successful. Your new balance is: " + account.getBalance());
                    break;
                case 4:
                    transferFunds(account);
                    break;
                case 5:
                    System.out.print("Enter new name: ");
                    String newName = scanner.next();
                    user.updateProfile(newName);
                    System.out.println("Profile updated successfully.");
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void checkBalance(Account account) throws SQLException {
        System.out.println("Your balance is: " + account.getBalance());
        System.out.println("Transaction History:");
        for (Transaction transaction : Transaction.getTransactionsByAccountId(account.getAccountId())) {
            System.out.println(transaction);  // This will now use the overridden toString() method
        }
    }

    public static void transferFunds(Account account) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter recipient User ID: ");
        String recipientUserId = scanner.nextLine();
        Account recipientAccount = Account.getAccountByUserId(recipientUserId);

        if (recipientAccount != null) {
            System.out.print("Enter amount to transfer: ");
            double transferAmount = scanner.nextDouble();
            account.withdraw(transferAmount);
            recipientAccount.deposit(transferAmount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Recipient account not found.");
        }
    }

    public static void adminMenu(Admin admin) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View Users");
            System.out.println("2. View Transactions");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    admin.viewUsers();
                    break;
                case 2:
                    admin.viewTransactions();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
