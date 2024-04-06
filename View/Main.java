package View;

import java.util.Scanner;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Welcome message
        System.out.println("=============================================================== WELCOME TO INSURANCE CLAIMS MANAGEMENT SYSTEM! ===============================================================");
        while (true) {
            System.out.println("Please login (enter '0' to cancel): ");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as a Policy Holder");
            System.out.println("3. Login as a Dependent");
            System.out.println("4. Cancel");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    AdminView adminView = new AdminView(); // Leading to admins' interface
                    adminView.authenticateAdmins();
                }
                case 2 -> {
                    PolicyHolderView policyHolderView = new PolicyHolderView(); // Leading to policy holders' interface
                    policyHolderView.authenticateUser();
                }
                case 3 -> {
                    DependentView dependentView = new DependentView(); // Leading to dependents' interface
                    dependentView.authenticateUser();
                }
                case 4 -> {
                    System.out.println("Exiting the system...");
                    System.exit(0);
                }
                default -> {
                    System.err.println("Invalid input. Please try again.");
                    return;
                }
            }
        }
    }
}