package banking.model;

/**
 * Contract for accounts that earn monthly interest.
 * Implemented by SavingsAccount and InvestmentAccount.
 */
public interface InterestBearable {
    double calculateInterest();
}