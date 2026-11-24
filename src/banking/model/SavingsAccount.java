package banking.model;

public class SavingsAccount extends Account implements InterestBearable {
    private static final double INTEREST_RATE = 0.0005;

    public SavingsAccount(String accountNumber, Customer customer) {
        super(accountNumber, customer);
    }

    public String getAccountType() { return "SAVINGS"; }

    public boolean withdraw(double amount) {
        if (!canWithdraw()) return false;
        System.out.println("Withdrawal denied: Savings Account does not allow withdrawals.");
        return false;
    }

    public double calculateInterest() {
        return getBalance() * INTEREST_RATE;
    }

    public void applyMonthlyInterest() {
        if (getStatus() == AccountStatus.CLOSED || getStatus() == AccountStatus.FROZEN) {
            System.out.println("Cannot apply interest: account is " + getStatus());
            return;
        }
        double interest = calculateInterest();
        if (interest > 0) {
            setBalance(getBalance() + interest);
            recordInterest(interest, "Monthly savings interest");
        }
    }
}