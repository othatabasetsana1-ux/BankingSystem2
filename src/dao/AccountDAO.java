package dao;

import banking.model.*;
import java.sql.*;
import java.time.LocalDate;

public class AccountDAO {

    public boolean insert(Account a) {
        String sql = "INSERT INTO account VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, a.getAccountNumber());
            p.setString(2, a.getAccountType());
            p.setDouble(3, a.getBalance());
            p.setDate(4, Date.valueOf(a.getDateOpened()));
            p.setString(5, a.getCustomer().getCustomerId());
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Insert account failed: " + e.getMessage());
            return false;
        }
    }

    public boolean updateBalance(Account a) {
        String sql = "UPDATE account SET balance = ? WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setDouble(1, a.getBalance());
            p.setString(2, a.getAccountNumber());
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update balance failed: " + e.getMessage());
            return false;
        }
    }
}
