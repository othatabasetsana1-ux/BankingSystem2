package banking.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private String accountNumber;
    private double balance;
    private LocalDate dateOpened;
    private Customer customer;
    private List<Transaction> transactions;
    private AccountStatus status;

    public Account(String accountNumber, Customer customer) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = 0.0;
        this.dateOpened = LocalDate.now();
        this.transactions = new ArrayList<>();
        this.status = AccountStatus.ACTIVE;
    }

    public void deposit(double amount) {
        deposit(amount, "Deposit");
    }

    public void deposit(double amount, String description) {
        if (status == AccountStatus.CLOSED) {
            System.out.println("Cannot deposit: account is CLOSED.");
            return;
        }
        if (status == AccountStatus.FROZEN) {
            System.out.println("Cannot deposit: account is FROZEN.");
            return;
        }
        if (amount <= 0) {
            System.out.println("Deposit must be positive.");
            return;
        }
        this.balance += amount;
        transactions.add(new Transaction(TransactionType.DEPOSIT, amount, description));
    }

    public abstract boolean withdraw(double amount);

    protected boolean canWithdraw() {
        if (status == AccountStatus.CLOSED) {
            System.out.println("Cannot withdraw: account is CLOSED.");
            return false;
        }
        if (status == AccountStatus.FROZEN) {
            System.out.println("Cannot withdraw: account is FROZEN.");
            return false;
        }
        if (status == AccountStatus.LOCKED) {
            System.out.println("Cannot withdraw: account is LOCKED.");
            return false;
        }
        return true;
    }

    public double getBalance() { return balance; }
    protected void setBalance(double balance) { this.balance = balance; }
    public String getAccountNumber() { return accountNumber; }
    public LocalDate getDateOpened() { return dateOpened; }
    public Customer getCustomer() { return customer; }
    public List<Transaction> getTransactions() { return transactions; }
    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }

    protected void recordWithdrawal(double amount, String description) {
        transactions.add(new Transaction(TransactionType.WITHDRAWAL, amount, description));
    }
    protected void recordInterest(double amount, String description) {
        transactions.add(new Transaction(TransactionType.INTEREST, amount, description));
    }

    public abstract String getAccountType();

    public String toString() {
        return getAccountType() + " [" + accountNumber + "] Balance: BWP " + balance + " (" + status + ")";
    }
}