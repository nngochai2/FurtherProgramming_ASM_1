package Controller;

import Model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminController implements Serializable {
    private static AdminController instance;
    private final CustomersController customersController = CustomersController.getInstance();
    private final PolicyHoldersController policyHoldersController = PolicyHoldersController.getInstance();
    private final DependentsController dependentsController = DependentsController.getInstance();
    private final InsuranceCardController insuranceCardController = InsuranceCardController.getInstance();
    private final ClaimsController claimsController = ClaimsController.getInstance();
    private List<Admin> adminList;
    private List<Customer> customers;
    private List<PolicyHolder> policyHolders;
    private List<Dependent> dependents;

    public AdminController() {
        this.adminList = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.policyHolders = new ArrayList<>();
        this.dependents = new ArrayList<>();
    }

    public static AdminController getInstance() {
        if (instance == null) {
            instance = new AdminController();
        }
        return instance;
    }

    // Add a policyholder
    public void addPolicyHolder(String fullName, InsuranceCard insuranceCard) {
        String customerID = customersController.generateUserID();
        PolicyHolder newPolicyHolder = new PolicyHolder(customerID, fullName, insuranceCard);
        policyHolders.add(newPolicyHolder);
    }

    // Remove a user
    public void removeUser(String customerID) {
        Optional<Customer> customerToRemove = customers.stream()
                .filter(customer -> customer.getCustomerID().equals(customerID))
                .findFirst();
        customerToRemove.ifPresent(customer -> {
            // If the target user is a policyholder, remove them as well as their dependents
            if (customer instanceof PolicyHolder) {
                policyHolders.remove(customer);
                List<Dependent> policyHolderDependents = ((PolicyHolder) customer).getDependents();
                dependents.removeAll(policyHolderDependents);
                customers.removeAll(policyHolderDependents);
            } else if (customer instanceof Dependent) {
                // Remove a dependent
                dependents.remove(customer);
                customers.remove(customer);
            }
        });

    }

    // Find a customer by ID
    public Customer findCustomer(String customerID) {
        return customers.stream()
                .filter(customer -> customer.getCustomerID().equals(customerID))
                .findFirst()
                .orElse(null);
    }

    // Get all customers

    public List<Customer> getAllCustomers() {
        return customers;
    }


    // Read all customers' data from the system

}
