package View;

import Controller.*;
import Model.Customer;
import Model.PolicyHolder;

import java.util.List;
import java.util.Scanner;

public class AdminView {
    private final AdminController adminController = AdminController.getInstance();
    private final PolicyHoldersController policyHoldersController = PolicyHoldersController.getInstance();
    private final DependentsController dependentsController = DependentsController.getInstance();
    private final ClaimsController claimsController = ClaimsController.getInstance();
    private final InsuranceCardController insuranceCardController = InsuranceCardController.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public void authenticateAdmins() {
        int maxAttempts = 5;
        int attempts = 0;
        while (true) {
            policyHoldersController.deserializePolicyHoldersFromFile();
            System.out.println("________________________________________________________________________________ADMIN LOGIN____________________________________________________________________________________");
            System.out.println("Enter your user ID:");
            String inputID = scanner.nextLine();

            System.out.println("Enter your full name: ");
            String inputName = scanner.nextLine();

            PolicyHolder policyHolderCustomer = policyHoldersController.authenticatePolicyHolder(inputID, inputName);

            if (policyHolderCustomer != null) {
                System.out.println("Login successful. Welcome, " + inputName + "!");
                policyHoldersController.deserializeDependentsFromFile();
                insuranceCardController.deserializeInsuranceCardsFromFile();
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
            System.out.println("You can choose one of the following procedure:");
            System.out.println("1. Manage Customers");
            System.out.println("2. Manage Claims");
            System.out.println("3. Manage Insurance Cards");
            System.out.println("4. Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> this.manageCustomers();
                case 2 -> this.manageClaims();
                case 3 -> this.manageInsuranceCards();
                case 4 -> {
                    System.exit(0);
                    System.out.println("Exiting the system...");
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    // Menu for admins to manage the customers
    public void manageCustomers() {
        while (true) {
            System.out.println("________________________________________________________________________________ADMIN - MANAGE USERS____________________________________________________________________________________");
            System.out.println("You can choose one of the following options:");
            System.out.println("1. View all customers");
            System.out.println("2. Add a new policy holder");
            System.out.println("3. Add a new dependent");
            System.out.println("4. Remove a customer");
            System.out.println("5. Modify a customer");
            System.out.println("6. Exit");
            System.out.println("Enter your choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> this.viewAllCustomersMenu();
                case 2 -> this.addPolicyHolder();
                case 3 -> this.addDependent();
                case 4 -> this.removeCustomer();
                case 5 -> this.modifyCustomer();
                case 6 -> this.menu();
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    // Method to view all customers in the system (admin can choose to view all customers, all policyholders, or all dependents)
    public void viewAllCustomersMenu() {
        while (true) {
            System.out.println("________________________________________________________________________________ADMIN - MANAGE USERS - VIEW ALL CUSTOMER____________________________________________________________________________________");
            System.out.println("You can choose one of the following procedure:");
            System.out.println("1. View all customers");
            System.out.println("2. View all policy holders");
            System.out.println("3. View all dependents");
            System.out.println("4. Cancel");
            System.out.println("Enter your choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> this.viewAllCustomers();
            }
        }
    }

    public void viewAllCustomers() {
        System.out.println("________________________________________________________________________________ADMIN - MANAGE USERS - VIEW ALL CUSTOMERS____________________________________________________________________________________");
        List<Customer> customers = adminController.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            manageCustomers();
        } else {
            System.out.println("There are currently " + customers.size() + " users in the system.");

            // Display header
            System.out.printf("%-20s | %-70s" , "ID", "Full name\n");
            System.out.println("-----------------------------------------------------");

            // Display content
            for (Customer customer : customers) {
                System.out.printf();
            }
        }
    }

    public void addPolicyHolder() {

    }

    public void addDependent() {

    }

    public void removeCustomer() {

    }

    public void modifyCustomer() {

    }

    public void manageClaims() {

    }

    public void manageInsuranceCards() {

    }

}
