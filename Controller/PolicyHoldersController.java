package Controller;

import Model.Dependent;
import Model.InsuranceCard;
import Model.PolicyHolder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PolicyHoldersController implements Serializable {
    private static PolicyHoldersController instance;
    private PolicyHolder currentPolicyHolder;
    private List<PolicyHolder> policyHolders;
    private List<Dependent> dependents;
    public PolicyHoldersController() {
        policyHolders = new ArrayList<>();
        dependents = new ArrayList<>();
    }

    //
    public static PolicyHoldersController getInstance() {
        if (instance == null) {
            instance = new PolicyHoldersController();
        }
        return instance;
    }

    // Method to serialize the policyholders into the system
    public void serializePolicyHoldersToFile(String filePath) {
        createFileIfNotExists("data/policyholders.dat");
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ){
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Create parent directory
            objectOutputStream.writeObject(policyHolders);
            System.out.println("Products have been saved products to " + filePath);
        } catch (IOException e) {
            System.err.println("Error: Unable to save products to " + filePath);
        }
    }

    // Method to read the policyholders' data from the system
    public void deserializePolicyHoldersFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream("data/insuranceCards.dat");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            Object importedObject = objectInputStream.readObject();

            if (importedObject instanceof ArrayList<?> importedData && !((ArrayList<?>) importedObject).isEmpty()) {

                if (importedData.get(0) instanceof InsuranceCard) {
                    policyHolders = (ArrayList<PolicyHolder>) importedData;
                    System.out.println("Products have been deserialized and imported from data/insuranceCards.dat");
                    return;
                }
            }
            System.err.println("Error: Unexpected data format in the file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method for serialize function in case the targeted file path does not exist
    private void createFileIfNotExists(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + "data/policyholders.dat");
                } else {
                    System.err.println("Error: Unable to create file " + "data/policyholders.dat");
                }
            } catch (IOException e) {
                System.err.println("Error: Unable to create file " + "data/policyholders.dat");
            }
        }
    }

    // Method to
    public PolicyHolder getCurrentPolicyHolder() {
        return currentPolicyHolder;
    }

    public void setCurrentPolicyHolder(PolicyHolder currentPolicyHolder) {
        this.currentPolicyHolder = currentPolicyHolder;
    }

    // Add a policyholder to the list (might be usable if the application is upgraded)
    public void addPolicyHolder(PolicyHolder policyHolder) {
        policyHolders.add(policyHolder);
    }

    public void addDependent(String dependentID) {
        dependents.add(getDependentByID(dependentID));
    }

    // Allows policyholder to remove a dependent
    public boolean removeDependent(String dependentID) {
        Optional<Dependent> dependentToRemove = dependents.stream()
                .filter(dependent -> dependent.getCustomerID().equals(dependentID))
                .findFirst();
        if (dependentToRemove.isPresent()) {
            dependents.remove(dependentToRemove.get());
            // Update the products in the system
            serializeDependentsToFile("data/dependents.dat");
            return true;
        }
        return false;
    }

    // Serialize the dependents into the system
    public void serializeDependentsToFile(String filePath) {
        createFileIfNotExists("data/dependents.dat");
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ){
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Create parent directory
            objectOutputStream.writeObject(dependents);
            System.out.println("Products have been saved products to " + filePath);
        } catch (IOException e) {
            System.err.println("Error: Unable to save products to " + filePath);
        }
    }

    public void deserializeDependentsFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream("data/dependents.dat");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            Object importedObject = objectInputStream.readObject();

            if (importedObject instanceof ArrayList<?> importedData && !((ArrayList<?>) importedObject).isEmpty()) {

                if (importedData.get(0) instanceof Dependent) {
                    dependents = (ArrayList<Dependent>) importedData;
                    System.out.println("Products have been deserialized and imported from data/dependents.dat");
                    return;
                }
            }

            System.err.println("Error: Unexpected data format in the file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

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

    // Find a policyholder by name and ID for login purpose
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
