package banking;

import controller.AccountController;
import controller.LoginController;
import controller.RegistrationController;
import dao.DatabaseConnection;
import banking.model.*;

public class IntegrationTest {
    public static void main(String[] args) {
        System.out.println("=== INTEGRATION TEST START ===\n");
        DatabaseConnection.getConnection();

        RegistrationController reg = new RegistrationController();
        boolean t1 = reg.registerIndividual("TEST001", "Test", "User",
                "1990-01-01", "123456789", "Engineer", "ABC", "Plot 1",
                "Plot 2", "70000000", "test@test.com", "pass123");
        System.out.println("TEST 1 - Register individual: " + (t1 ? "PASS" : "FAIL"));

        LoginController login = new LoginController();
        Customer c = login.authenticate("TEST001", "pass123");
        System.out.println("TEST 2 - Login correct credentials: " + (c != null ? "PASS" : "FAIL"));

        Customer c2 = login.authenticate("TEST001", "wrongpass");
        System.out.println("TEST 3 - Login wrong password (should fail): " + (c2 == null ? "PASS" : "FAIL"));

        AccountController acctCtrl = new AccountController();
        boolean t4 = acctCtrl.openSavingsAccount(c, "TEST-SAV-001");
        SavingsAccount sav = null;
        for (Account a : c.getAccounts()) {
            if (a instanceof SavingsAccount) { sav = (SavingsAccount) a; break; }
        }
        System.out.println("TEST 4 - Open savings account: " + (t4 && sav != null ? "PASS" : "FAIL"));

        boolean t5 = acctCtrl.deposit(sav, 1000.00);
        System.out.println("TEST 5 - Deposit BWP 1000: " + (t5 && sav.getBalance() == 1000.0 ? "PASS" : "FAIL"));

        boolean w6 = acctCtrl.withdraw(sav, 100.00);
        System.out.println("TEST 6 - Withdraw from savings (should fail): " + (!w6 ? "PASS" : "FAIL"));

        acctCtrl.applyMonthlyInterest(sav);
        double expected = 1000.0 + (1000.0 * 0.0005);
        System.out.println("TEST 7 - Apply savings interest: " + (Math.abs(sav.getBalance() - expected) < 0.01 ? "PASS" : "FAIL"));

        boolean t8 = acctCtrl.openInvestmentAccount(c, "TEST-INV-001", 500.00);
        InvestmentAccount inv = null;
        for (Account a : c.getAccounts()) {
            if (a instanceof InvestmentAccount) { inv = (InvestmentAccount) a; break; }
        }
        System.out.println("TEST 8 - Open investment BWP 500: " + (t8 && inv != null ? "PASS" : "FAIL"));

        boolean t9 = acctCtrl.withdraw(inv, 200.00);
        System.out.println("TEST 9 - Withdraw BWP 200 from investment: " + (t9 && inv.getBalance() == 300.0 ? "PASS" : "FAIL"));

        boolean t10 = sav.getTransactions().size() >= 2;
        System.out.println("TEST 10 - Transaction history recorded: " + (t10 ? "PASS" : "FAIL"));

        System.out.println("\n=== INTEGRATION TEST COMPLETE ===");
    }
}