package Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PolicyHolder extends Customer implements Serializable {
    private final List<Dependent> dependents;
    public PolicyHolder(String customerID, String fullName, InsuranceCard insuranceCard, List<Dependent> dependents) {
        super(customerID, fullName, insuranceCard);
        this.dependents = dependents;
    }

    // Policy holder can add dependent(s)
    public void addDependent(Dependent dependent) {
        dependents.add(dependent);
    }

    public void removeDependent(Dependent dependent) {
        dependents.remove(dependent);
    }

    public void updateDependentInfo(Dependent dependent, String fullName, InsuranceCard insuranceCard) {
        dependent.setFullName(fullName);
        dependent.setInsuranceCard(insuranceCard);
    }

    // Policyholder can renew a policy
    public void renewPolicy(int years) {
        // Get current expiration date
        Date currentExpirationDate = getInsuranceCard().getExpirationDate();

        // Calculate the new expiration date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentExpirationDate);
        calendar.add(Calendar.YEAR, years);
        Date newExpirationDate = calendar.getTime();

        // Update the expiration date of the insurance card
        getInsuranceCard().setExpirationDate(newExpirationDate);
    }

    // Policyholder can also modify a policy
    public void modifyPolicy(InsuranceCard newInsuranceCard) {
        // Update the insurance card with the new details
        setInsuranceCard(newInsuranceCard);
    }

    // View claim history
    public List<Claim> viewClaimHistory() {
        return getClaims();
    }

    @Override
    public String toString() {
        return "Policy Owner: " + getFullName() + "(" + getCustomerID() + ")";
    }
}
