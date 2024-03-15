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

    public void setCurrentDependent(Dependent currentDependent) {
        this.currentDependent = currentDependent;
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

    // Allows policyholder to add a dependent
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

    // Checks if a dependent exists
    public boolean dependentExists(String dependentID) {
        for (Dependent dependent : getAllDependentCustomers()) {
            if (dependent.getCustomerID().equals(dependentID)) {
                return true;
            }
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

    // Read the data from the server
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

    public void createFileIfNotExists(String filePath) {
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

    public InsuranceCard getInsuranceCard(String dependentID, String fullName) {
        Optional<Dependent> dependentCustomerOptional = findDependent(dependentID, fullName);
        return dependentCustomerOptional.map(Dependent::getInsuranceCard).orElse(null);
    }

    public PolicyHolder getPolicyOwner(Dependent dependent) {
        return dependent.getPolicyHolder();
    }

//
//    public boolean validateDependentID(String dependentID) {
//        String regex = "p-[0-9a-zA-Z]+";
//
//    }
}
