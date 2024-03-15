package Controller;

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
    public Optional<PolicyHolderCustomer> findPolicyHolder(String ID, String name) {
        return policyHolderCustomers.stream()
                .filter(policyHolderCustomer -> policyHolderCustomer.getCustomerID().equals(ID) && policyHolderCustomer.getFullName().equals(name))
                .findFirst();
    }
}
