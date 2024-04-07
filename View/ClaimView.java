package View;

import Controller.ClaimsController;
import Model.Claim;
import Model.Customer;
import Model.Dependent;
import Model.PolicyHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

public class ClaimView {
    private final ClaimsController claimsController = ClaimsController.getInstance();
    private Customer currentCustomer; // Attribute to store the current customer
    private final Scanner scanner = new Scanner(System.in);

    public ClaimView(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    // Method to display the main claim menu
    public void viewClaimsMenu() {
        // Customize the header
        String user = null;
        if (currentCustomer instanceof PolicyHolder) {
            user = "POLICY HOLDER";
        } else if (currentCustomer instanceof Dependent) {
            user = "DEPENDENT";
        }
        System.out.println("__________________________________________________________________________" + user + " - MANAGE CLAIMS____________________________________________________________________________________");
        while (true) {
            System.out.println("You can choose one of the following options: ");
            System.out.println("1. View All Claims");
            System.out.println("2. Submit A Claim");
            System.out.println("3. Delete A Claim");
            System.out.println("4. Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> this.displayAllClaims();
                case 2 -> this.submitClaim();
                case 3 -> this.deleteAClaim();
                case 4 -> {
                    System.out.println("Exiting claim view...");
                    return;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    // Method to display all claims of a customer
    public void displayAllClaims() {
        List<Claim> claims = claimsController.getAllClaimsForCustomer(currentCustomer);

        // Customize the header
        String user = null;
        if (currentCustomer instanceof PolicyHolder) {
            user = "POLICY HOLDER";
        } else if (currentCustomer instanceof Dependent) {
            user = "DEPENDENT";
        }
        System.out.println("__________________________________________________________________________" + user + " - MANAGE CLAIMS - VIEW ALL CLAIMS____________________________________________________________________________________");
        if (claims.isEmpty()) {
            System.out.println("No claim found for " + currentCustomer.getFullName());
        } else {
            System.out.println("Claims for " + currentCustomer.getFullName() + ":");
            // Display header
            System.out.printf("%-13s | %-30s | %-30s | %-40s | %-15s | %-35s | %-50s | %-15s | %-15s\n",
                    "ID", "Date", "Insured Person", "Banking Info", "Card Number", "Exam Date", "Documents", "Claim Amount", "Status");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            // Display content
            for (Claim claim : claims) {
                System.out.printf("%-13s | %-30s | %-30s | %-40s | %-15s | %-35s | %-50s | %-15s | %-15s\n",
                        claim.getClaimID(), claim.getClaimDate(), claim.getInsuredPerson(), claim.getReceiverBankingInfo(),
                        claim.getCardNumber(), claim.getExamDate(), claim.getDocuments(),
                        claim.getClaimAmount() + "$", claim.getStatus());
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            // Customer can view a claim by entering claim ID
            System.out.println("Enter a claim ID to view the details (enter 'cancel' to cancel): ");
            String selectedID = scanner.nextLine();

            if (!selectedID.equalsIgnoreCase("cancel")) {
                this.displayClaimDetails(selectedID);
            } else {
                System.out.println("Exiting...");
                this.viewClaimsMenu();
            }
        }

    }

    // Display a claim's details
    public void displayClaimDetails(String claimID) {
        if (claimsController.claimExits(claimID)) {
            Claim claim = claimsController.getClaimByID(claimID);
            System.out.println("Claim ID: " + claim.getClaimID());
            System.out.println("Date: " + claim.getClaimDate());
            System.out.println("Insured person: " + claim.getInsuredPerson());
            System.out.println("Card number: " + claim.getCardNumber());
            System.out.println("Exam date: " + claim.getClaimDate());
            System.out.println("Documents: " + claim.getDocuments());
            System.out.println("Claim amount: " + claim.getClaimAmount());
            System.out.println("Status: " + claim.getStatus());
            System.out.println("Receiver Banking Information: " + claim.getReceiverBankingInfo());
        } else {
            System.err.println("The claim with the ID" + claimID + " does not exist.");
        }
    }

    // Display to submit a claim
    public void submitClaim() {
        String user = null;
        if (currentCustomer instanceof PolicyHolder) {
            user = "POLICY HOLDER";
        } else if (currentCustomer instanceof Dependent) {
            user = "DEPENDENT";
        }
        System.out.println("__________________________________________________________________________" + user + " - MANAGE CLAIMS - SUBMIT A CLAIM____________________________________________________________________________________");
        while (true) {
            System.out.println("Enter the exam date (YYYY-MM-DD):");
            String examDateStr = scanner.nextLine();
            Date examDate = parseDate(examDateStr); // Convert the date from String to Date format

            System.out.println("Enter the claim amount: ");
            int claimAmount = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter your banking information (Bank - Name - Number): ");
            String bankingInfo = scanner.nextLine();

            // Prompt user to attach documents
            List<String> documents = new ArrayList<>();
            while (true) {
                System.out.println("Enter the document name:");
                String documentName = scanner.nextLine();
                documents.add(documentName);

                // Prompt if users want to add another document
                System.out.println("Do you want to add another document? (yes/no):");
                String confirmation = scanner.nextLine();
                if (!confirmation.equalsIgnoreCase("yes")) {
                    break;
                }
            }

            // Asking for user's confirmation
            System.out.println("Do you want to submit this claim? (yes/no):");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                Claim claim = new Claim(claimsController.generateClaimID(), new Date(), currentCustomer, currentCustomer.getInsuranceCard().getCardNumber(),
                        examDate, documents, claimAmount, Claim.Status.NEW, bankingInfo);

                // Add the claim to the controller
                claimsController.addClaim(claim);
                System.out.println("Claim submitted successfully!");
                claimsController.appendClaimToTextFile(claim,"data/claims.txt");
                claimsController.serializeClaimsToFile("data/claims.dat");
                return;

            } else if (confirmation.equalsIgnoreCase("no")) {
                System.out.println("Procedure has been canceled.");
                return;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
    public void deleteAClaim() {
        // Customize the header
        String user = null;
        if (currentCustomer instanceof PolicyHolder) {
            user = "POLICY HOLDER";
        } else if (currentCustomer instanceof Dependent) {
            user = "DEPENDENT";
        }
        System.out.println("__________________________________________________________________________" + user + " - MANAGE CLAIMS - REMOVE A CLAIM____________________________________________________________________________________");
        System.out.println("Enter the claim ID you want to remove (enter 'cancel' to cancel): ");
        String claimID = scanner.nextLine();

        if (claimID.equalsIgnoreCase("cancel")) {
            System.out.println("Procedure has been canceled.");
            return;
        }

        // Get the claim instance
        Claim claimToRemove = claimsController.getAClaim(claimID);
        if (claimToRemove == null) {
            System.out.println("No claim with the ID found. Please try again. ");
        } else if (!claimToRemove.getStatus().equals(Claim.Status.NEW)) {
            System.out.println("You cannot remove this claim since it is being or has been reviewed.");
        } else {
            // Display claim details
            System.out.println("Claim found: ");
            this.displayClaimDetails(claimID);

            // Asking for user confirmation
            System.out.println("\nDo you want to remove this claim? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                claimsController.deleteClaim(claimID);
                System.out.println("Claim has been removed successfully.");

                // Save the changes
                claimsController.serializeClaimsToFile("data/claims.dat");
                claimsController.saveClaimsToTextFile("data/claims.txt");
            }
        }

    }

    // Method to convert String to Date
    private Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            System.err.println("Error: Invalid data format. Please enter data in YYYY-MM-DD format.");
            return null;
        }
    }
}
