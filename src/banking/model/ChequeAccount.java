package banking.model;

/**
 * Cheque account: for employed individual customers AND company customers.
 * Allows deposits and withdrawals of any amount.
 * No interest is paid on cheque accounts.
 */
public class ChequeAccount extends Account implements Withdrawable {
    private String employerName;
    private String employerAddress;
    private Company verifiedBy;

    public ChequeAccount(String accountNumber, Customer customer,
                         String employerName, String employerAddress) {
        super(accountNumber, customer);
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    @Override
    public String getAccountType() {
        return "CHEQUE";
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
        recordWithdrawal(amount, "Cheque withdrawal");
        return true;
    }

    public boolean creditSalary(double amount) {
        if (amount <= 0) return false;
        deposit(amount, "Salary credit");
        return true;
    }

    public boolean validateEmployment(Company c) {
        if (c == null) return false;
        boolean ok = c.verifyEmployment(getCustomer().getCustomerId(), employerName, employerAddress);
        if (ok) this.verifiedBy = c;
        return ok;
    }

    public String getEmployerName() { return employerName; }
    public String getEmployerAddress() { return employerAddress; }
    public Company getVerifiedBy() { return verifiedBy; }
}