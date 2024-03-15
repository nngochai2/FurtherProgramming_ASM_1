package Controller;

import Model.DependentCustomer;
import Model.PolicyHolderCustomer;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class ManageDependents implements Serializable {
    private static ManageDependents instance;
    public ArrayList<DependentCustomer> dependentCustomers;
    public ManageDependents() {
        dependentCustomers = new ArrayList<>();
    }

    public static ManageDependents getInstance() {
        if (instance == null) {
            instance = new ManageDependents();
        }
        return instance;
    }

    public ArrayList<DependentCustomer> getAllDependentCustomers() {
        return dependentCustomers;
    }

    public Optional<DependentCustomer> findDependent(String ID, String name) {
        return dependentCustomers.stream()
                .filter(dependentCustomer -> dependentCustomer.getCustomerID().equals(ID) && dependentCustomer.getFullName().equals(name))
                .findFirst();
    }

    // Allows policyholder to find a dependent by ID
    public DependentCustomer getDependentByID(String dependentID) {
        DependentCustomer dependentCustomer = null;
        for (DependentCustomer d : dependentCustomers) {
            if (d.getCustomerID().equals(dependentID)) {
                dependentCustomer = d;
            }
        }
        return dependentCustomer;
    }

    // Allows policyholder to add a dependent
    public void addDependent(String dependentID) {
        dependentCustomers.add(getDependentByID(dependentID));
    }

    // Allows policyholder to remove a dependent
    public boolean removeDependent(String dependentID) {
        Optional<DependentCustomer> dependentToRemove = dependentCustomers.stream()
                .filter(dependentCustomer -> dependentCustomer.getCustomerID().equals(dependentID))
                .findFirst();
        if (dependentToRemove.isPresent()) {
            dependentCustomers.remove(dependentToRemove.get());
            // Update the products in the system
            serializeDependentsToFile("data/dependents.dat");
            return true;
        }
        return false;
    }

    // Checks if a dependent exists
    public boolean dependentExists(String dependentID) {
        for (DependentCustomer dependentCustomer : getAllDependentCustomers()) {
            if (dependentCustomer.getCustomerID().equals(dependentID)) {
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
            objectOutputStream.writeObject(dependentCustomers);
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

                if (importedData.get(0) instanceof DependentCustomer) {
                    dependentCustomers = (ArrayList<DependentCustomer>) importedData;
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

//
//    public boolean validateDependentID(String dependentID) {
//        String regex = "p-[0-9a-zA-Z]+";
//
//    }
}
