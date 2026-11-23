package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/banking";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            initSchema(conn);
            return conn;
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }

    private static void initSchema(Connection conn) throws SQLException {
        String[] sqls = {
                "CREATE TABLE IF NOT EXISTS customer (customer_id VARCHAR(20) PRIMARY KEY, customer_type VARCHAR(20), address VARCHAR(200), phone VARCHAR(20), email VARCHAR(100), password_hash VARCHAR(100), date_registered DATE)",
                "CREATE TABLE IF NOT EXISTS individual_customer (customer_id VARCHAR(20) PRIMARY KEY, first_name VARCHAR(50), surname VARCHAR(50), date_of_birth DATE, national_id VARCHAR(20), occupation VARCHAR(50), employer VARCHAR(100), employer_address VARCHAR(200), FOREIGN KEY (customer_id) REFERENCES customer(customer_id))",
                "CREATE TABLE IF NOT EXISTS company_customer (customer_id VARCHAR(20) PRIMARY KEY, company_name VARCHAR(100), company_registration_no VARCHAR(50), industry VARCHAR(50), contact_person VARCHAR(50), tax_number VARCHAR(50), FOREIGN KEY (customer_id) REFERENCES customer(customer_id))",
                "CREATE TABLE IF NOT EXISTS account (account_number VARCHAR(20) PRIMARY KEY, account_type VARCHAR(20), balance DOUBLE, date_opened DATE, customer_id VARCHAR(20), FOREIGN KEY (customer_id) REFERENCES customer(customer_id))",
                "CREATE TABLE IF NOT EXISTS transaction_record (transaction_id VARCHAR(50) PRIMARY KEY, account_number VARCHAR(20), type VARCHAR(20), amount DOUBLE, description VARCHAR(200), date TIMESTAMP, FOREIGN KEY (account_number) REFERENCES account(account_number))"
        };
        try (Statement stmt = conn.createStatement()) {
            for (String sql : sqls) stmt.execute(sql);
        }
    }
}