package View;

import Controller.ClaimsController;
import Controller.DependentsController;
import Model.Dependent;
import Model.InsuranceCard;

import java.util.Optional;
import java.util.Scanner;

public class DependentCustomerView {
    private final ClaimsController claimsController = ClaimsController.getInstance();
    private final DependentsController dependentsController = DependentsController.getInstance();
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

            Optional<Dependent> dependentCustomer = dependentsController.findDependent(inputID, inputName);

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
            System.out.println("2. View Personal Information");
            System.out.println("3. Manage Claims");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> this.viewInsuranceCard();
                case 2 -> this.viewPersonalInfo();
                case 3 -> this.viewClaimsMenu();
                default -> {
                    System.out.println("Invalid input.");
                    return;
                }
            }
        }
    }


    public void viewInsuranceCard() {
        System.out.println("________________________________________________________________________________DEPENDENT - VIEW INSURANCE CARD____________________________________________________________________________________");
        System.out.println("Enter your user ID: ");
        String userID = scanner.nextLine();

        System.out.println("Enter your full name: ");
        String fullName = scanner.nextLine();

        // Get the insurance card
        InsuranceCard insuranceCard = dependentsController.getInsuranceCard(userID, fullName);
        if (insuranceCard != null) {
            System.out.println("Insurance Card Details: ");
            System.out.println("Card Number: " + insuranceCard.getCardNumber());
            System.out.println("Policy Owner: " + insuranceCard.getPolicyOwner());
            System.out.println("Expiration Date: " + insuranceCard.getExpirationDate());
        } else {
            System.out.println("No insurance card found for the provided ID and full name.");
        }
    }

    public void viewPolicyHolderInfo() {

        System.out.println("___________________________________________________________________________DEPENDENT - VIEW POLICY OWNER INFORMATION____________________________________________________________________________________");

    }

    public void viewClaimsMenu() {

    }

    public void viewPersonalInfo() {
        Dependent currentDependent = dependentsController.getCurrentDependent();
        if (currentDependent != null) {
            System.out.println("__________________________________________________________________________DEPENDENT - VIEW PERSONAL INFORMATION____________________________________________________________________________________");
            System.out.println("ID: " + currentDependent.getCustomerID());
            System.out.println("Name: " + currentDependent.getFullName());
            System.out.println("Insurance Card: " + currentDependent.getInsuranceCard());
            System.out.println("Policy Owner: " + currentDependent.getPolicyHolder());
        }
    }

}
