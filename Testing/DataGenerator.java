package Testing;

import Controller.CustomersController;
import Controller.DependentsController;
import Controller.InsuranceCardController;
import Controller.PolicyHoldersController;
import Model.Customer;
import Model.Dependent;
import Model.InsuranceCard;
import Model.PolicyHolder;
import View.PolicyHolderView;

import java.util.Date;

public class DataGenerator {
    public static void main(String[] args) {
        // Initialize controllers
        CustomersController customersController = CustomersController.getInstance();
        PolicyHoldersController policyHoldersController = PolicyHoldersController.getInstance();
        InsuranceCardController insuranceCardController = InsuranceCardController.getInstance();

        // Adding first policyholder and their insurance card
        PolicyHolder policyHolder1 = new PolicyHolder(customersController.generateUserID(), "Nguyen Ngoc Hai", null);
        InsuranceCard policyHolderInsuranceCard1 = insuranceCardController.generateInsuranceCard(policyHolder1, policyHolder1);
        policyHolder1.setInsuranceCard(policyHolderInsuranceCard1);

        policyHoldersController.addPolicyHolder(policyHolder1);
        insuranceCardController.addInsuranceCard(policyHolderInsuranceCard1);

        // Adding second policyholder and their insurance card
        PolicyHolder policyHolder2 = new PolicyHolder(customersController.generateUserID(), "Nguyen Van A", null);
        InsuranceCard policyHolderInsuranceCard2 = insuranceCardController.generateInsuranceCard(policyHolder2, policyHolder2);
        policyHolder2.setInsuranceCard(policyHolderInsuranceCard2);

        policyHoldersController.addPolicyHolder(policyHolder2);
        insuranceCardController.addInsuranceCard(policyHolderInsuranceCard2);

        // Adding a dependent for policyHolder 1
        Dependent dependent1 = new Dependent(customersController.generateUserID(), "Dependent 1", null, policyHolder1);
        InsuranceCard dependentInsuranceCard1 = insuranceCardController.generateInsuranceCard(dependent1, policyHolder1);
        policyHoldersController.addDependent(dependent1);
        dependent1.setInsuranceCard(dependentInsuranceCard1);

        // Adding a dependent for policyHolder 2
        Dependent dependent2 = new Dependent("c-4567890", "Dependent 2", null, policyHolder2);
        InsuranceCard dependentInsuranceCard2 = insuranceCardController.generateInsuranceCard(dependent2, policyHolder2);
        policyHoldersController.addDependent(dependent2);
        dependent2.setInsuranceCard(dependentInsuranceCard2);

        // Serialize data
        policyHoldersController.serializePolicyHoldersToFile("data/policyholders.dat");
        policyHoldersController.serializeDependentsToFile("data/dependents.dat");
        insuranceCardController.serializeInsuranceCardsToFile("data/insuranceCards.dat");

        // Confirmation message
        System.out.println("Data generation and serialization completed.");
        System.out.println(policyHolder1);
        System.out.println(policyHolder2);
        System.out.println(dependent1);
        System.out.println(dependent2);

        // Get the data
        policyHoldersController.deserializePolicyHoldersFromFile();
        policyHoldersController.deserializeDependentsFromFile();
        insuranceCardController.deserializeInsuranceCardsFromFile();

        int i = 0;
        for (PolicyHolder policyHolder : policyHoldersController.getAllPolicyHolders()) {
            System.out.println("Policy Holder " + (i + 1) + ":");
            System.out.println("ID: " + policyHolder.getCustomerID());
            System.out.println("Name: " + policyHolder.getFullName());

            // Check if the insurance card is not null before accessing its properties
            InsuranceCard insuranceCard = policyHolder.getInsuranceCard();
            if (insuranceCard != null) {
                System.out.println("Insurance Card: " + insuranceCard.getCardNumber());
            } else {
                System.out.println("No Insurance Card found.");
            }
            i++;
        }


        i = 0;
        for (Dependent dependent : policyHoldersController.getAllDependents()) {
            System.out.println("Dependent: " + (i + 1) + ":");
            System.out.println("ID: " + dependent.getCustomerID());
            System.out.println("Name: " + dependent.getFullName());
            System.out.println("Insurance Card: " + dependent.getInsuranceCard().getCardNumber());
            System.out.println("Policy Owner: " + dependent.getPolicyHolder());
            i++;
        }
    }
}
