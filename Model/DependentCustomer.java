package Model;

import java.util.List;

public class DependentCustomer extends Customer {
    public DependentCustomer(String customerID, String fullName, InsuranceCard insuranceCard) {
        super(customerID, fullName, insuranceCard);
    }

    // View claim history
    public List<Claim> viewClaimHistory() {
        return getClaims();
    }
}
