package Controller;

import Model.InsuranceCard;
import Model.PolicyHolderCustomer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

public class ManagePolicyHolders implements Serializable {
    private static ManagePolicyHolders instance;
    private final ArrayList<PolicyHolderCustomer> policyHolderCustomers;
    public ManagePolicyHolders() {
        policyHolderCustomers = new ArrayList<>();
    }

    public static ManagePolicyHolders getInstance() {
        if (instance == null) {
            instance = new ManagePolicyHolders();
        }
        return instance;
    }

    // Add a policyholder to the list
    public void addPolicyHolder(PolicyHolderCustomer policyHolderCustomer) {
        policyHolderCustomers.add(policyHolderCustomer);
    }

    // Find a policyholder by name and ID
    public Optional<PolicyHolderCustomer> findPolicyHolder(String policyHolderID, String fullName) {
        return policyHolderCustomers.stream()
                .filter(policyHolderCustomer -> policyHolderCustomer.getCustomerID().equals(policyHolderID) && policyHolderCustomer.getFullName().equals(fullName))
                .findFirst();
    }

    // Get the insurance card of a policyholder by their ID
    public InsuranceCard getInsuranceCard(String policyHolderID, String fullName) {
        Optional<PolicyHolderCustomer> policyHolderCustomerOptional = findPolicyHolder(policyHolderID, fullName);
        return policyHolderCustomerOptional.map(PolicyHolderCustomer::getInsuranceCard).orElse(null);
    }
}
