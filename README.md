# Banking System

## Project Overview

This **Banking System** is a Java-based project that simulates basic banking operations. It allows users to create accounts, log in, view their account balance, deposit and withdraw money, transfer funds between accounts, and view their transaction history. An administrator can log in to manage user accounts and view all transactions.

The system is built using Java for backend logic, MySQL for data storage, and a graphical user interface (GUI) using **Swing**.

## Features

### User Features:
- **Account Creation**: Users can create new accounts with a minimum deposit amount.
- **Login**: Users can log in securely using their User ID and PIN.
- **Check Balance**: View current account balance and transaction history.
- **Deposit Funds**: Add money to the account.
- **Withdraw Funds**: Withdraw money from the account, ensuring sufficient balance.
- **Transfer Funds**: Transfer funds to another account.
- **Update Profile**: Update personal information (like name).
- **Transaction History**: View detailed transaction history including deposits, withdrawals, and transfers.

### Admin Features:
- **Admin Login**: Admins can log in with admin credentials.
- **View Users**: Admins can view all registered users.
- **View Transactions**: Admins can view all transactions made by users.

## Technologies Used

- **Java**: Backend logic and application structure.
- **MySQL**: Database for storing user and transaction data.
- **Swing**: GUI for user interaction.
- **JDBC**: Database connectivity with MySQL.

## Database Structure

This project uses a MySQL database with two primary tables: `users`, `accounts`, and `transactions`. Here's a simplified view:

1. **users**:
   - `user_id` (Primary Key)
   - `name`
   - `pin`

2. **accounts**:
   - `account_id` (Primary Key)
   - `user_id` (Foreign Key)
   - `balance`

3. **transactions**:
   - `transaction_id` (Primary Key)
   - `account_id` (Foreign Key)
   - `transaction_type` (Deposit, Withdraw, Transfer)
   - `amount`
   - `timestamp`


### Prerequisites
Before you start, make sure you have the following installed:
- Java (JDK 8 or higher)
- MySQL Server
- MySQL JDBC Driver (Connector/J)
- An IDE (such as IntelliJ IDEA or Eclipse) or any text editor
- Maven (if you're using a Maven project)


## Additional Improvements

- **Input Validation**: Ensures valid input for fields like User ID, PIN, deposit amount, etc.
- **Security**: PIN is stored securely (consider using hashing for PIN storage).
- **Admin Functions**: Enhanced admin features for user management.
- **Interest Calculation**: Automatically calculate interest on user balances at specific intervals.
