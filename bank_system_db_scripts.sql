CREATE DATABASE BankDB;
USE BankDB;
DROP TABLE admins;
SELECT * FROM Transactions;
show tables;

-- Users Table
CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    pin VARCHAR(4)
);


-- Accounts Table
CREATE TABLE accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50),
    balance DOUBLE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Transactions Table
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT,
    transaction_type VARCHAR(50),
    amount DOUBLE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

-- Admins Table
CREATE TABLE admins (
    admin_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100)
);

-- Inserting into Users Table
INSERT INTO users (user_id, name, pin) VALUES ('user1', 'Bunty', '1111');
INSERT INTO users (user_id, name, pin) VALUES ('user2', 'Bablu', '2222');

-- Inserting into Accounts Table
INSERT INTO accounts (user_id, balance) VALUES ('user1', 1000.00);
INSERT INTO accounts (user_id, balance) VALUES ('user2', 1500.00);

-- Inserting into Transactions Table
INSERT INTO transactions (account_id, transaction_type, amount) VALUES (1, 'Deposit', 1000.00);
INSERT INTO transactions (account_id, transaction_type, amount) VALUES (2, 'Deposit', 1500.00);

-- Inserting into Admins Table
INSERT INTO admins (admin_id, password) VALUES ('admin1', 'pass123');
