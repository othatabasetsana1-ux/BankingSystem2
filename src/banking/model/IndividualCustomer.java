package banking.model;

import java.time.LocalDate;

/**
 * A natural person customer (salaried employee, student, pensioner).
 * Extends Customer — inherits all common attributes and methods.
 */
public class IndividualCustomer extends Customer {
    private String firstName;
    private String surname;
    private LocalDate dateOfBirth;
    private String nationalId;
    private String occupation;
    private String employer;
    private String employerAddress;

    public IndividualCustomer(String customerId, String firstName, String surname,
                              LocalDate dateOfBirth, String nationalId, String occupation,
                              String employer, String employerAddress,
                              String address, String phone, String email, String passwordHash) {
        super(customerId, address, phone, email, passwordHash);
        this.firstName = firstName;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.nationalId = nationalId;
        this.occupation = occupation;
        this.employer = employer;
        this.employerAddress = employerAddress;
    }

    @Override
    public String getFullName() {
        return firstName + " " + surname;
    }

    @Override
    public boolean validateIdentity() {
        return nationalId != null && nationalId.length() == 9;
    }

    public boolean isEmployed() {
        return employer != null && !employer.trim().isEmpty();
    }

    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getNationalId() { return nationalId; }
    public String getOccupation() { return occupation; }
    public String getEmployer() { return employer; }
    public String getEmployerAddress() { return employerAddress; }
}