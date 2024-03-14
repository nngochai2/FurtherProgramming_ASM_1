package View;

import Controller.ManageClaims;
import Controller.ManageDependents;
import Model.DependentCustomer;

import java.util.Scanner;

public class PolicyHolderView {
    private final ManageClaims manageClaims = ManageClaims.getInstance();
    private final ManageDependents manageDependents = ManageDependents.getInstance();
    private final DependentCustomerView dependentCustomerView = new DependentCustomerView();

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("========================================================================= WELCOME POLICY HOLDER =========================================================================");
            System.out.println("You can choose one of the following options: ");
            System.out.println("1. Manage Dependents");
            System.out.println("2. View Policy Information");
            System.out.println("3. Submit Claims");
            System.out.println("4. Track Claim Status");
            System.out.println("5. Update Personal Information");
            System.out.println("6. Renew or Modify Policy");
            System.out.println("7. View Claim History");
            System.out.println("8. Exit");
            System.out.println("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> manageDependents();
            }
        }
    }

    public void manageDependents() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("========================================================================= MANAGE DEPENDENTS =========================================================================");
        System.out.println("You can choose one of the following options: ");
        System.out.println("1. View All Dependents");
        System.out.println("2. Add A Dependent");
        System.out.println("3. Modify A Dependent");
        System.out.println("4. Remove A Dependent");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> viewAllDependents();
            case 2 -> addDependent();
            case 3 -> modifyDependent();
            case 4 -> removeDependent();
            default -> {
                System.out.println("Invalid input");
                break;
            }
        }
    }

    public void viewAllDependents() {

    }


    public void addDependent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("______________________________________________________________________POLICY HOLDER - MANAGE DEPENDENTS - ADD A DEPENDENT____________________________________________________________________________________");
        System.out.println("Adding a new dependent (Enter 'cancel' at any time to cancel the procedure: ");
        do {
            System.out.println("Enter the dependent's full name: ");
            String fullName = scanner.nextLine();


            System.out.println("Enter the dependent's");
        } while ()

    }

    public void modifyDependent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("______________________________________________________________________POLICY HOLDER - MANAGE DEPENDENTS - MODIFY A DEPENDENT'S INFORMATION____________________________________________________________________________________");
        System.out.println("Enter the dependent's ID: ");
        String selectedID = scanner.nextLine();

        // Check if the targeted dependent exists
        DependentCustomer dependentCustomerToEdit = manageDependents.getDependentByID(selectedID);
        if (dependentCustomerToEdit == null) {
            System.err.println("Error: Dependent with this ID does not exist.");
            return;
        }

        // Display the current details of the targeted dependent
        System.out.println("Dependent found: ");
        dependentCustomerView.viewPersonalInfo(selectedID);

        //


    }

    public void removeDependent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("______________________________________________________________________POLICY HOLDER - MANAGE DEPENDENTS - REMOVING A DEPENDENT____________________________________________________________________________________");

        String selectedID;
        do {
            System.out.println("Enter the ID of the dependent (enter 'cancel' to cancel the procedure): ");
            selectedID = scanner.nextLine();
            if (manageDependents.dependentExists(selectedID)) {
                // Display dependent information first
                System.out.println("Dependent found: ");
                dependentCustomerView.viewPersonalInfo(selectedID);

                System.out.println("Do you want to remove this dependent? (yes/no): ");
                String confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("yes")) {
                    boolean removed = manageDependents.removeDependent(selectedID);
                }
            }
        } while (!selectedID.equals("cancel"));

    }
}
