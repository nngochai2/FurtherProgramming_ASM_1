package Model;

import java.util.List;

public class PolicyHolderCustomer extends Customer {
    public PolicyHolderCustomer(String customerID, String fullName, InsuranceCard insuranceCard, List<Claim> claims) {
        super(customerID, fullName, insuranceCard, claims);
    }
}
