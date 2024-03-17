package View;

import Controller.*;
import Model.Dependent;
import Model.InsuranceCard;
import Model.PolicyHolder;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PolicyHolderView {
    private final ClaimsController claimsController = ClaimsController.getInstance();
    private final CustomersController customersController = CustomersController.getInstance();
    private final PolicyHoldersController policyHoldersController = PolicyHoldersController.getInstance();
    private final InsuranceCardController insuranceCardController = InsuranceCardController.getInstance();
    private final PolicyHolder currentPolicyHolder = policyHoldersController.getCurrentPolicyHolder();
    private final ClaimView claimView = new ClaimView();
    private final Scanner scanner = new Scanner(System.in);

    // Manage user login
    public void authenticateUser() {
        int maxAttempts = 5; // Limits fail attempts
        int attempts = 0;

        while (true) {
            System.out.println("________________________________________________________________________________POLICY HOLDER LOGIN____________________________________________________________________________________");
            System.out.println("Enter your user ID:");
            String inputID = scanner.nextLine();

            System.out.println("Enter your full name: ");
            String inputName = scanner.nextLine();

            Optional<PolicyHolder> policyHolderCustomer = policyHoldersController.findPolicyHolder(inputID, inputName);

            if (policyHolderCustomer.isPresent()) {
                System.out.println("Login successful. Welcome, " + inputName + "!");
                policyHoldersController.deserializePolicyHoldersFromFile();
                menu(); // Proceed to main menu
                return; // Exit the method
            } else {
                attempts++;

                if (attempts < maxAttempts) {
                    System.out.println("Login failed. Please check your user ID and full name.");
                    System.out.println("Attempts remaining: " + (maxAttempts - attempts));
                    System.out.println("1. Try again");
                    System.out.println("2. Cancel");
                    System.out.println("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice != 1) {
                        System.out.println("Exiting policy holder login...");
                        break;
                    }
                } else {
                    System.out.println("Maximum login attempts reached. Exiting policy holder login...");
                    break;
                }
            }
        }
    }

    public void menu() {
        while (true) {
            System.out.println("========================================================================= WELCOME POLICY HOLDER =========================================================================");
            System.out.println("You can choose one of the following options: ");
            System.out.println("1. Manage Dependents");
            System.out.println("2. View Insurance Card");
            System.out.println("3. Manage Claims");
            System.out.println("4. Manage Personal Information");
            System.out.println("5. Renew or Modify Policy");
            System.out.println("6. Exit");
            System.out.println("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> this.manageDependents();
                case 2 -> this.viewInsuranceCard();
                case 3 -> this.claimView.viewClaimsMenu();
                case 4 -> this.managePersonalInfo();
                case 5 -> this.managePolicy();
                case 6 -> {
                    // Exit the program
                    System.out.println("Exiting the program...");
                    System.exit(0);
                }
                default -> {
                    System.err.println("Error: Invalid input.");
                }
            }
        }
    }

    // Display a menu for managing dependents
    public void manageDependents() {
        policyHoldersController.deserializeDependentsFromFile();
        while (true) {
            System.out.println("___________________________________________________________________________POLICY HOLDER - MANAGE DEPENDENTS____________________________________________________________________________________");
            System.out.println("You can choose one of the following options: ");
            System.out.println("1. View All Dependents");
            System.out.println("2. Add A Dependent");
            System.out.println("3. Modify A Dependent");
            System.out.println("4. Remove A Dependent");
            System.out.println("5. Back to Main Menu");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewAllDependents();
                case 2 -> addDependent();
                case 3 -> modifyDependent();
                case 4 -> removeDependent();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid input");
            }
        }

    }

    // View all dependents
    public void viewAllDependents() {
        System.out.println("______________________________________________________________________POLICY HOLDER - MANAGE DEPENDENTS - VIEW ALL DEPENDENTS____________________________________________________________________________________");

        List<Dependent> dependents = policyHoldersController.getAllDependents();

        if (dependents.isEmpty()) {
            System.out.println("No dependents found.");
        } else {
            System.out.println("You currently have " + dependents.size() + " dependent(s).");

            // Display header
            System.out.printf("%-20s | %-70s", "ID", "Full name");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");

            // Display content
            System.out.println("List of dependents:");
            for (Dependent dependent : dependents) {
                System.out.printf("%-20s | %-70s", dependent.getCustomerID(), dependent.getFullName());
            }
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }

        // User can view a dependent's details
        System.out.println("Enter the ID of a dependent that you want to view (enter 'cancel' to cancel): ");
        String selectedID = scanner.nextLine();
        if (!selectedID.equalsIgnoreCase("cancel")) {
            displayADependentDetails(selectedID);
        } else {
            // Return to the manage dependents menu
            manageDependents();
        }

    }

    // Display a dependent details
    public void displayADependentDetails(String dependentID) {
        Dependent dependent = policyHoldersController.getDependentByID(dependentID);
        if (dependent != null) {
            System.out.println("ID: " + dependent.getCustomerID());
            System.out.println("Full name: " + dependent.getFullName());

        } else {
            System.out.println("Error: No dependent found. Please try again.");
        }
    }

    public void viewInsuranceCard() {
        System.out.println("________________________________________________________________________________POLICY HOLDER - VIEW INSURANCE CARD____________________________________________________________________________________");
        System.out.println("Enter your user ID: ");
        String userID = scanner.nextLine();

        System.out.println("Enter your full name: ");
        String fullName = scanner.nextLine();

        // Get the insurance card
        InsuranceCard insuranceCard = policyHoldersController.getInsuranceCard(userID, fullName);
        if (insuranceCard != null) {
            System.out.println("Insurance Card Details: ");
            System.out.println("Card Number: " + insuranceCard.getCardNumber());
            System.out.println("Policy Owner: " + insuranceCard.getPolicyOwner());
            System.out.println("Expiration Date: " + insuranceCard.getExpirationDate());
        } else {
            System.out.println("No insurance card found for the provided ID and full name.");
        }
    }

    public void addDependent() {
        System.out.println("______________________________________________________________________POLICY HOLDER - MANAGE DEPENDENTS - ADD A DEPENDENT____________________________________________________________________________________");
        while (true){
            System.out.println("Adding a new dependent (Enter 'cancel' at any time to cancel the procedure.");
            System.out.println("Enter the dependent's full name: ");
            String fullName = scanner.nextLine();

            if (fullName.equals("cancel")) {
                System.out.println("Procedure has been canceled.");
                break;
            }

            // Create a new dependent
            String newDependentID = customersController.generateUserID();
            Dependent newDependent = new Dependent(newDependentID, fullName, null, currentPolicyHolder);
            policyHoldersController.addDependent(newDependent);

            // Serialize the new dependent into the system
            policyHoldersController.serializeDependentsToFile("data/dependents.dat");

            // Create a new insurance card
            createNewInsuranceCard(fullName);

            // Print out the result and view all dependents
            System.out.println("Dependent" + newDependent.getFullName() + "has been added successfully!");
            viewAllDependents();
        }
    }

    public void createNewInsuranceCard(String dependentFullName) {
        policyHoldersController.deserializeDependentsFromFile();
        System.out.println("________________________________________________________________________________POLICY HOLDER - CREATE NEW INSURANCE CARD____________________________________________________________________________________");
        System.out.println("Are you sure you want to proceed? (yes/no):");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            // Find the dependent
            Optional<Dependent> dependentOptional = policyHoldersController.getDependentByName(dependentFullName);
            if (dependentOptional.isPresent()) {
                Dependent dependent = dependentOptional.get();
                InsuranceCard newInsuranceCard = insuranceCardController.generateInsuranceCard(dependent, currentPolicyHolder);
                insuranceCardController.addInsuranceCard(newInsuranceCard);

                System.out.println("New insurance card has been created successfully: ");
                System.out.println(newInsuranceCard);

                // Serialize the updated insurance cards data
                insuranceCardController.serializeInsuranceCardsToFile("data/insuranceCards.dat");
            } else {
                System.out.println("Dependent with name " + dependentFullName + " not found.");
            }
        } else {
            System.out.println("Procedure has been canceled.");
        }
    }

    public void modifyDependent() {
        System.out.println("_____________________________________________________________POLICY HOLDER - MANAGE DEPENDENTS - MODIFY A DEPENDENT'S INFORMATION____________________________________________________________________________________");
        System.out.println("Enter the dependent's ID: ");
        String selectedID = scanner.nextLine();

        // Check if the targeted dependent exists
        Dependent dependentToEdit = policyHoldersController.getDependentByID(selectedID);
        if (dependentToEdit == null) {
            System.err.println("Error: Dependent with this ID does not exist.");
            return;
        }

        // Display the current details of the targeted dependent
        System.out.println("Dependent found: ");
        displayADependentDetails(selectedID);

        //


    }

    public void removeDependent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("______________________________________________________________________POLICY HOLDER - MANAGE DEPENDENTS - REMOVING A DEPENDENT____________________________________________________________________________________");

        String selectedID;
        do {
            System.out.println("Enter the ID of the dependent (enter 'cancel' to cancel the procedure): ");
            selectedID = scanner.nextLine();
            if (policyHoldersController.dependentExists(selectedID)) {
                // Display dependent information first
                System.out.println("Dependent found: ");
               displayADependentDetails(selectedID);

                System.out.println("Do you want to remove this dependent? (yes/no): ");
                String confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("yes")) {
                    boolean removed = policyHoldersController.removeDependent(selectedID);
                }
            }
        } while (!selectedID.equals("cancel"));
    }

    public void managePersonalInfo() {
        while (true) {
            System.out.println("______________________________________________________________________POLICY HOLDER - MANAGE PERSONAL INFORMATION____________________________________________________________________________________");
            System.out.println("You can choose one of the following options:");
            System.out.println("1. View Personal Information");
            System.out.println("2. Edit Personal Information");
            System.out.println("3. Cancel");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewPersonalInfo();
                case 2 -> editPersonalInfo();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public void viewPersonalInfo() {
        if (currentPolicyHolder != null) {
            System.out.println("__________________________________________________________________________POLICY HOLDER - VIEW PERSONAL INFORMATION____________________________________________________________________________________");
            System.out.println("ID: " + currentPolicyHolder.getCustomerID());
            System.out.println("Full Name: " + currentPolicyHolder.getFullName());
            System.out.println("Insurance Card: " + currentPolicyHolder.getInsuranceCard().toString());
        }
    }

    public void editPersonalInfo() {

    }

    public void managePolicy() {

    }
}
