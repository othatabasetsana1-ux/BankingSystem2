package banking.model;

/**
 * A registered company or organisation customer.
 * Extends Customer — inherits all common attributes and methods.
 */
public class CompanyCustomer extends Customer {
    private String companyName;
    private String companyRegistrationNo;
    private String industry;
    private String contactPerson;
    private String taxNumber;

    public CompanyCustomer(String customerId, String companyName, String companyRegistrationNo,
                           String industry, String contactPerson, String taxNumber,
                           String address, String phone, String email, String passwordHash) {
        super(customerId, address, phone, email, passwordHash);
        this.companyName = companyName;
        this.companyRegistrationNo = companyRegistrationNo;
        this.industry = industry;
        this.contactPerson = contactPerson;
        this.taxNumber = taxNumber;
    }

    @Override
    public String getFullName() {
        return companyName;
    }

    @Override
    public boolean validateIdentity() {
        return companyRegistrationNo != null && !companyRegistrationNo.trim().isEmpty();
    }

    public String getCompanyName() { return companyName; }
    public String getCompanyRegistrationNo() { return companyRegistrationNo; }
    public String getIndustry() { return industry; }
    public String getContactPerson() { return contactPerson; }
    public String getTaxNumber() { return taxNumber; }
}
