package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PolicyHolder extends Customer implements Serializable {
    private List<Dependent> dependents;
    protected List<Claim> claims;
    public PolicyHolder(String customerID, String fullName, InsuranceCard insuranceCard) {
        super(customerID, fullName, insuranceCard);
        this.dependents = new ArrayList<>();
        this.claims = new ArrayList<>();
    }

    // Policy holder can add dependent(s)
    public void addDependent(Dependent dependent) {
        dependents.add(dependent);
    }

    public void removeDependent(Dependent dependent) {
        dependents.remove(dependent);
    }

    public List<Dependent> getDependents() {
        return dependents;
    }

    public void setDependents(List<Dependent> dependents) {
        this.dependents = dependents;
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
