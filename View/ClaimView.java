package View;

import Controller.ClaimsController;
import Model.Claim;
import Model.Customer;
import Model.Dependent;
import Model.PolicyHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ClaimView {
    private final ClaimsController claimsController = ClaimsController.getInstance();
    private Customer currentCustomer; // Attribute to store the current customer
    private final Scanner scanner = new Scanner(System.in);

    public ClaimView(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
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
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> this.displayAllClaims(currentCustomer);
                case 2 -> this.submitClaim();
                case 3 -> {
                    System.out.println("Exiting claim view...");
                    return;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    // Method to display all claims of a customer
    public void displayAllClaims(Customer customer) {
        List<Claim> claims = claimsController.getAllClaimsForCustomer(customer);

        if (claims.isEmpty()) {
            System.out.println("No claims found for " + customer.getFullName());
        } else {
            System.out.println("Claims for " + customer.getFullName() + ":");
            // Display header
            System.out.printf("%-10s | %-20s | %-20s | %-15s | %-15s | %-15s | %-15s | %-15s\n",
                    "ID", "Date", "Insured Person", "Card Number", "Exam Date", "Documents", "Claim Amount", "Status");
            System.out.println("-------------------------------------------------------------------------------------------------------------------");

            // Display content
            for (Claim claim : claims) {
                System.out.printf("%-10s | %-20s | %-20s | %-15s | %-15s | %-15s | %-15s | %-15s\n",
                        claim.getClaimID(), claim.getClaimDate(), claim.getInsuredPerson(),
                        claim.getCardNumber(), claim.getExamDate(), claim.getDocuments(),
                        claim.getClaimAmount(), claim.getStatus());
            }

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

            System.out.println("Enter your banking information: ");
            String bankingInfo = scanner.nextLine();

            // Asking for user's confirmation
            System.out.println("Do you want to submit this claim? (yes/no):");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                Claim claim = new Claim(claimsController.generateClaimID(), new Date(), currentCustomer, currentCustomer.getInsuranceCard().getCardNumber(),
                        examDate, null, claimAmount, Claim.Status.NEW, bankingInfo);

                // Add the claim to the controller
                claimsController.addClaim(claim);
                System.out.println("Claim submitted successfully!");
                claimsController.serializeClaimsToFile("data/claims.dat"); // Serialize the claim

            } else if (confirmation.equalsIgnoreCase("no")) {
                System.out.println("Procedure has been canceled.");
                return;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    // Method to convert String to Date
    private Date parseDate(String dataString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dataString);
        } catch (ParseException e) {
            System.err.println("Error: Invalid data format. Please enter data in YYYY-MM-DD format.");
            return null;
        }
    }
}
