package controller;

import dao.CustomerDAO;
import banking.model.Customer;

public class LoginController {
    private CustomerDAO customerDAO = new CustomerDAO();

    public Customer authenticate(String customerId, String password) {
        Customer c = customerDAO.findById(customerId);
        if (c != null && c.login(password)) {
            return c;
        }
        return null;
    }
}
