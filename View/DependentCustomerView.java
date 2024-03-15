package View;

import Controller.ManageClaims;
import Controller.ManageDependents;
import Model.DependentCustomer;
import Model.PolicyHolderCustomer;

import java.util.Optional;
import java.util.Scanner;

public class DependentCustomerView {
    private final ManageClaims manageClaims = ManageClaims.getInstance();
    private final ManageDependents manageDependents = ManageDependents.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public void authenticateUser() {
        int maxAttempts = 5; // Limits fail attempts
        int attempts = 0;

        while (true) {
            System.out.println("________________________________________________________________________________DEPENDENT LOGIN____________________________________________________________________________________");
            System.out.println("Enter your user ID:");
            String inputID = scanner.nextLine();

            System.out.println("Enter your full name: ");
            String inputName = scanner.nextLine();

            Optional<DependentCustomer> dependentCustomer = manageDependents.findDependent(inputID, inputName);

            if (dependentCustomer.isPresent()) {
                System.out.println("Login successful. Welcome, " + inputName + "!");
                menu(); // Proceed to main menu
                return; // Exit the method
            } else {
                System.out.println("Login failed. Please check your user ID and full name.");
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
                        System.out.println("Exiting dependent login...");
                        break;
                    }
                } else {
                    System.out.println("Maximum login attempts reached. Exiting dependent login...");
                    break;
                }
            }
        }
    }

    public void menu() {
        while (true) {
            System.out.println("========================================================================= WELCOME DEPENDENT CUSTOMER =========================================================================");
            System.out.println("You can choose one of the following options: ");
            System.out.println("1. View Insurance Card");
            System.out.println("2. View Policy Holder Information");
            System.out.println("3. Manage Claims");
            System.out.println("5. View Personal Information");
            System.out.println("6. Update Personal Information");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> this.viewPolicyInfo();
                case 2 -> this.viewPolicyHolderInfo();
                case 3 -> this.viewClaimsMenu();
                case 4 -> this.viewPersonalInfo();
                default -> {
                    System.out.println("Invalid input.");
                    return;
                }
            }
        }
    }

    public void viewPolicyInfo() {

    }

    public void viewPolicyHolderInfo() {

    }

    public void viewClaimsMenu() {

    }

    public void viewPersonalInfo() {
//        DependentCustomer dependentCustomer = manageDependents.getDependentByID();
//        if (dependentCustomer.getCustomerID().equals(dependentID)) {
//            System.out.println("Personal Information");
//            System.out.println("ID: " + dependentCustomer.getCustomerID());
//            System.out.println("Name: " + dependentCustomer.getFullName());
//            System.out.println("Insurance Card: " + dependentCustomer.getInsuranceCard());
//        }
    }

}
