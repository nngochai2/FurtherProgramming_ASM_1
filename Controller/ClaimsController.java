package Controller;

import Model.Claim;
import Model.ClaimProcessManager;
import Model.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClaimsController implements Serializable, ClaimProcessManager {
    private static ClaimsController instance;
    private static int lastClaimID = 0;
    public ArrayList<Claim> claims;
    public ClaimsController() {
        claims = new ArrayList<>();
    }
    private static final Logger logger = Logger.getLogger(ClaimsController.class.getName());

    public static ClaimsController getInstance() {
        if (instance == null) {
            instance = new ClaimsController();
        }
        return instance;
    }

    public List<Claim> getAllClaims() {
        return claims;
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
        claims.removeIf(claim -> claim.getClaimID().equals(claimID));
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
        // Filter claims based on the customer's ID
        return claims.stream()
                .filter(claim -> claim.getInsuredPerson().getCustomerID().equals(customer.getCustomerID()))
                .collect(Collectors.toList());
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
    public String generateClaimID() {
        lastClaimID++;
        return "f-" + String.format("%010d", lastClaimID);
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

    // Serialize the claims to a .txt file
    public void serializeClaimsToFile(String filePath) {
        createFileIfNotExists(filePath);
        try (FileWriter fileWriter = new FileWriter(filePath);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);)
        {
            for (Claim claim : claims) {
                String serializedClaim = serializeClaimToText(claim);
                bufferedWriter.write(serializedClaim);
                bufferedWriter.newLine();
            }
            System.out.println();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to save claims to " + filePath);
        }
    }

    private String serializeClaimToText(Claim claim) {
        return "ID:" + claim.getClaimID() +
                ", Date: " + claim.getClaimDate() +
                ", Insured Person: " + claim.getInsuredPerson() +
                ", Status: " + claim.getStatus();
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
}
