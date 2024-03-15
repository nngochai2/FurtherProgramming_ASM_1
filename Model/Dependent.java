package Model;

import java.io.Serializable;
import java.util.List;

public class Dependent extends Customer implements Serializable {
    private PolicyHolder policyHolder;
    public Dependent(String customerID, String fullName, InsuranceCard insuranceCard, PolicyHolder policyHolder) {
        super(customerID, fullName, insuranceCard);
        this.policyHolder = policyHolder;
    }

    // View claim history
    public List<Claim> viewClaimHistory() {
        return getClaims();
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }
}
