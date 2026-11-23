package controller;

import dao.CustomerDAO;
import banking.model.*;
import java.time.LocalDate;

public class RegistrationController {
    private CustomerDAO customerDAO = new CustomerDAO();

    public boolean registerIndividual(String id, String firstName, String surname,
                                      String dob, String nationalId, String occupation,
                                      String employer, String employerAddress,
                                      String address, String phone, String email, String password) {
        LocalDate birthDate = (dob == null || dob.isEmpty())
                ? LocalDate.of(1990, 1, 1) : LocalDate.parse(dob);
        IndividualCustomer c = new IndividualCustomer(
                id, firstName, surname, birthDate, nationalId, occupation,
                employer, employerAddress, address, phone, email, password);
        return customerDAO.insertIndividual(c);
    }

    public boolean registerCompany(String id, String companyName, String regNo,
                                   String industry, String contactPerson, String taxNumber,
                                   String address, String phone, String email, String password) {
        CompanyCustomer c = new CompanyCustomer(
                id, companyName, regNo, industry, contactPerson, taxNumber,
                address, phone, email, password);
        return customerDAO.insertCompany(c);
    }
}