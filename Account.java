package courseProject;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Account {
    private String accountNumber;
    private String accountType;
    protected double serviceFee;
    protected double interestRate;
    protected double overdraftFee;
    protected double balance;
    private List<Transaction> transactions;

    // Constructor
    public Account(String accountNumber, String accountType, double serviceFee, double interestRate, double overdraftFee) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.serviceFee = serviceFee;
        this.interestRate = interestRate;
        this.overdraftFee = overdraftFee;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getOverdraftFee() {
        return overdraftFee;
    }

    public void setOverdraftFee(double overdraftFee) {
        this.overdraftFee = overdraftFee;
    }

    public double getBalance() {
        return balance;
    }
    
    public List<Transaction> getTransactions() {
        return transactions;
    }


    // Abstract methods for subclasses to implement
    public abstract void withdrawal(double amount);

    public abstract void deposit(double amount);

    // Method to add a transaction to the transaction list
    protected void addTransaction(String type, double amount, double fee) {
        balance += (type.equals("Deposit") || type.equals("Interest")) ? amount - fee : -amount - fee;
        Transaction transaction = new Transaction(LocalDateTime.now(), type, amount, fee, balance);
        transactions.add(transaction);

        // Debugging: Print out the transaction list size after adding
        System.out.println("Transaction added. Current transaction list size: " + transactions.size());
    }
    

    // Method to get transaction history as a String
    public String getTransactionHistory() {
        StringBuilder history = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (!transactions.isEmpty()) {
            for (Transaction transaction : transactions) {
                history.append(transaction.toString()).append("\n");
            }
        } else {
            history.append("No transactions found.");
        }
        return history.toString();
    }

    // Method to print transaction history (can be used for debugging or console output)
    public void printTransactionHistory(String customerId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf("Customer ID: %s | Account Number: %s | Account Type: %s%n",
                customerId, accountNumber, accountType); 
        System.out.println("Date                 Type           Amount         Fee             Balance");
        System.out.println("---------------------------------------------------------------------------");
        if (!transactions.isEmpty()) {
            for (Transaction transaction : transactions) {
                System.out.println(transaction.toString());
            }

            // Debugging: Print out a summary of the last transaction
            System.out.println("Last transaction: " + transactions.get(transactions.size() - 1));
        } else {
            System.out.println("No transactions found.");
        }
    }

    @Override
    public String toString() {
        return "\nAccount Information\n------------------- \n" +
                "Account Number: " + accountNumber + "\n" +
                "Account Type: " + accountType + "\n" +
                "Service Fee: $" + serviceFee + "\n" +
                "Interest Rate: " + interestRate + "%\n" +
                "Overdraft Fee: $" + overdraftFee + "\n" +
                "Balance: $" + balance;
    }
}
