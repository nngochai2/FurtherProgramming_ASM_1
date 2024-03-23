package Controller;

import Model.Claim;
import Model.ClaimProcessManager;
import Model.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClaimsController implements Serializable, ClaimProcessManager {
    private static ClaimsController instance;
    private static int lastClaimID = 0;
    public ArrayList<Claim> claims;
    public ClaimsController() {
        claims = new ArrayList<>();
    }

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
        return "f-" + String.format("%10d", lastClaimID);
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

    // Serialize the claims to the system
    public void serializeClaimsToFile(String filePath) {
        createFileIfNotExists("data/claims.dat");
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ){
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Create parent directories if they don't exist
            objectOutputStream.writeObject(claims);
            System.out.println("Claims has been saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error: Unable to save products to " + filePath);
        }
    }

    // Read the claims from the system
    public void deserializeClaimsFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream("src/data/products.dat");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            Object importedObject = objectInputStream.readObject();

            if (importedObject instanceof ArrayList<?> importedData && !((ArrayList<?>) importedObject).isEmpty()) {

                if (importedData.get(0) instanceof Claim) {
                    claims = (ArrayList<Claim>) importedData;
                    System.out.println("Products have been deserialized and imported from src/data/products.dat");
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
}
