package View;

import Controller.ClaimsController;
import Model.Claim;

public class ClaimView {
    private final ClaimsController claimsController = ClaimsController.getInstance();
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
