package banking.model;

/**
 * Savings account: deposits only, NO withdrawals.
 * Pays 0.05% monthly interest.
 */
public class SavingsAccount extends Account implements InterestBearable {
    private static final double INTEREST_RATE = 0.0005; // 0.05% monthly

    public SavingsAccount(String accountNumber, Customer customer) {
        super(accountNumber, customer);
    }

    @Override
    public String getAccountType() {
        return "SAVINGS";
    }

    // OVERRIDDEN — always denies withdrawal (FR-19)
    @Override
    public boolean withdraw(double amount) {
        System.out.println("Withdrawal denied: Savings Account does not allow withdrawals.");
        return false;
    }

    @Override
    public double calculateInterest() {
        return getBalance() * INTEREST_RATE;
    }

    public void applyMonthlyInterest() {
        double interest = calculateInterest();
        if (interest > 0) {
            setBalance(getBalance() + interest);
            recordInterest(interest, "Monthly savings interest");
        }
    }
}
