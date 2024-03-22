package Controller;

import Model.Dependent;
import Model.InsuranceCard;
import Model.PolicyHolder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PolicyHoldersController implements Serializable {
    private static PolicyHoldersController instance;
    private PolicyHolder currentPolicyHolder;
    private List<PolicyHolder> policyHolders;
    private List<Dependent> dependents;
    public PolicyHoldersController() {
        policyHolders = new ArrayList<>();
        dependents = new ArrayList<>();
    }

    public static PolicyHoldersController getInstance() {
        if (instance == null) {
            instance = new PolicyHoldersController();
        }
        return instance;
    }

    public List<PolicyHolder> getAllPolicyHolders() {
        return policyHolders;
    }

    public PolicyHolder authenticatePolicyHolder(String userID, String fullName) {
        PolicyHolder policyHolder = findPolicyHolder(userID, fullName);
        if (policyHolder != null) {
            currentPolicyHolder = policyHolder;
        }
        return policyHolder;
    }

    // Find a policyholder by name and ID for login purpose
    public PolicyHolder findPolicyHolder(String policyHolderID, String fullName) {
        for (PolicyHolder policyHolder : policyHolders) {
            if (policyHolder.getCustomerID().equals(policyHolderID) && policyHolder.getFullName().equals(fullName)) {
                return policyHolder;
            }
        }
        return null; // Return null if no policyholder is found
    }

    private void createFileIfNotExists(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + filePath);
                } else {
                    System.err.println("Error: Unable to create file " + filePath);
                }
            } catch (IOException e) {
                System.err.println("Error: Unable to create file " + filePath);
            }
        }
    }

    // Method to serialize the policyholders into the system
    public void serializePolicyHoldersToFile(String filePath) {
        createFileIfNotExists(filePath);
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ){
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Create parent directory
            objectOutputStream.writeObject(policyHolders);
            System.out.println("Policy holders have been saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error: Unable to save policy holders to " + filePath);
        }
    }

    // Method to read the policyholders' data from the system
    public void deserializePolicyHoldersFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream("data/policyholders.dat");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            Object importedObject = objectInputStream.readObject();

            if (importedObject instanceof ArrayList<?> importedData && !((ArrayList<?>) importedObject).isEmpty()) {

                if (importedData.get(0) instanceof PolicyHolder) {
                    policyHolders = (ArrayList<PolicyHolder>) importedData;
                    System.out.println("Policy holders have been deserialized and imported from data/policyholders.dat");
                    return;
                }
            }
            System.err.println("Error: Unexpected data format in the file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to get the current policyholder user
    public PolicyHolder getCurrentPolicyHolder() {
        return currentPolicyHolder;
    }

    public void setCurrentPolicyHolder(PolicyHolder currentPolicyHolder) {
        this.currentPolicyHolder = currentPolicyHolder;
    }

    public void clearCurrentPolicyHolder() {
        this.currentPolicyHolder = null;
    }

    // Add a policyholder to the list (might be usable if the application is upgraded)
    public void addPolicyHolder(PolicyHolder policyHolder) {
        policyHolders.add(policyHolder);
    }

    // Add a dependent to the list of dependents
    public void addDependent(Dependent dependent) {
        if (currentPolicyHolder != null) {
            currentPolicyHolder.addDependent(dependent);
            dependents.add(dependent);

            // Update the dependents in the system
            serializeDependentsToFile("data/dependents.dat");
        } else {
            System.err.println("Error: No current policy holder set.");
        }

    }

    // Allows policyholder to remove a dependent from the dependents list
    public boolean removeDependent(String dependentID) {
        if (currentPolicyHolder != null) {
            Optional<Dependent> dependentToRemove = dependents.stream()
                    .filter(dependent -> dependent.getCustomerID().equals(dependentID))
                    .findFirst();
            if (dependentToRemove.isPresent()) {
                currentPolicyHolder.removeDependent(dependentToRemove.get());
                dependents.remove(dependentToRemove.get());

                // Update the products in the system
                serializeDependentsToFile("data/dependents.dat");
                return true;
            }
        } else {
            System.err.println("Error: No current policy holder set.");
        }
        return false;
    }

    // Checks if a dependent exists
    public boolean dependentExists(String dependentID) {
        for (Dependent dependent : getAllDependents()) {
            if (dependent.getCustomerID().equals(dependentID)) {
                return true;
            }
        }
        return false;
    }

    // Serialize the dependents into the system
    public void serializeDependentsToFile(String filePath) {
        createFileIfNotExists(filePath);
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ){
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Create parent directory
            objectOutputStream.writeObject(dependents);
            System.out.println("Dependents have been saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error: Unable to save dependents to " + filePath);
        }
    }

    // Read the dependents' data from the system
    public void deserializeDependentsFromFile() {
        if (currentPolicyHolder != null) {
            try (FileInputStream fileInputStream = new FileInputStream("data/dependents.dat");
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

                Object importedObject = objectInputStream.readObject();

                if (importedObject instanceof ArrayList<?> importedData && !((ArrayList<?>) importedObject).isEmpty()) {

                    if (importedData.get(0) instanceof Dependent) {
                        dependents = ((ArrayList<Dependent>) importedData).stream()
                                .filter(dependent -> dependent.getPolicyHolder().equals(currentPolicyHolder))
                                .collect(Collectors.toList());
                        System.out.println("Dependents have been deserialized and imported from data/dependents.dat");
                        return;
                    }
                }
                System.err.println("Error: Unexpected data format in the file.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Error: No current policy holder set.");
        }

    }

    // Method to get a dependent by ID
    public Dependent getDependentByID(String dependentID) {
        Dependent dependent = null;
        for (Dependent d : dependents) {
            if (d.getCustomerID().equals(dependentID)) {
                dependent = d;
            }
        }
        return dependent;
    }

    // Allows policyholder to find a dependent by name
    public Optional<Dependent> getDependentByName(String dependentName) {
        for (Dependent dependent : dependents) {
            if (dependent.getFullName().equalsIgnoreCase(dependentName)) {
                return Optional.of(dependent);
            }
        }
        return Optional.empty();
    }

    public List<Dependent> getAllDependents() {
        return dependents;
    }

    public void setPolicyHolders(List<PolicyHolder> policyHolders) {
        this.policyHolders = policyHolders;
    }

    public void setDependents(PolicyHolder currentPolicyHolder, List<Dependent> dependents) {
        if (currentPolicyHolder != null) {
            currentPolicyHolder.setDependents(dependents);
        } else {
            System.err.println("Error: No current policy holder set ");
        }
    }

    // Get the insurance card of a policyholder by their ID
    public InsuranceCard getInsuranceCard(String policyHolderID, String fullName) {
        PolicyHolder policyHolder = findPolicyHolder(policyHolderID, fullName);
        return policyHolder != null ? policyHolder.getInsuranceCard() : null;
    }
}
