package Model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PolicyHolderCustomer extends Customer {
    private final List<DependentCustomer> dependentCustomers;
    public PolicyHolderCustomer(String customerID, String fullName, InsuranceCard insuranceCard, List<DependentCustomer> dependentCustomers) {
        super(customerID, fullName, insuranceCard);
        this.dependentCustomers = dependentCustomers;
    }

    // Policy holder can add dependent(s)
    public void addDependent(DependentCustomer dependentCustomer) {
        dependentCustomers.add(dependentCustomer);
    }

    public void removeDependent(DependentCustomer dependentCustomer) {
        dependentCustomers.remove(dependentCustomer);
    }

    public void updateDependentInfo(DependentCustomer dependentCustomer, String fullName, InsuranceCard insuranceCard) {
        dependentCustomer.setFullName(fullName);
        dependentCustomer.setInsuranceCard(insuranceCard);
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
}
