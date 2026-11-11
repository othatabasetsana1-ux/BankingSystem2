package banking.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all account types.
 */
public abstract class Account {
    private String accountNumber;
    private double balance;
    private LocalDate dateOpened;
    private Customer customer;
    private List<Transaction> transactions;

    public Account(String accountNumber, Customer customer) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = 0.0;
        this.dateOpened = LocalDate.now();
        this.transactions = new ArrayList<>();
    }

    // OVERLOADED deposit — version 1: just an amount
    public void deposit(double amount) {
        deposit(amount, "Deposit");
    }

    // OVERLOADED deposit — version 2: amount + description
    public void deposit(double amount, String description) {
        if (amount <= 0) {
            System.out.println("Deposit must be positive.");
            return;
        }
        this.balance += amount;
        transactions.add(new Transaction(TransactionType.DEPOSIT, amount, description));
    }

    // ABSTRACT — each subclass decides its own behaviour
    public abstract boolean withdraw(double amount);

    public double getBalance() { return balance; }
    protected void setBalance(double balance) { this.balance = balance; }
    public String getAccountNumber() { return accountNumber; }
    public LocalDate getDateOpened() { return dateOpened; }
    public Customer getCustomer() { return customer; }
    public List<Transaction> getTransactions() { return transactions; }

    protected void recordWithdrawal(double amount, String description) {
        transactions.add(new Transaction(TransactionType.WITHDRAWAL, amount, description));
    }
    protected void recordInterest(double amount, String description) {
        transactions.add(new Transaction(TransactionType.INTEREST, amount, description));
    }

    public abstract String getAccountType();

    public String toString() {
        return getAccountType() + " [" + accountNumber + "] Balance: BWP " + balance;
    }
}
