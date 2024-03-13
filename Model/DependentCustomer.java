package Model;

import java.util.List;

public class DependentCustomer extends Customer {

    public DependentCustomer(String customerID, String fullName, InsuranceCard insuranceCard, List<Claim> claims) {
        super(customerID, fullName, insuranceCard, claims);
    }

}
