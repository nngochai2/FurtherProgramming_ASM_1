package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

public class Dependent extends Customer implements Serializable {
    private PolicyHolder policyHolder;
    protected List<Claim> claims;
    public Dependent(String customerID, String fullName, InsuranceCard insuranceCard, PolicyHolder policyHolder) {
        super(customerID, fullName, insuranceCard);
        this.policyHolder = policyHolder;
        this.claims = new ArrayList<>();
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }

    @Override
    public List<Claim> getClaims() {
        return claims;
    }

    @Override
    public void setClaims(List<Claim> claims) {
        this.claims = claims;
    }

    @Override
    public String toString() {
        return getFullName() + "(" + getCustomerID() + ")";
    }
}
