package Controller;

import Model.InsuranceCard;
import Model.PolicyHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

public class PolicyHoldersController implements Serializable {
    private static PolicyHoldersController instance;
    private PolicyHolder currentPolicyHolder;
    private final ArrayList<PolicyHolder> policyHolders;
    public PolicyHoldersController() {
        policyHolders = new ArrayList<>();
    }

    public static PolicyHoldersController getInstance() {
        if (instance == null) {
            instance = new PolicyHoldersController();
        }
        return instance;
    }

    public PolicyHolder getCurrentPolicyHolder() {
        return currentPolicyHolder;
    }

    public void setCurrentPolicyHolder(PolicyHolder currentPolicyHolder) {
        this.currentPolicyHolder = currentPolicyHolder;
    }

    // Add a policyholder to the list
    public void addPolicyHolder(PolicyHolder policyHolder) {
        policyHolders.add(policyHolder);
    }

    // Find a policyholder by name and ID
    public Optional<PolicyHolder> findPolicyHolder(String policyHolderID, String fullName) {
        return policyHolders.stream()
                .filter(policyHolderCustomer -> policyHolderCustomer.getCustomerID().equals(policyHolderID) && policyHolderCustomer.getFullName().equals(fullName))
                .findFirst();
    }

    // Get the insurance card of a policyholder by their ID
    public InsuranceCard getInsuranceCard(String policyHolderID, String fullName) {
        Optional<PolicyHolder> policyHolderCustomerOptional = findPolicyHolder(policyHolderID, fullName);
        return policyHolderCustomerOptional.map(PolicyHolder::getInsuranceCard).orElse(null);
    }
}
