package banking.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for any party that holds accounts at the bank.
 * Cannot be instantiated directly — use IndividualCustomer or CompanyCustomer.
 */
public abstract class Customer {
    private String customerId;
    private String address;
    private String phone;
    private String email;
    private String passwordHash;
    private LocalDate dateRegistered;
    private List<Account> accounts;

    public Customer(String customerId, String address, String phone,
                    String email, String passwordHash) {
        this.customerId = customerId;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.passwordHash = passwordHash;
        this.dateRegistered = LocalDate.now();
        this.accounts = new ArrayList<>();
    }

    public void register() {
        System.out.println("Customer " + customerId + " registered on " + dateRegistered);
    }

    public boolean login(String password) {
        return this.passwordHash.equals(password);
    }

    public List<Account> getAccounts() { return accounts; }
    public void addAccount(Account a) { accounts.add(a); }

    public String getCustomerId() { return customerId; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public LocalDate getDateRegistered() { return dateRegistered; }

    // ABSTRACT METHODS — subclasses MUST implement these
    public abstract String getFullName();
    public abstract boolean validateIdentity();

    public String toString() {
        return getFullName() + " [ID=" + customerId + "]";
    }
}
