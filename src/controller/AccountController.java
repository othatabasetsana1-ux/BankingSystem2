package controller;

import dao.AccountDAO;
import dao.TransactionDAO;
import banking.model.*;
import java.util.List;

public class AccountController {
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public boolean openSavingsAccount(Customer customer, String accountNumber) {
        SavingsAccount acc = new SavingsAccount(accountNumber, customer);
        customer.addAccount(acc);
        return accountDAO.insert(acc);
    }

    public boolean openInvestmentAccount(Customer customer, String accountNumber, double openingDeposit) {
        if (openingDeposit < InvestmentAccount.MIN_OPENING_DEPOSIT) {
            return false;
        }
        InvestmentAccount acc = new InvestmentAccount(accountNumber, customer, openingDeposit);
        customer.addAccount(acc);
        return accountDAO.insert(acc);
    }

    public boolean openChequeAccount(Customer customer, String accountNumber,
                                     String employerName, String employerAddress) {
        ChequeAccount acc = new ChequeAccount(accountNumber, customer, employerName, employerAddress);
        customer.addAccount(acc);
        return accountDAO.insert(acc);
    }

    public boolean deposit(Account account, double amount) {
        if (amount <= 0) return false;
        account.deposit(amount);
        if (account.getBalance() >= amount) {
            accountDAO.updateBalance(account);
            saveLastTransaction(account);
            return true;
        }
        return false;
    }

    public boolean withdraw(Account account, double amount) {
        if (!account.withdraw(amount)) return false;
        accountDAO.updateBalance(account);
        saveLastTransaction(account);
        return true;
    }

    public void applyMonthlyInterest(SavingsAccount account) {
        account.applyMonthlyInterest();
        accountDAO.updateBalance(account);
        saveLastTransaction(account);
    }

    public void applyMonthlyInterest(InvestmentAccount account) {
        account.applyMonthlyInterest();
        accountDAO.updateBalance(account);
        saveLastTransaction(account);
    }

    // NEW: Account status methods
    public boolean freezeAccount(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            System.out.println("Cannot freeze: account is " + account.getStatus());
            return false;
        }
        account.setStatus(AccountStatus.FROZEN);
        return accountDAO.updateStatus(account);
    }

    public boolean unfreezeAccount(Account account) {
        if (account.getStatus() != AccountStatus.FROZEN) {
            System.out.println("Cannot unfreeze: account is " + account.getStatus());
            return false;
        }
        account.setStatus(AccountStatus.ACTIVE);
        return accountDAO.updateStatus(account);
    }

    public boolean lockAccount(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            System.out.println("Cannot lock: account is " + account.getStatus());
            return false;
        }
        account.setStatus(AccountStatus.LOCKED);
        return accountDAO.updateStatus(account);
    }

    public boolean unlockAccount(Account account) {
        if (account.getStatus() != AccountStatus.LOCKED) {
            System.out.println("Cannot unlock: account is " + account.getStatus());
            return false;
        }
        account.setStatus(AccountStatus.ACTIVE);
        return accountDAO.updateStatus(account);
    }

    public boolean closeAccount(Account account) {
        if (account.getStatus() == AccountStatus.CLOSED) {
            System.out.println("Account is already CLOSED.");
            return false;
        }
        if (account.getBalance() > 0) {
            System.out.println("Cannot close: account has balance BWP " + account.getBalance());
            return false;
        }
        account.setStatus(AccountStatus.CLOSED);
        return accountDAO.updateStatus(account);
    }

    private void saveLastTransaction(Account account) {
        List<Transaction> txns = account.getTransactions();
        if (!txns.isEmpty()) {
            transactionDAO.insert(txns.get(txns.size() - 1), account.getAccountNumber());
        }
    }
}