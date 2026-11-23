package dao;

import banking.model.*;
import java.sql.*;

public class TransactionDAO {

    public boolean insert(Transaction t, String accountNumber) {
        String sql = "INSERT INTO transaction_record VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, t.getTransactionId());
            p.setString(2, accountNumber);
            p.setString(3, t.getType().name());
            p.setDouble(4, t.getAmount());
            p.setString(5, t.getDescription());
            p.setTimestamp(6, Timestamp.valueOf(t.getDate()));
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Insert transaction failed: " + e.getMessage());
            return false;
        }
    }
}
