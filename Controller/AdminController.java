package Controller;

import Model.Admin;
import Model.Customer;
import Model.Dependent;
import Model.PolicyHolder;

import java.util.List;
import java.util.Optional;

public class AdminController {
    private static AdminController instance;
    private List<Admin> adminList;
    private List<Customer> customers;
    private List<PolicyHolder> policyHolders;
    private List<Dependent> dependents;

    public AdminController getInstance() {
        if (instance == null) {
            instance = new AdminController();
        }
        return instance;
    }

    public PolicyHolder createNewPolicyHolder() {

    }

    public void removeUser(String customerID) {
        Optional<Customer> customerToRemove = customers.stream()
                .filter(customer -> customer.getCustomerID().equals(customerID))
                .findFirst();
        customerToRemove.ifPresent(customer -> customers.remove(customer));
    }

    public Customer findCustomer(String customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerID().equals(customerID)) {
                return customer;
            }
        }
        return null; // Return null if no customer was found
    }
}
