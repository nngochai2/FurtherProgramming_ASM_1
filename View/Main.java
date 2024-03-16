package View;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Welcome message
        System.out.println("=============================================================== WELCOME TO INSURANCE CLAIMS MANAGEMENT SYSTEM! ===============================================================");
        int input;
        do {
            System.out.println("Please login (enter '0' to cancel): ");
            System.out.println("1. Login as a Policy Holder");
            System.out.println("2. Login as a Dependent");
            System.out.println("Enter your choice: ");
            input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1 -> {
                    PolicyHolderView policyHolderView = new PolicyHolderView();
                    policyHolderView.authenticateUser();
                }
                case 2 -> {
                    DependentView dependentView = new DependentView();
                    dependentView.authenticateUser();
                }
            }
        } while (input != 0);
    }
}