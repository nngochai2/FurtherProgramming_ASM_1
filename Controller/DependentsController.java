package Controller;

import Model.Dependent;
import Model.InsuranceCard;
import Model.PolicyHolder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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

    public Dependent authenticateDependent(String userID, String fullName) {
        Dependent dependent = findDependent(userID, fullName);
        if (dependent != null) {
            currentDependent = dependent;
        }
        return dependent;
    }

    public Dependent getCurrentDependent() {
        return currentDependent;
    }

    public void setCurrentDependent(Dependent currentDependent) {
        this.currentDependent = currentDependent;
    }

    public List<Dependent> getAllDependentCustomers() {
        return dependents;
    }

    // Find a dependent by name and ID for login purpose
    public Dependent findDependent(String ID, String name) {
        for (Dependent dependent : dependents) {
            if (dependent.getCustomerID().equals(ID) && dependent.getFullName().equals(name)) {
                return dependent;
            }
        }
        return null;
    }

    public InsuranceCard getInsuranceCard(String dependentID, String fullName) {
        Dependent dependent = findDependent(dependentID, fullName);
        return dependent != null ? dependent.getInsuranceCard() : null;
    }

    public PolicyHolder getPolicyOwner(Dependent dependent) {
        return dependent.getPolicyHolder();
    }
}
