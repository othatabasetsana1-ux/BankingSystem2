package banking.model;

/**
 * Contract for accounts that permit withdrawals.
 * Implemented by InvestmentAccount and ChequeAccount.
 */
public interface Withdrawable {
    boolean withdraw(double amount);
}