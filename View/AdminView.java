package View;

import Controller.*;
import Model.Admin;
import Model.Customer;
import Model.Dependent;
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

    // Authenticate admins' login
    public void authenticateAdmins() {
        int maxAttempts = 5;
        int attempts = 0;
        while (true) {
            adminController.deserializeAdminsFromFile("data/admins.dat");
            System.out.println("________________________________________________________________________________ADMIN LOGIN____________________________________________________________________________________");
            System.out.println("Enter your username:");
            String username = scanner.nextLine();

            System.out.println("Enter your password: ");
            String password = scanner.nextLine();

            Admin admin = adminController.authenticateAdmin(username, password);

            if (admin != null) {
                System.out.println("Login successful. Welcome, " + admin.getUsername() + "!");

                insuranceCardController.deserializeInsuranceCardsFromFile("data/insuranceCards.dat");
                menu(); // Proceed to main menu
                return; // Exit the method
            } else {
                attempts++;

                if (attempts < maxAttempts) {
                    System.out.println("Login failed. Please check your username and password.");
                    System.out.println("Attempts remaining: " + (maxAttempts - attempts));
                    System.out.println("1. Try again");
                    System.out.println("2. Cancel");
                    System.out.println("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice != 1) {
                        System.out.println("Exiting admin login...");
                        break;
                    }
                } else {
                    System.out.println("Maximum login attempts reached. Exiting admin login...");
                    break;
                }
            }
        }
    }

    public void menu() {
        while (true) {
            System.out.println("========================================================================= WELCOME ADMIN =========================================================================");
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
                case 6 -> {
                    this.menu();
                    return;
                }
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
                case 2 -> this.viewAllPolicyHolders();
                case 3 -> this.viewAllDependents();
                case 4 -> {
                    this.manageCustomers();
                    return;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public void viewAllCustomers() {
        System.out.println("________________________________________________________________________________ADMIN - MANAGE USERS - VIEW ALL CUSTOMERS____________________________________________________________________________________");
        List<Customer> customers = adminController.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            this.viewAllCustomersMenu();
        } else {
            System.out.println("There are currently " + customers.size() + " users in the system.");

            // Display header
            System.out.printf("%-20s | %-70s | %-70s | %70s%n\n" , "ID", "Full name", "Insurance Card Number", "Role");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");

            // Display content
            for (Customer customer : customers) {
                String id = customer.getCustomerID();
                String fullName = customer.getFullName();
                int insuranceCardNumber = customer.getInsuranceCard().getCardNumber();
                String role = (customer instanceof PolicyHolder) ? "Policy Holder" : "Dependent";

                System.out.printf("%-20s | %-70s | %-70s | %70s%n\n", id, fullName, insuranceCardNumber, role);
            }
        }
    }

    public void viewAllPolicyHolders() {
        System.out.println("________________________________________________________________________________ADMIN - MANAGE USERS - VIEW ALL CUSTOMERS - VIEW ALL POLICY HOLDERS____________________________________________________________________________________");
        List<PolicyHolder> policyHolders = adminController.getAllPolicyHolders();
        if (policyHolders.isEmpty()) {
            System.out.println("No policy holder found.");
            this.viewAllCustomersMenu();
        } else {
            System.out.println("There are currently " + policyHolders.size() + " policy holders in the system.");

            // Display header
            System.out.printf("%-20s | %-70s | %-70s\n" , "ID", "Full name", "Insurance Card Number");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");

            // Display content
            for (PolicyHolder policyHolder : policyHolders) {
                String id = policyHolder.getCustomerID();
                String fullName = policyHolder.getFullName();
                int insuranceCardNumber = policyHolder.getInsuranceCard().getCardNumber();
                System.out.printf("%-20s | %-70s | %-70s\n", id, fullName, insuranceCardNumber);
            }
        }
    }

    public void viewAllDependents() {
        System.out.println("________________________________________________________________________________ADMIN - MANAGE USERS - VIEW ALL CUSTOMERS - VIEW ALL DEPENDENTS____________________________________________________________________________________");
        List<Dependent> dependents = adminController.getAllDependents();
        if (dependents.isEmpty()) {
            System.out.println("No dependents found.");
            this.viewAllCustomersMenu();
        } else {
            System.out.println("There are currently " + dependents.size() + " dependents in the system.");

            // Display header
            System.out.printf("%-20s | %-70s | %-70s\n" , "ID", "Full name", "Insurance Card Number");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");

            // Display content
            for (Dependent dependent : dependents) {
                String id = dependent.getCustomerID();
                String fullName = dependent.getFullName();
                int insuranceCardNumber = dependent.getInsuranceCard().getCardNumber();
                System.out.printf("%-20s | %-70s | %-70s\n", id, fullName, insuranceCardNumber);
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
        System.out.println("________________________________________________________________________________ADMIN - MANAGE CLAIMS____________________________________________________________________________________");
        while (true) {
            System.out.println("You can choose one of the following procedures:");
            System.out.println("1. View All Claims");
            System.out.println("2. Modify A Claim");
            System.out.println("3. Cancel");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> this.viewAllClaims();
                case 2 -> this.modifyAClaim();
                case 3 -> {
                    return;
                }
                default -> System.err.println("Invalid input. Please try again.");
            }
        }
    }

    public void viewAllClaims() {

    }

    public void modifyAClaim() {

    }

    public void manageInsuranceCards() {

    }

}
