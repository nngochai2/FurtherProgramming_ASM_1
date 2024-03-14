package View;

import Controller.ManageClaims;
import Controller.ManageDependents;
import Model.DependentCustomer;

import java.util.List;
import java.util.Scanner;

public class PolicyHolderView {
    private final ManageClaims manageClaims = ManageClaims.getInstance();
    private final ManageDependents manageDependents = ManageDependents.getInstance();
    private final DependentCustomerView dependentCustomerView = new DependentCustomerView();
    private final Scanner scanner = new Scanner(System.in);

    public void menu() {
        // Get the data from the system
        manageDependents.deserializeDependentsFromFile();
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
                case 8 -> {
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
        while (true) {
            System.out.println("========================================================================= MANAGE DEPENDENTS =========================================================================");
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

        List<DependentCustomer> dependents = manageDependents.getAllDependentCustomers();

        if (dependents.isEmpty()) {
            System.out.println("No dependents found.");
        } else {
            System.out.println("You currently have " + dependents.size() + " dependent(s).");

            // Display header
            System.out.printf("%-60s | %-70s", "ID", "Full name");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");

            // Display content
            System.out.println("List of dependents:");
            for (DependentCustomer dependentCustomer : dependents) {
                System.out.printf("%-60s | %-70s", dependentCustomer.getCustomerID(), dependentCustomer.getFullName());
            }
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }

        // User can view a dependent's details
        System.out.println("Enter the ID of a dependent that you want to view (enter 'cancel' to cancel): ");
        String selectedID = scanner.nextLine();

        if (!selectedID.equalsIgnoreCase("cancel")) {
            DependentCustomer dependentCustomer = manageDependents.getDependentByID(selectedID);

            if (dependentCustomer != null) {
                displayADependentDetails(selectedID);
            } else {
                System.out.println("No dependent was found.");
            }
        }
    }

    // Display a dependent details
    public void displayADependentDetails(String dependentID) {
        DependentCustomer dependentCustomer = manageDependents.getDependentByID(dependentID);
        if (dependentCustomer != null) {
            System.out.println("ID: " + dependentCustomer.getCustomerID());
            System.out.println("Full name: " + dependentCustomer.getFullName());

        } else {
            System.err.println("Error: No dependent found. Please try again.");
        }
    }

    public void addDependent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("______________________________________________________________________POLICY HOLDER - MANAGE DEPENDENTS - ADD A DEPENDENT____________________________________________________________________________________");
        System.out.println("Adding a new dependent (Enter 'cancel' at any time to cancel the procedure: ");
        while (true){
            System.out.println("Enter the dependent's full name: ");
            String fullName = scanner.nextLine();
            if (fullName.equals("cancel")) {
                System.out.println("Procedure has been canceled.");
                break;
            }

            System.out.println("Enter the dependent's");

            // Create a new dependent
//            DependentCustomer dependentCustomer = new DependentCustomer();

            // Add the dependent to the policyholder's account

        }

    }

    public void modifyDependent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("_____________________________________________________________POLICY HOLDER - MANAGE DEPENDENTS - MODIFY A DEPENDENT'S INFORMATION____________________________________________________________________________________");
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
            if (manageDependents.dependentExists(selectedID)) {
                // Display dependent information first
                System.out.println("Dependent found: ");
               displayADependentDetails(selectedID);

                System.out.println("Do you want to remove this dependent? (yes/no): ");
                String confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("yes")) {
                    boolean removed = manageDependents.removeDependent(selectedID);
                }
            }
        } while (!selectedID.equals("cancel"));

    }
}
