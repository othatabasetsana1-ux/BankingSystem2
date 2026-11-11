package banking.model;

/**
 * Represents the employer organisation that verifies employment
 * for individual Cheque Account applicants.
 */
public class Company {
    private String companyId;
    private String name;
    private String address;
    private String contactPhone;
    private String contactEmail;

    public Company(String companyId, String name, String address,
                   String contactPhone, String contactEmail) {
        this.companyId = companyId;
        this.name = name;
        this.address = address;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
    }

    public boolean verifyEmployment(String customerId, String employerName, String employerAddress) {
        return this.name.equals(employerName) && this.address.equals(employerAddress);
    }

    public String getCompanyId() { return companyId; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getContactPhone() { return contactPhone; }
    public String getContactEmail() { return contactEmail; }

    public String toString() {
        return name + " (" + companyId + ")";
    }
}
