package banking;

import controller.AccountController;
import controller.RegistrationController;
import dao.CustomerDAO;
import banking.model.*;

public class SampleData {
    public static void main(String[] args) {
        System.out.println("Inserting sample records...");
        RegistrationController reg = new RegistrationController();
        AccountController acct = new AccountController();
        CustomerDAO custDao = new CustomerDAO();

        // 3 individual customers
        reg.registerIndividual("IND001", "Lerato", "Modise", "1990-05-15", "123456789",
                "Accountant", "ABC Holdings", "Plot 123, Francistown",
                "Plot 45, Gaborone", "71000001", "lerato@email.com", "pass001");
        reg.registerIndividual("IND002", "Thabo", "Moeng", "1985-03-20", "987654321",
                "Manager", "ABC Holdings", "Plot 123, Francistown",
                "Plot 50, Gaborone", "71000002", "thabo@email.com", "pass002");
        reg.registerIndividual("IND003", "Grace", "Phiri", "1995-08-10", "111222333",
                "Nurse", "Bots Hosp", "Plot 78, Gaborone",
                "Plot 55, Gaborone", "71000003", "grace@email.com", "pass003");

        // 2 company customers
        reg.registerCompany("CO001", "ABC Holdings (Pty) Ltd", "BW12345678",
                "Logistics", "David Kgosi", "TIN0012345",
                "Plot 123, Francistown", "24000001", "finance@abc.bw", "copass001");
        reg.registerCompany("CO002", "Bots Construction Ltd", "BW87654321",
                "Construction", "John Smith", "TIN0098765",
                "Plot 200, Gaborone", "24000002", "info@botscon.bw", "copass002");

        // 8 accounts
        Customer c1 = custDao.findById("IND001");
        acct.openSavingsAccount(c1, "SAV-001");
        acct.openInvestmentAccount(c1, "INV-001", 500.00);
        acct.openChequeAccount(c1, "CHQ-001", "ABC Holdings", "Plot 123, Francistown");

        Customer c2 = custDao.findById("IND002");
        acct.openSavingsAccount(c2, "SAV-002");
        acct.openChequeAccount(c2, "CHQ-002", "ABC Holdings", "Plot 123, Francistown");

        Customer c3 = custDao.findById("CO001");
        acct.openInvestmentAccount(c3, "INV-002", 1000.00);
        acct.openChequeAccount(c3, "CHQ-003", "ABC Holdings (Pty) Ltd", "Plot 123, Francistown");

        Customer c4 = custDao.findById("CO002");
        acct.openChequeAccount(c4, "CHQ-004", "Bots Construction Ltd", "Plot 200, Gaborone");

        System.out.println("Done! 5 customers + 8 accounts = 13 records");
        System.out.println("Login: IND001 / pass001 or CO001 / copass001");
    }
}
