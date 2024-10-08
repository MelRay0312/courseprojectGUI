package courseProject;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class CheckingAccount extends Account implements AccountInterface {
    public static final double SERVICE_FEE = 0.50;
    public static final double OVERDRAFT_FEE = 30.00;
    private Date transactionDate;
    private String transactionType;
    private double transactionAmount;

    private static final double INTEREST_RATE = 2.0; // 2% interest rate

    public CheckingAccount(String accountNumber, double balance) {
        super(accountNumber, "CHK", SERVICE_FEE, INTEREST_RATE, OVERDRAFT_FEE);
        this.balance = balance;
    }

    public CheckingAccount(String accountNumber, String accountType, double serviceFee, double interestRate, double overdraftFee) {
        super(accountNumber, accountType, serviceFee, interestRate, overdraftFee);
    }

    // Method to set transaction details
    public void setTransactionDetails(String date, String type, double amount) throws ParseException {
        SimpleDateFormat dateFormatWithTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormatWithoutTime = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.transactionDate = dateFormatWithTime.parse(date);
        } catch (ParseException e) {
            this.transactionDate = dateFormatWithoutTime.parse(date);
        }
        this.transactionType = type;
        this.transactionAmount = amount;
    }

    @Override
    public void withdrawal(double amount) {
        if (transactionType == null || !transactionType.equals("WTH")) {
            throw new IllegalStateException("Transaction type must be set to 'WTH' before withdrawing.");
        }
        if (balance - amount - SERVICE_FEE < 0) {
            balance -= (amount + SERVICE_FEE + OVERDRAFT_FEE);
            addTransaction("Overdraft", amount, SERVICE_FEE + OVERDRAFT_FEE);
            System.out.println("Overdraft fee applied.");
        } else {
            balance -= (amount + SERVICE_FEE);
            addTransaction("Withdraw", amount, SERVICE_FEE);
        }
    }

    @Override
    public void deposit(double amount) {
        if (transactionType == null || !transactionType.equals("DEP")) {
            throw new IllegalStateException("Transaction type must be set to 'DEP' before depositing.");
        }
        balance += (amount - SERVICE_FEE);
        addTransaction("Deposit", amount, SERVICE_FEE);
    }

    @Override
    public double balance() {
        return balance;
    }

    // Method to apply interest
    public void applyInterest() {
        double interest = balance * (INTEREST_RATE / 100); // Calculate 2% interest
        balance += interest; // Add interest to the balance

        // Set the transaction details to reflect the interest application
        try {
            setTransactionDetails(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), "Interest", interest);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the exception (in practice, log this error)
        }

        addTransaction("Interest", interest, 0.0);
        System.out.println("Interest applied to Checking Account.");
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return super.toString() +
                "\nTransaction Date: " + (transactionDate != null ? dateFormat.format(transactionDate) : "N/A") +
                "\nTransaction Type: " + (transactionType != null ? transactionType : "N/A") +
                "\nTransaction Amount: $" + transactionAmount;
    }
}
