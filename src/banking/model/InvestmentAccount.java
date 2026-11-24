package banking.model;

public class InvestmentAccount extends Account implements InterestBearable, Withdrawable {
    private static final double INTEREST_RATE = 0.05;
    public static final double MIN_OPENING_DEPOSIT = 500.00;

    public InvestmentAccount(String accountNumber, Customer customer, double openingDeposit) {
        super(accountNumber, customer);
        if (openingDeposit < MIN_OPENING_DEPOSIT) {
            System.out.println("ERROR: Investment Account needs at least BWP " + MIN_OPENING_DEPOSIT);
            return;
        }
        deposit(openingDeposit, "Opening deposit");
    }

    public String getAccountType() { return "INVESTMENT"; }

    public boolean withdraw(double amount) {
        if (!canWithdraw()) return false;
        if (amount <= 0) {
            System.out.println("Withdrawal denied: amount must be positive.");
            return false;
        }
        if (amount > getBalance()) {
            System.out.println("Withdrawal denied: insufficient funds.");
            return false;
        }
        setBalance(getBalance() - amount);
        recordWithdrawal(amount, "Investment withdrawal");
        return true;
    }

    public double calculateInterest() { return getBalance() * INTEREST_RATE; }

    public void applyMonthlyInterest() {
        if (getStatus() == AccountStatus.CLOSED || getStatus() == AccountStatus.FROZEN) {
            System.out.println("Cannot apply interest: account is " + getStatus());
            return;
        }
        double interest = calculateInterest();
        if (interest > 0) {
            setBalance(getBalance() + interest);
            recordInterest(interest, "Monthly investment interest");
        }
    }
}