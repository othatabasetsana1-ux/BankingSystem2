package banking.model;

/**
 * Investment account: deposits and withdrawals allowed.
 * Requires BWP 500 minimum opening deposit.
 * Pays 5% monthly interest.
 */
public class InvestmentAccount extends Account implements InterestBearable, Withdrawable {
    private static final double INTEREST_RATE = 0.05; // 5% monthly
    public static final double MIN_OPENING_DEPOSIT = 500.00;

    public InvestmentAccount(String accountNumber, Customer customer, double openingDeposit) {
        super(accountNumber, customer);
        if (openingDeposit < MIN_OPENING_DEPOSIT) {
            System.out.println("ERROR: Investment Account needs at least BWP " + MIN_OPENING_DEPOSIT);
            return;
        }
        deposit(openingDeposit, "Opening deposit");
    }

    @Override
    public String getAccountType() {
        return "INVESTMENT";
    }

    @Override
    public boolean withdraw(double amount) {
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

    @Override
    public double calculateInterest() {
        return getBalance() * INTEREST_RATE;
    }

    public void applyMonthlyInterest() {
        double interest = calculateInterest();
        if (interest > 0) {
            setBalance(getBalance() + interest);
            recordInterest(interest, "Monthly investment interest");
        }
    }
}
