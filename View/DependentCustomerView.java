package View;

import Controller.ManageClaims;
import Controller.ManageDependents;
import Model.DependentCustomer;

import java.util.Scanner;

public class DependentCustomerView {
    private final ManageClaims manageClaims = ManageClaims.getInstance();
    private final ManageDependents manageDependents = ManageDependents.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("========================================================================= WELCOME DEPENDENT CUSTOMER =========================================================================");
            System.out.println("You can choose one of the following options: ");
            System.out.println("1. View Policy Information");
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
