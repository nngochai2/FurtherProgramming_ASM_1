package Controller;

import Model.Claim;
import Model.ClaimProcessManager;
import Model.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

public class ClaimsController implements Serializable, ClaimProcessManager {
    public List<Claim> claims;
    private static ClaimsController instance;
    private static int claimCounter = 0;
    private static final Logger logger = Logger.getLogger(ClaimsController.class.getName());
    private ClaimsController() {
        claims = this.deserializeAllClaimsFromFile("data/claims.dat");
    }
    public static ClaimsController getInstance() {
        if (instance == null) {
            instance = new ClaimsController();
        }
        return instance;
    }

    public List<Claim> getAllClaims() {
        if (claims.isEmpty()) {
            updateCachedClaims();
        }
        return claims;
    }

    private void updateCachedClaims() {
        List<Claim> fetchedClaims = deserializeAllClaimsFromFile("data/claims.dat");
        claims.clear();
        claims.addAll(fetchedClaims);
    }

    @Override
    public void addClaim(Claim claim) {
        claims.add(claim);
    }

    @Override
    public void updateClaim(Claim claim) {
        // Find the claim by ID and update the details
        for (int i = 0; i < claims.size(); i++) {
            if (claims.get(i).getClaimID().equals(claim.getClaimID())) {
                claims.set(i, claim);
                break;
            }
        }
    }

    @Override
    public void deleteClaim(String claimID) {
        // Find and remove the claim by ID
        claims.removeIf(claim -> claim.getClaimID().equals(claimID) && claim.getStatus().equals(Claim.Status.NEW));
    }

    @Override
    public Claim getAClaim(String claimID) {
        // Find and return the claim by ID
        for (Claim claim : claims) {
            if (claim.getClaimID().equals(claimID)) {
                return claim;
            }
        }
        return null;
    }

    @Override
    public List<Claim> getClaims() {
        return new ArrayList<>(claims); // Return a copy of the list to prevent direct modification
    }

    // Method to get all claims of a customer
    public List<Claim> getAllClaimsForCustomer(Customer customer) {
        if (claims.isEmpty()) {
            // If the cached list is empty, fetch claims from the data source and cache them
            claims = deserializeClaimsForCustomer("data/claims.dat", customer);
        }
        // Filter claims based on the customer's ID
        return claims.stream()
                .filter(claim -> claim.getInsuredPerson().getCustomerID().equals(customer.getCustomerID()))
                .distinct()
                .toList();
    }

    // Check if a claim has already exits
    public boolean claimExits(String claimID) {
        for (Claim claim : claims) {
            if (claim.getClaimID().equals(claimID)) {
                return true;
            }
        }
        return false;
    }

    // Method to generate random claim IDs
    public static String generateClaimID() {
        claimCounter++;
        String claimID = "f-" + String.format("%010d", claimCounter);

        // Check if the generated claim ID already exists
        if (getInstance().claimExits(claimID)) {
            return generateClaimID();
        }
        return claimID;
    }

    // Method to get a claim by ID
    public Claim getClaimByID(String claimID) {
        Claim claim = null;
        for (Claim c : claims) {
            if (c.getClaimID().equals(claimID)) {
                claim = c;
            }
        }
        return claim;
    }

    // Method to save the claims to a .txt file
    public void saveClaimsToTextFile(String filePath) {
        createFileIfNotExists(filePath);
        try (FileWriter fileWriter = new FileWriter(filePath);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter))
        {
            for (Claim claim : claims) {
                String serializedClaim = saveClaimToText(claim);
                bufferedWriter.write(serializedClaim);
                bufferedWriter.newLine();
            }
            System.out.println();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to save claims to " + filePath);
        }
    }

    // Method to serialize a single claim to a String
    private String saveClaimToText(Claim claim) {
        return "ID:" + claim.getClaimID() +
                ", Claim Date: " + claim.getClaimDate() +
                ", Insured Person: " + claim.getInsuredPerson() +
                ", Card Number: " + claim.getCardNumber() +
                ", Exam Date: " + claim.getExamDate() +
                ", Documents: " + claim.getDocuments() +
                ", Claim amount: " + claim.getClaimAmount() +
                ", Status: " + claim.getStatus() +
                ", Receiver Banking Info: " + claim.getReceiverBankingInfo();
    }

    // Method to create a file in case the file path does not exist
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

    // Save new instance of claim to the text file without making any changes to the data file
    public void appendClaimToTextFile(Claim claim, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)){

            // Serialize the claim to a string
            String claimString = saveClaimToText(claim);

            // Append the claim string to the file
            printWriter.println(claimString);

            System.out.println("Claim appended to file: " + filePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred while appending claim to file " + filePath, e);
        }
    }



    // Method to serialize the claims to a .dat file
    public void serializeClaimsToFile(String filePath) {
        List<Claim> existingClaims = new ArrayList<>();

        // Check if the file already exists
        File file = new File(filePath);
        if (file.exists()) {
            // Deserialize existing claims from the file
            existingClaims = deserializeAllClaimsFromFile(filePath);
        }

        // Append new claims to the existing list
        existingClaims.addAll(claims);

        // Write the updated list of claims to the file
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(existingClaims);
            System.out.println("Claims have been appended and serialized to file: " + filePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred while serializing claims to file " + filePath, e);
        }
    }

    // Method to deserialize all claims in the system (developed for admins only)
    public List<Claim> deserializeAllClaimsFromFile(String filePath) {
        List<Claim> importedClaims = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            Object importedObject = objectInputStream.readObject();

            if (importedObject instanceof ArrayList<?>) {
                @SuppressWarnings("unchecked")
                ArrayList<Claim> allClaims = (ArrayList<Claim>) importedObject;
                // Filter out duplicate claims based on claim ID
                Set<String> claimIDs = new HashSet<>();
                for (Claim claim : allClaims) {
                    if (claimIDs.add(claim.getClaimID())) {
                        importedClaims.add(claim);
                    }
                }
                System.out.println("Claims have been deserialized and imported from " + filePath);
            } else {
                logger.log(Level.SEVERE, "Unexpected data format in the claims file.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IO exception while reading claims file.", e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Class not found during deserialization.", e);
        }
        return importedClaims;
    }

    // Method to deserialize claims for a specific customer
    public List<Claim> deserializeClaimsForCustomer(String filePath, Customer customer) {
        List<Claim> importedClaims = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            Object importedObject = objectInputStream.readObject();

            if (importedObject instanceof ArrayList<?>) {
                @SuppressWarnings("unchecked")
                ArrayList<Claim> allClaims = (ArrayList<Claim>) importedObject;
                importedClaims.addAll(allClaims.stream()
                        .filter(claim -> claim.getInsuredPerson().equals(customer))
                        .toList());
                System.out.println("Claims have been deserialized and imported from " + filePath + " for " + customer.getFullName());
            } else {
                logger.log(Level.SEVERE, "Unexpected data format in the claims file.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IO exception while reading claims file.", e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Class not found during deserialization.", e);
        }
        return importedClaims;
    }

}
