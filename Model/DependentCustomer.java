package Model;

import java.util.List;

public class DependentCustomer extends Customer {

    public DependentCustomer(String customerID, String fullName, InsuranceCard insuranceCard, List<Claim> claims) {
        super(customerID, fullName, insuranceCard, claims);
    }

    // View claim history
    public List<Claim> viewClaimHistory() {
        return getClaims();
    }

    // Update personal information
    public void updatePersonalInfo(String fullName, InsuranceCard insuranceCard) {
        setInsuranceCard(insuranceCard);
        setFullName(fullName);
    }
}
