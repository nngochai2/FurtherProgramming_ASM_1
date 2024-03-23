package Controller;

import Model.Dependent;
import Model.InsuranceCard;
import Model.PolicyHolder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DependentsController implements Serializable {
    private static DependentsController instance;
    private Dependent currentDependent;
    private static final Logger logger = Logger.getLogger(DependentsController.class.getName());
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
    public void serializeDependentsToFile(String filePath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(dependents);
            logger.log(Level.INFO, "Dependents have been serialized and saved to file: " + filePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred while serializing dependents to file: " + filePath, e);
        }
    }

    public void deserializeDependentsFromFile(PolicyHolder currentPolicyHolder) {
        if (currentPolicyHolder != null) {
            try (FileInputStream fileInputStream = new FileInputStream("data/dependents.dat");
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

                Object importedObject = objectInputStream.readObject();

                if (importedObject instanceof List<?>) {
                    List<Dependent> allDependents = (ArrayList<Dependent>) importedObject;
                    ArrayList<Dependent> dependentArrayList = new ArrayList<>(allDependents);

                    dependents = dependentArrayList.stream()
                            .filter(dependent -> dependent.getPolicyHolder().equals(currentPolicyHolder))
                            .collect(Collectors.toCollection(ArrayList::new));

                    logger.log(Level.INFO, "Dependents have been deserialized and imported for policy holder " + currentPolicyHolder.getCustomerID());
                    return;
                }
                logger.log(Level.SEVERE, "Unexpected data format in the dependents file.");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "IO exception while reading dependents file", e);
            } catch (ClassNotFoundException e) {
                logger.log(Level.SEVERE, "Class not found during serialization.", e);
            }
        } else {
            logger.log(Level.SEVERE, "Error: No current policy holder set.");
        }
    }


}
