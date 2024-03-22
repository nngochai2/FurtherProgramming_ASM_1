package Controller;

import Model.*;
import View.AdminView;

import java.io.*;
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

    // Find an admin by his/her username and password
    public Admin findAdmin(String username, String password) {
        for (Admin admin : adminList) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null; // Return null if no policyholder is found
    }

    public Admin authenticateAdmin(String username, String password) {
        return findAdmin(username, password);
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

    public List<PolicyHolder> getPolicyHolders() {
        return policyHolders;
    }

    public List<Dependent> getDependents() {
        return dependents;
    }

    // Create a new file for admins' data
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

    // Serialize admins to file
    public void serializeAdminToFile(String filePath) {
        createFileIfNotExists(filePath);
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ){
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Create parent directory
            objectOutputStream.writeObject(adminList);
            System.out.println("Admin have been saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error: Unable to save admins to " + filePath);
        }
    }

    // Method to read the admins' data
    public void deserializeAdminsFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream("data/admins.dat");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            Object importedObject = objectInputStream.readObject();

            if (importedObject instanceof ArrayList<?> importedData && !((ArrayList<?>) importedObject).isEmpty()) {

                if (importedData.get(0) instanceof PolicyHolder) {
                    policyHolders = (ArrayList<PolicyHolder>) importedData;
                    System.out.println("Admins have been deserialized and imported from data/admins.dat");
                    return;
                }
            }
            System.err.println("Error: Unexpected data format in the file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Read all customers' data from the system

}
