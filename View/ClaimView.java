package View;

import Controller.ClaimsController;
import Model.Claim;

import java.util.Scanner;

public class ClaimView {
    private final ClaimsController claimsController = ClaimsController.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public void viewClaimsMenu() {
        System.out.println("__________________________________________________________________________DEPENDENT - MANAGE CLAIMS____________________________________________________________________________________");
        int input;
        do {
            System.out.println("You can choose one of the following options: ");
            System.out.println("1. View All Claims");
            System.out.println("2. Submit A Claim");
            input = scanner.nextInt();
            scanner.nextLine();

            if (input == 1) {
                // Display header
                System.out.printf("%-30s | %-70s", "ID", "Full name");
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
            }
        } while (input != 0);
    }

    public void displayClaimDetails(Claim claim) {
        System.out.println("Claim ID: " + claim.getClaimID());
        System.out.println("Date: " + claim.getClaimDate());
        System.out.println("Insured person: " + claim.getInsuredPerson());
        System.out.println("Card number: " + claim.getCardNumber());
        System.out.println("Exam date: " + claim.getClaimDate());
        System.out.println("Documents: " + claim.getDocuments());
        System.out.println("Claim amount: " + claim.getClaimAmount());
        System.out.println("Status: " + claim.getStatus());
        System.out.println("Receiver Banking Information: " + claim.getReceiverBankingInfo());
    }
}
