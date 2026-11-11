package banking;

import banking.model.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== BANKING SYSTEM CORE MODEL TEST ===\n");

        // 1. Create a Company (for employment verification)
        Company abc = new Company("C001", "ABC Holdings",
                "Plot 123, Francistown", "2400000", "hr@abc.bw");

        // 2. Register an individual customer
        IndividualCustomer lerato = new IndividualCustomer(
                "IND001", "Lerato", "Modise", LocalDate.of(1990, 5, 15),
                "123456789", "Accountant", "ABC Holdings",
                "Plot 123, Francistown",
                "Plot 45, Gaborone", "71000000", "lerato@email.com",
                "secret123");
        lerato.register();
        System.out.println("Name: " + lerato.getFullName());
        System.out.println("Identity valid? " + lerato.validateIdentity());
        System.out.println("Employed? " + lerato.isEmployed());

        // 3. Register a company customer
        CompanyCustomer abcCust = new CompanyCustomer(
                "CO001", "ABC Holdings (Pty) Ltd", "BW12345678",
                "Logistics", "David Kgosi", "TIN0012345",
                "Plot 123, Francistown", "24000000",
                "finance@abc.bw", "companyPass456");
        abcCust.register();
        System.out.println("\nCompany: " + abcCust.getFullName());
        System.out.println("Identity valid? " + abcCust.validateIdentity());

        // 4. Open a Savings account for Lerato
        SavingsAccount savings = new SavingsAccount("SAV-001", lerato);
        lerato.addAccount(savings);
        savings.deposit(1000.00, "Initial savings");
        System.out.println("\n" + savings);

        // Try to withdraw from savings — should fail
        System.out.println("Try withdraw 100 from savings: " + savings.withdraw(100.00));

        // Apply interest
        savings.applyMonthlyInterest();
        System.out.println("After 1 month interest: " + savings);

        // 5. Open Investment account
        InvestmentAccount invest = new InvestmentAccount("INV-001", lerato, 500.00);
        lerato.addAccount(invest);
        System.out.println("\n" + invest);

        // Withdraw from investment
        System.out.println("Withdraw 200 from investment: " + invest.withdraw(200.00));
        System.out.println(invest);

        // 6. Open Cheque account
        ChequeAccount cheque = new ChequeAccount("CHQ-001", lerato,
                "ABC Holdings", "Plot 123, Francistown");
        lerato.addAccount(cheque);

        // Verify employment
        System.out.println("\nEmployment verified? " + cheque.validateEmployment(abc));

        // Credit salary
        cheque.creditSalary(15000.00);
        System.out.println(cheque);

        // Withdraw from cheque
        System.out.println("Withdraw 3000 from cheque: " + cheque.withdraw(3000.00));
        System.out.println(cheque);

        // 7. Show transaction history
        System.out.println("\n--- Transaction history for CHQ-001 ---");
        for (Transaction t : cheque.getTransactions()) {
            System.out.println(t);
        }

        // 8. Show all accounts for Lerato
        System.out.println("\n--- All accounts for Lerato ---");
        for (Account a : lerato.getAccounts()) {
            System.out.println(a);
        }

        // 9. Login test
        System.out.println("\nLogin with correct password: " + lerato.login("secret123"));
        System.out.println("Login with wrong password: " + lerato.login("wrong"));

        System.out.println("\n=== TEST COMPLETE ===");
    }
}
