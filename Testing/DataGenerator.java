package Testing;

import Controller.DependentsController;
import Controller.InsuranceCardController;
import Controller.PolicyHoldersController;
import Model.Dependent;
import Model.InsuranceCard;
import Model.PolicyHolder;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static void main(String[] args) {
        PolicyHoldersController policyHoldersController = PolicyHoldersController.getInstance();
        InsuranceCardController insuranceCardController = InsuranceCardController.getInstance();
        DependentsController dependentsController = DependentsController.getInstance();

        // Generate sample data
        generateSampleData(policyHoldersController, insuranceCardController, dependentsController);

        // Serialize the generated data
        serializeData(policyHoldersController, insuranceCardController, dependentsController);

        // Deserialize and print the data
        deserializeAndPrintData(policyHoldersController, dependentsController, insuranceCardController);
    }

    private static void generateSampleData(PolicyHoldersController policyHoldersController, InsuranceCardController insuranceCardController, DependentsController dependentsController) {
        // Generate sample PolicyHolders
        PolicyHolder policyHolder1 = new PolicyHolder("c-0000001", "Nguyen Ngoc Hai", null);
        PolicyHolder policyHolder2 = new PolicyHolder("c-0000002", "Elton John", null);

        // Generate sample Insurance Cards for PolicyHolders
        InsuranceCard insuranceCard1 = insuranceCardController.generateInsuranceCard(policyHolder1, "RMIT");
        InsuranceCard insuranceCard2 = insuranceCardController.generateInsuranceCard(policyHolder2, "RMIT");
        insuranceCardController.addInsuranceCard(insuranceCard1);
        insuranceCardController.addInsuranceCard(insuranceCard2);

        // Set insurance cards for PolicyHolders
        policyHolder1.setInsuranceCard(insuranceCard1);
        policyHolder2.setInsuranceCard(insuranceCard2);

        // Generate sample Dependents
        Dependent dependent1 = new Dependent("c-0000003", "Dependent 1", insuranceCard1, policyHolder1);
        Dependent dependent2 = new Dependent("c-0000004", "Dependent 2", insuranceCard1, policyHolder1);
        Dependent dependent3 = new Dependent("c-0000005", "Dependent 3", insuranceCard2, policyHolder2);

        // Add policyholders to the controller
        policyHoldersController.addPolicyHolder(policyHolder1);
        policyHoldersController.addPolicyHolder(policyHolder2);

        // Add dependents to the controller
        dependentsController.addDependent(dependent1);
        dependentsController.addDependent(dependent2);
        dependentsController.addDependent(dependent3);

        // Assign the dependents for the policy holders
        List<Dependent> dependents1 = new ArrayList<>();
        dependents1.add(dependent1);
        policyHolder1.setDependents(dependents1);
        policyHoldersController.addDependent(policyHolder1, dependent1);

        List<Dependent> dependents2 = new ArrayList<>();
        dependents2.add(dependent2);
        dependents2.add(dependent3);
        policyHolder2.setDependents(dependents2);
        policyHoldersController.setDependents(policyHolder2, dependents2);

    }

    private static void serializeData(PolicyHoldersController policyHoldersController, InsuranceCardController insuranceCardController, DependentsController dependentsController) {
        policyHoldersController.serializePolicyHoldersToFile("data/policyholders.dat");
        dependentsController.serializeDependentsToFile("data/dependents.dat");
        insuranceCardController.serializeInsuranceCardsToFile("data/insuranceCards.dat");
    }

    private static void deserializeAndPrintData(PolicyHoldersController policyHoldersController, DependentsController dependentsController, InsuranceCardController insuranceCardController) {
        policyHoldersController.deserializePolicyHoldersFromFile();
        insuranceCardController.deserializeInsuranceCardsFromFile("data/insuranceCards.dat");

        System.out.println("All policy holders:");
        for (PolicyHolder policyHolder : policyHoldersController.getAllPolicyHolders()) {
            System.out.println(policyHolder);
            System.out.println("Dependents:");

            // Get dependents for the current policy holder
            List<Dependent> dependents = policyHoldersController.getAllDependents(policyHolder);
            for (Dependent dependent : dependents) {
                System.out.println(dependent);
            }
            System.out.println();
        }

        System.out.println("All dependents: ");
        for (Dependent dependent : dependentsController.getAllDependents()) {
            System.out.println(dependent);
        }

        System.out.println("All insurance cards:");
        for (InsuranceCard insuranceCard : insuranceCardController.getInsuranceCards()) {
            System.out.println(insuranceCard);
        }
    }

}
