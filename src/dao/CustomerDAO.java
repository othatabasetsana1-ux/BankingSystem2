package dao;

import banking.model.*;
import java.sql.*;
import java.time.LocalDate;

public class CustomerDAO {

    public boolean insertIndividual(IndividualCustomer c) {
        String sql1 = "INSERT INTO customer VALUES (?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO individual_customer VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement p1 = conn.prepareStatement(sql1)) {
                p1.setString(1, c.getCustomerId());
                p1.setString(2, "INDIVIDUAL");
                p1.setString(3, c.getAddress());
                p1.setString(4, c.getPhone());
                p1.setString(5, c.getEmail());
                p1.setString(6, c.getPasswordHash());
                p1.setDate(7, Date.valueOf(c.getDateRegistered()));
                p1.executeUpdate();
            }
            try (PreparedStatement p2 = conn.prepareStatement(sql2)) {
                p2.setString(1, c.getCustomerId());
                p2.setString(2, c.getFirstName());
                p2.setString(3, c.getSurname());
                p2.setDate(4, Date.valueOf(c.getDateOfBirth()));
                p2.setString(5, c.getNationalId());
                p2.setString(6, c.getOccupation());
                p2.setString(7, c.getEmployer());
                p2.setString(8, c.getEmployerAddress());
                p2.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Insert individual failed: " + e.getMessage());
            return false;
        }
    }

    public boolean insertCompany(CompanyCustomer c) {
        String sql1 = "INSERT INTO customer VALUES (?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO company_customer VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement p1 = conn.prepareStatement(sql1)) {
                p1.setString(1, c.getCustomerId());
                p1.setString(2, "COMPANY");
                p1.setString(3, c.getAddress());
                p1.setString(4, c.getPhone());
                p1.setString(5, c.getEmail());
                p1.setString(6, c.getPasswordHash());
                p1.setDate(7, Date.valueOf(c.getDateRegistered()));
                p1.executeUpdate();
            }
            try (PreparedStatement p2 = conn.prepareStatement(sql2)) {
                p2.setString(1, c.getCustomerId());
                p2.setString(2, c.getCompanyName());
                p2.setString(3, c.getCompanyRegistrationNo());
                p2.setString(4, c.getIndustry());
                p2.setString(5, c.getContactPerson());
                p2.setString(6, c.getTaxNumber());
                p2.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Insert company failed: " + e.getMessage());
            return false;
        }
    }

    public Customer findById(String customerId) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, customerId);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                String type = rs.getString("customer_type");
                if ("INDIVIDUAL".equals(type)) {
                    return loadIndividual(conn, customerId, rs);
                } else {
                    return loadCompany(conn, customerId, rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("FindById failed: " + e.getMessage());
        }
        return null;
    }

    private IndividualCustomer loadIndividual(Connection conn, String id, ResultSet rs) throws SQLException {
        String sql = "SELECT * FROM individual_customer WHERE customer_id = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, id);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                return new IndividualCustomer(
                        id, r.getString("first_name"), r.getString("surname"),
                        r.getDate("date_of_birth").toLocalDate(),
                        r.getString("national_id"), r.getString("occupation"),
                        r.getString("employer"), r.getString("employer_address"),
                        rs.getString("address"), rs.getString("phone"), rs.getString("email"),
                        rs.getString("password_hash"));
            }
        }
        return null;
    }

    private CompanyCustomer loadCompany(Connection conn, String id, ResultSet rs) throws SQLException {
        String sql = "SELECT * FROM company_customer WHERE customer_id = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, id);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                return new CompanyCustomer(
                        id, r.getString("company_name"), r.getString("company_registration_no"),
                        r.getString("industry"), r.getString("contact_person"), r.getString("tax_number"),
                        rs.getString("address"), rs.getString("phone"), rs.getString("email"),
                        rs.getString("password_hash"));
            }
        }
        return null;
    }
}
