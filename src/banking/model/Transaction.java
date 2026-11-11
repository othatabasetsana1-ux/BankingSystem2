package banking.model;

import java.time.LocalDateTime;

/**
 * An immutable record of a single deposit, withdrawal, or interest payment.
 */
public class Transaction {
    private String transactionId;
    private LocalDateTime date;
    private TransactionType type;
    private double amount;
    private String description;

    public Transaction(TransactionType type, double amount, String description) {
        this.transactionId = "T" + System.currentTimeMillis();
        this.date = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public String getTransactionId() { return transactionId; }
    public LocalDateTime getDate() { return date; }
    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }

    public String toString() {
        return date.toLocalDate() + " | " + type + " | BWP " + amount + " | " + description;
    }
}