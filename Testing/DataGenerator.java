//package Testing;
//
//import Controller.DependentsController;
//import Controller.PolicyHoldersController;
//import Model.Dependent;
//import Model.InsuranceCard;
//import Model.PolicyHolder;
//
//import java.util.Date;
//
//public class DataGenerator {
//    public static void main(String[] args) {
//        // Initialize controllers
//        PolicyHoldersController policyHoldersController = PolicyHoldersController.getInstance();
//        DependentsController dependentsController = DependentsController.getInstance();
//
//        // Adding PolicyHolders
//        InsuranceCard policyHolderInsuranceCard1 = new InsuranceCard("1234567", "Nguyen Ngoc Hai", "Nguyen Ngoc Hai", new Date());
//        PolicyHolder policyHolder1 = new PolicyHolder("c-1234567", "Nguyen Ngoc Hai", policyHolderInsuranceCard1);
//        policyHoldersController.addPolicyHolder(policyHolder1);
//
//        InsuranceCard policyHolderInsuranceCard2 = new InsuranceCard("2345678", "Policy Holder 2", "Policy Holder 2", new Date());
//        PolicyHolder policyHolder2 = new PolicyHolder("c-2345678", "Policy Holder 2", policyHolderInsuranceCard2);
//        policyHoldersController.addPolicyHolder(policyHolder2);
//
//        // Adding Dependents
//        InsuranceCard dependentInsuranceCard1 = new InsuranceCard("3456789", "Nguyen Van A", new Date());
//        Dependent dependent1 = new Dependent("c-3456789", "Dependent 1", dependentInsuranceCard1, policyHolder1);
//        dependentsController.addDependent(dependent1);
//
//        InsuranceCard dependentInsuranceCard2 = new InsuranceCard("4567890", "Dependent 2", new Date());
//        Dependent dependent2 = new Dependent("c-4567890", "Dependent 2", dependentInsuranceCard2, policyHolder1);
//        dependentsController.addDependent(dependent2);
//
//
//        // Serialize data
//        policyHoldersController.serializePolicyHoldersToFile("data/policyHolders.dat");
//        dependentsController.serializeDependentsToFile("data/dependents.dat");
//
//        // Confirmation message
//        System.out.println("Data generation and serialization completed.");
//    }
//}
