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
        accountDAO.updateBalance(account);
        saveLastTransaction(account);
        return true;
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

    private void saveLastTransaction(Account account) {
        List<Transaction> txns = account.getTransactions();
        if (!txns.isEmpty()) {
            transactionDAO.insert(txns.get(txns.size() - 1), account.getAccountNumber());
        }
    }
}