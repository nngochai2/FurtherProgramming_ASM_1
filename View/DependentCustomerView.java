package View;

import Controller.ManageClaims;

import java.util.Scanner;

public class DependentCustomerView {
    private final ManageClaims manageClaims = ManageClaims.getInstance();

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("========================================================================= WELCOME DEPENDENT CUSTOMER =========================================================================");
            System.out.println("You can choose one of the following options: ");
            System.out.println("1. View Policy Information");
            System.out.println("2. View Policy Holder Information");
            System.out.println("3. Submit Claims");
            System.out.println("4. Check Claim Status");
            System.out.println("5. Update Personal Information");
            System.out.println("6. View Claim History");

            int choice = scanner.nextInt();
            scanner.nextLine();

        }
    }

}
