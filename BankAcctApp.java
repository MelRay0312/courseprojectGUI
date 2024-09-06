package courseProject;

import javax.swing.SwingUtilities;
import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BankAcctApp extends JFrame {
    private JTextField customerIdField, ssnField, lastNameField, firstNameField, streetField, cityField, zipField, phoneField, accountNumberField, initialBalanceField, transactionAmountField;
    private JRadioButton checkingButton, savingsButton, depositButton, withdrawButton;
    private JComboBox<String> stateDropdown;
    private JButton addCustomerButton, displayDataButton, performTransactionButton, nextCustomerButton, clearButton, showBreakdownButton, applyInterestButton;
    private JLabel statusLabel;
    private JTextArea transactionDetailsArea;
    private int currentCustomerIndex = 0;

    private ArrayList<Customer> customers = new ArrayList<>();

    public BankAcctApp() {
        setTitle("Bank Account Application");
        setSize(800, 600); // Increased window size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Changed layout to BorderLayout

        // Top Panel for customer and account input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(12, 2)); // Adjusted grid size for more fields
        add(inputPanel, BorderLayout.NORTH);

        // Create GUI components
        inputPanel.add(new JLabel("Customer ID:"));
        customerIdField = new JTextField();
        inputPanel.add(customerIdField);

        inputPanel.add(new JLabel("SSN:"));
        ssnField = new JTextField();
        inputPanel.add(ssnField);

        inputPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        inputPanel.add(lastNameField);

        inputPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        inputPanel.add(firstNameField);

        inputPanel.add(new JLabel("Street:"));
        streetField = new JTextField();
        inputPanel.add(streetField);

        inputPanel.add(new JLabel("City:"));
        cityField = new JTextField();
        inputPanel.add(cityField);

        inputPanel.add(new JLabel("State:"));
        stateDropdown = new JComboBox<>(new String[]{"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", 
        	    "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", 
        	    "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", 
        	    "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"});
        inputPanel.add(stateDropdown);

        inputPanel.add(new JLabel("Zip:"));
        zipField = new JTextField();
        inputPanel.add(zipField);

        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Account Number:"));
        accountNumberField = new JTextField();
        inputPanel.add(accountNumberField);

        inputPanel.add(new JLabel("Initial Balance:"));
        initialBalanceField = new JTextField();
        inputPanel.add(initialBalanceField);

        // Radio Buttons for Account Type
        inputPanel.add(new JLabel("Account Type:"));
        JPanel accountTypePanel = new JPanel();
        checkingButton = new JRadioButton("Checking");
        savingsButton = new JRadioButton("Savings");
        ButtonGroup accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(checkingButton);
        accountTypeGroup.add(savingsButton);
        accountTypePanel.add(checkingButton);
        accountTypePanel.add(savingsButton);
        inputPanel.add(accountTypePanel);

        // Buttons for Adding Customer and Displaying Data
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 4)); // Adjusted layout for buttons
        add(buttonPanel, BorderLayout.CENTER);

        addCustomerButton = new JButton("Add New Customer and Account");
        buttonPanel.add(addCustomerButton);
        displayDataButton = new JButton("Display Customer and Account Data");
        buttonPanel.add(displayDataButton);

        // Transaction Fields and Buttons
        buttonPanel.add(new JLabel("Transaction Amount:"));
        transactionAmountField = new JTextField();
        buttonPanel.add(transactionAmountField);

        buttonPanel.add(new JLabel("Transaction Type:"));
        JPanel transactionTypePanel = new JPanel();
        depositButton = new JRadioButton("Deposit");
        withdrawButton = new JRadioButton("Withdraw");
        ButtonGroup transactionTypeGroup = new ButtonGroup();
        transactionTypeGroup.add(depositButton);
        transactionTypeGroup.add(withdrawButton);
        transactionTypePanel.add(depositButton);
        transactionTypePanel.add(withdrawButton);
        buttonPanel.add(transactionTypePanel);

        performTransactionButton = new JButton("Perform Transaction");
        buttonPanel.add(performTransactionButton);
        
        nextCustomerButton = new JButton("Next Customer");
        buttonPanel.add(nextCustomerButton);

        showBreakdownButton = new JButton("Show Full Breakdown");
        buttonPanel.add(showBreakdownButton);
        
     // Add "Apply Interest" Button
        applyInterestButton = new JButton("Apply Interest");
        buttonPanel.add(applyInterestButton);

        // Clear Button
        clearButton = new JButton("Clear");
        buttonPanel.add(clearButton);

        // Status Label
        statusLabel = new JLabel("");
        add(statusLabel, BorderLayout.SOUTH);

        // Transaction Details Area
        transactionDetailsArea = new JTextArea(15, 70); // Increased size for better display
        transactionDetailsArea.setEditable(false);
        transactionDetailsArea.setLineWrap(true);
        transactionDetailsArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(transactionDetailsArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.SOUTH);

        // Add Action Listeners
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddCustomer();
            }
        });

        displayDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDisplayData();
            }
        });

        performTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePerformTransaction();
            }
        });

        nextCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNextCustomer();
            }
        });

        showBreakdownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFullBreakdown();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        
     // Add action listener for Apply Interest Button
        applyInterestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyInterestToCurrentAccount();
            }
        });
    }
    
 // Method to apply interest to the current customer's account
    private void applyInterestToCurrentAccount() {
        if (customers.isEmpty()) {
            statusLabel.setText("Error: No customer available to apply interest.");
            return;
        }
        
        Customer customer = customers.get(currentCustomerIndex);
        Account account = customer.getAccount();

        if (account instanceof CheckingAccount) {
            ((CheckingAccount) account).applyInterest();
            statusLabel.setText("Interest applied to Checking Account. New Balance: " + account.getBalance());
        } else if (account instanceof SavingsAccount) {
            ((SavingsAccount) account).applyInterest();
            statusLabel.setText("Interest applied to Savings Account. New Balance: " + account.getBalance());
        }
    }
 // Validation methods for real-time validation
    private void validateCustomerId() {
        String customerId = customerIdField.getText();
        if (customerId.isEmpty() || customerId.length() != 5 || !customerId.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Customer ID must be exactly 5 numeric characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            customerIdField.requestFocus();
        }
    }

    private void validateSSN() {
        String ssn = ssnField.getText();
        if (ssn.isEmpty() || ssn.length() != 9 || !ssn.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "SSN must be exactly 9 numeric characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            ssnField.requestFocus();
        }
    }

    private void validateLastName() {
        String lastName = lastNameField.getText();
        if (lastName.isEmpty() || lastName.length() > 20) {
            JOptionPane.showMessageDialog(this, "Last Name must be non-empty and max 20 characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            lastNameField.requestFocus();
        }
    }

    private void validateFirstName() {
        String firstName = firstNameField.getText();
        if (firstName.isEmpty() || firstName.length() > 15) {
            JOptionPane.showMessageDialog(this, "First Name must be non-empty and max 15 characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            firstNameField.requestFocus();
        }
    }

    private void validateStreet() {
        String street = streetField.getText();
        if (street.isEmpty() || street.length() > 20) {
            JOptionPane.showMessageDialog(this, "Street must be non-empty and max 20 characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            streetField.requestFocus();
        }
    }

    private void validateCity() {
        String city = cityField.getText();
        if (city.isEmpty() || city.length() > 20) {
            JOptionPane.showMessageDialog(this, "City must be non-empty and max 20 characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            cityField.requestFocus();
        }
    }

    private void validateZip() {
        String zip = zipField.getText();
        if (zip.isEmpty() || zip.length() != 5 || !zip.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Zip must be exactly 5 numeric characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            zipField.requestFocus();
        }
    }

    private void validatePhone() {
        String phone = phoneField.getText();
        if (phone.isEmpty() || phone.length() != 10 || !phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 numeric characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            phoneField.requestFocus();
        }
    }

    private void validateInitialBalance() {
        try {
            double initialBalance = Double.parseDouble(initialBalanceField.getText());
            if (initialBalance < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Initial balance must be a valid positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            initialBalanceField.requestFocus();
        }
    }


    private void handleAddCustomer() {
        
        	// Validate Customer ID
            String customerId = customerIdField.getText();
            if (customerId.isEmpty() || customerId.length() != 5 || !customerId.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Customer ID must be non-empty and max 5 characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate SSN
            String ssn = ssnField.getText();
            if (ssn.isEmpty() || ssn.length() != 9 || !ssn.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "SSN must be exactly 9 numeric characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate Last Name
            String lastName = lastNameField.getText();
            if (lastName.isEmpty() || lastName.length() > 20) {
                JOptionPane.showMessageDialog(this, "Last Name must be non-empty and max 20 characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate First Name
            String firstName = firstNameField.getText();
            if (firstName.isEmpty() || firstName.length() > 15) {
                JOptionPane.showMessageDialog(this, "First Name must be non-empty and max 15 characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate Street
            String street = streetField.getText();
            if (street.isEmpty() || street.length() > 20) {
                JOptionPane.showMessageDialog(this, "Street must be non-empty and max 20 characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate City
            String city = cityField.getText();
            if (city.isEmpty() || city.length() > 20) {
                JOptionPane.showMessageDialog(this, "City must be non-empty and max 20 characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate State
            String state = (String) stateDropdown.getSelectedItem();
            if (state.isEmpty() || state.length() != 2) {
                JOptionPane.showMessageDialog(this, "State must be exactly 2 characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate Zip
            String zip = zipField.getText();
            if (zip.isEmpty() || zip.length() != 5 || !zip.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Zip must be exactly 5 numeric characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate Phone
            String phone = phoneField.getText();
            if (phone.isEmpty() || phone.length() != 10 || !phone.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 numeric characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
            

            String accountNumber = accountNumberField.getText();
            double initialBalance = Double.parseDouble(initialBalanceField.getText());
            String accountType = checkingButton.isSelected() ? "CHK" : "SAV";

            Customer customer = new Customer(customerId, ssn, lastName, firstName, street, city, state, zip, phone);
            Account account;

            if (accountType.equals("CHK")) {
                account = new CheckingAccount(accountNumber, initialBalance);
            } else {
                account = new SavingsAccount(accountNumber, initialBalance);
            }

            customer.setAccount(account);
            customers.add(customer);

            statusLabel.setText("Customer and Account created successfully!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input for initial balance. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void handleDisplayData() {
        if (customers.isEmpty()) {
            statusLabel.setText("Error: No customer available to display.");
            return;
        }

        Customer customer = customers.get(currentCustomerIndex);
        Account account = customer.getAccount();
        
        //Display account information
        transactionDetailsArea.setText(account.toString());
        
     // Fetch transaction history
        StringBuilder history = new StringBuilder("\nTransaction History:\n");
        history.append("-----------------------------\n");

        // Append each transaction
        for (Transaction transaction : account.getTransactions()) {
            history.append(transaction.toString()).append("\n");
        }
        
        //Display history in the textarea
        transactionDetailsArea.append(history.toString());
    
    }

    private void handlePerformTransaction() {
        try {
            if (customers.isEmpty()) {
                statusLabel.setText("Error: No customer available to perform a transaction.");
                return;
            }

            double transactionAmount = Double.parseDouble(transactionAmountField.getText());
            String transactionType = depositButton.isSelected() ? "DEP" : "WTH";

            Customer customer = customers.get(currentCustomerIndex);
            Account account = customer.getAccount();

            if (account instanceof CheckingAccount) {
                ((CheckingAccount) account).setTransactionDetails(LocalDate.now().toString(), transactionType, transactionAmount);
            } else if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).setTransactionDetails(LocalDate.now().toString(), transactionType, transactionAmount);
            }

            if (transactionType.equals("DEP")) {
                account.deposit(transactionAmount);
            } else if (transactionType.equals("WTH")) {
                account.withdrawal(transactionAmount);
            }

            transactionDetailsArea.setText(
                "Transaction successful!\n" +
                "New balance: $" + account.getBalance() + "\n" +
                account.toString()
            );
        } catch (Exception ex) {
            statusLabel.setText("Error: " + ex.getMessage());
        }
    }

    private void handleNextCustomer() {
        if (customers.isEmpty()) {
            statusLabel.setText("No customers available.");
            return;
        }

        currentCustomerIndex = (currentCustomerIndex + 1) % customers.size();
        handleDisplayData();
    }

    private void showFullBreakdown() {
        if (customers.isEmpty()) {
            statusLabel.setText("No customers available.");
            return;
        }

        Customer customer = customers.get(currentCustomerIndex);
        Account account = customer.getAccount();

        StringBuilder breakdown = new StringBuilder();
        breakdown.append("Customer Information:\n")
                .append(customer.toString())
                .append("\n")
                .append(account.toString())
                .append("\n\nTransaction History:\n");
        

        // Fetch and append each transaction
        for (Transaction transaction : account.getTransactions()) {
        	breakdown.append(transaction.toString()).append("\n");
        }
        
        account.printTransactionHistory(customer.getID());

     // Update the transaction details area to reflect the new balance
        transactionDetailsArea.setText(breakdown.toString());
    }

    private void clearForm() {
        customerIdField.setText("");
        ssnField.setText("");
        lastNameField.setText("");
        firstNameField.setText("");
        streetField.setText("");
        cityField.setText("");
        stateDropdown.setSelectedIndex(0);
        zipField.setText("");
        phoneField.setText("");
        accountNumberField.setText("");
        initialBalanceField.setText("");
        transactionAmountField.setText("");
        checkingButton.setSelected(true);
        depositButton.setSelected(true);
        statusLabel.setText("");
        transactionDetailsArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankAcctApp frame = new BankAcctApp();
            frame.setVisible(true);
        });
    }
}
