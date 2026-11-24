package banking.model;

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

    public String getAccountType() { return "CHEQUE"; }

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
}