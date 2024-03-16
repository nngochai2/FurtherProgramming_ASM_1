package Controller;

import Model.Dependent;
import Model.InsuranceCard;
import Model.PolicyHolder;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class DependentsController implements Serializable {
    private static DependentsController instance;
    private Dependent currentDependent;
    public ArrayList<Dependent> dependents;
    public DependentsController() {
        dependents = new ArrayList<>();
    }

    public static DependentsController getInstance() {
        if (instance == null) {
            instance = new DependentsController();
        }
        return instance;
    }

    public Dependent getCurrentDependent() {
        return currentDependent;
    }

    public ArrayList<Dependent> getAllDependentCustomers() {
        return dependents;
    }

    public Optional<Dependent> findDependent(String ID, String name) {
        return dependents.stream()
                .filter(dependent -> dependent.getCustomerID().equals(ID) && dependent.getFullName().equals(name))
                .findFirst();
    }

    // Allows policyholder to find a dependent by ID
    public Dependent getDependentByID(String dependentID) {
        Dependent dependent = null;
        for (Dependent d : dependents) {
            if (d.getCustomerID().equals(dependentID)) {
                dependent = d;
            }
        }
        return dependent;
    }

    // Checks if a dependent exists
    public boolean dependentExists(String dependentID) {
        for (Dependent dependent : getAllDependentCustomers()) {
            if (dependent.getCustomerID().equals(dependentID)) {
                return true;
            }
        }
        return false;
    }

    public InsuranceCard getInsuranceCard(String dependentID, String fullName) {
        Optional<Dependent> dependentCustomerOptional = findDependent(dependentID, fullName);
        return dependentCustomerOptional.map(Dependent::getInsuranceCard).orElse(null);
    }

    public PolicyHolder getPolicyOwner(Dependent dependent) {
        return dependent.getPolicyHolder();
    }

}
