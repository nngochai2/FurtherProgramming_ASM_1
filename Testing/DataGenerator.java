//package Testing;
//
//import Controller.InsuranceCardController;
//import Controller.PolicyHoldersController;
//import Model.Dependent;
//import Model.InsuranceCard;
//import Model.PolicyHolder;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class DataGenerator {
//
//    public static void main(String[] args) {
//        PolicyHoldersController policyHoldersController = PolicyHoldersController.getInstance();
//        InsuranceCardController insuranceCardController = InsuranceCardController.getInstance();
//
//        // Generate sample data
//        generateSampleData(policyHoldersController, insuranceCardController);
//
//        // Serialize the generated data
//        serializeData(policyHoldersController, insuranceCardController);
//
//        // Deserialize and print the data
//        deserializeAndPrintData();
//    }
//
//    private static void generateSampleData(PolicyHoldersController policyHoldersController, InsuranceCardController insuranceCardController) {
//        // Generate sample PolicyHolders
//        PolicyHolder policyHolder1 = new PolicyHolder("c-0000001", "Nguyen Ngoc Hai", null);
//        PolicyHolder policyHolder2 = new PolicyHolder("c-0000002", "Elton John", null);
//
//        // Generate sample Insurance Cards for PolicyHolders
//        InsuranceCard insuranceCard1 = insuranceCardController.generateInsuranceCard(policyHolder1, policyHolder1);
//        InsuranceCard insuranceCard2 = insuranceCardController.generateInsuranceCard(policyHolder2, policyHolder2);
//
//        // Set insurance cards for PolicyHolders
//        policyHolder1.setInsuranceCard(insuranceCard1);
//        policyHolder2.setInsuranceCard(insuranceCard2);
//
//
//        // Generate sample Dependents
//
//
//        Dependent dependent1 = new Dependent("c-" + generateRandomID(), "Dependent 1", insuranceCard3, policyHolder1);
//        Dependent dependent2 = new Dependent("c-" + generateRandomID(), "Dependent 2", insuranceCard4, policyHolder1);
//        Dependent dependent3 = new Dependent("c-" + generateRandomID(), "Dependent 3", insuranceCard5, policyHolder2);
//
//        insuranceCard3.setCardHolder(dependent1);
//        insuranceCard4.setCardHolder(dependent2);
//        insuranceCard5.setCardHolder(dependent3);
//
//        // Assign dependents to policy holders
//        List<Dependent> dependents1 = new ArrayList<>();
//        dependents1.add(dependent1);
//        dependents1.add(dependent2);
//        policyHoldersController.setDependents(policyHolder1, dependents1);
//
//        List<Dependent> dependents2 = new ArrayList<>();
//        dependents2.add(dependent3);
//        policyHoldersController.setDependents(policyHolder2, dependents2);
//
//        // Add policyholders to the controller
//        policyHoldersController.addPolicyHolder(policyHolder1);
//        policyHoldersController.addPolicyHolder(policyHolder2);
//    }
//
//    private static String generateRandomID() {
//        return String.format("%07d", (int) (Math.random() * 10_000_000));
//    }
//
//    private static void serializeData(PolicyHoldersController policyHoldersController, InsuranceCardController insuranceCardController) {
//        policyHoldersController.serializePolicyHoldersToFile("data/policyholders.dat");
//        policyHoldersController.serializeDependentsToFile("data/dependents.dat");
//        insuranceCardController.serializeInsuranceCardsToFile("data/insuranceCards.dat");
//    }
//
//    private static void deserializeAndPrintData() {
//        PolicyHoldersController policyHoldersController = PolicyHoldersController.getInstance();
//        policyHoldersController.deserializePolicyHoldersFromFile();
//
//
//        List<PolicyHolder> policyHolders = policyHoldersController.getAllPolicyHolders();
//        for (PolicyHolder policyHolder : policyHolders) {
//            System.out.println(policyHolder);
//            System.out.println("Dependents:");
//            for (Dependent dependent : policyHolder.getDependents()) {
//                System.out.println(dependent);
//            }
//            System.out.println();
//        }
//    }
//}
//
