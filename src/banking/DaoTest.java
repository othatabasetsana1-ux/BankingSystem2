package banking;

import dao.DatabaseConnection;
import java.sql.Connection;

public class DaoTest {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("SUCCESS: Connected to MySQL!");
            System.out.println("Tables created in 'banking' database.");
        } else {
            System.out.println("FAILED: Could not connect.");
        }
    }
}
