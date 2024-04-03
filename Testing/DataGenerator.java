package Testing;

import Controller.*;
import Model.Admin;
import Model.Dependent;
import Model.InsuranceCard;
import Model.PolicyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DataGenerator {

    public static void main(String[] args) {
        CustomersController customersController = CustomersController.getInstance();
        PolicyHoldersController policyHoldersController = PolicyHoldersController.getInstance();
        InsuranceCardController insuranceCardController = InsuranceCardController.getInstance();
        DependentsController dependentsController = DependentsController.getInstance();
        AdminController adminController = AdminController.getInstance();

        // Generate sample data
        generateSampleData(customersController, policyHoldersController, insuranceCardController, dependentsController, adminController);

        // Serialize the generated data
        serializeData(adminController, policyHoldersController, insuranceCardController, dependentsController);

        // Deserialize and print the data
        deserializeAndPrintData(adminController, policyHoldersController, dependentsController, insuranceCardController);
    }

    private static void generateSampleData(CustomersController customersController, PolicyHoldersController policyHoldersController, InsuranceCardController insuranceCardController, DependentsController dependentsController, AdminController adminController) {
        // Generate sample admins
        Admin admin1 = new Admin("13052004", "Hai Nguyen", "123456");
        Admin admin2 = new Admin("0000001", "Demo Admin", "123456");

        // Add admins to the controller
        adminController.addAdmin(admin1);
        adminController.addAdmin(admin2);

        // Generate sample PolicyHolders
        PolicyHolder policyHolder1 = new PolicyHolder("c-0000001", "Nguyen Ngoc Hai", null); // Constant
        PolicyHolder policyHolder2 = new PolicyHolder("c-0000002", "Elton John", null);

        // Generate sample Insurance Cards for PolicyHolders
        InsuranceCard insuranceCard1 = insuranceCardController.generateInsuranceCard(policyHolder1, policyHolder1, "RMIT");
        InsuranceCard insuranceCard2 = insuranceCardController.generateInsuranceCard(policyHolder2, policyHolder2, "RMIT");
        insuranceCardController.addInsuranceCard(insuranceCard1);
        insuranceCardController.addInsuranceCard(insuranceCard2);

        // Set insurance cards for PolicyHolders
        policyHolder1.setInsuranceCard(insuranceCard1);
        policyHolder2.setInsuranceCard(insuranceCard2);

        // Add policyholders to the controller
        policyHoldersController.addPolicyHolder(policyHolder1);
        policyHoldersController.addPolicyHolder(policyHolder2);

        // Generate sample Dependents
        Dependent dependent1 = new Dependent("c-0000003", "Dependent 1", insuranceCard1, policyHolder1);
        Dependent dependent2 = new Dependent("c-0000004", "Dependent 2", insuranceCard1, policyHolder1);
        Dependent dependent3 = new Dependent("c-0000005", "Dependent 3", insuranceCard2, policyHolder2);

        // Add dependents to the controller
        dependentsController.addDependent(dependent1);
        dependentsController.addDependent(dependent2);
        dependentsController.addDependent(dependent3);

        // Assign the dependents for the policy holders
        List<Dependent> dependents1 = new ArrayList<>();
        dependents1.add(dependent1);
        policyHolder1.setDependents(dependents1);

        List<Dependent> dependents2 = new ArrayList<>();
        dependents2.add(dependent2);
        dependents2.add(dependent3);
        policyHolder2.setDependents(dependents2);
        policyHoldersController.setDependents(policyHolder2, dependents2);

        // Generate and add policy holders to the controller
        List<PolicyHolder> policyHolders = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            PolicyHolder policyHolder = generatePolicyHolder();
            policyHolders.add(policyHolder);
            policyHoldersController.addPolicyHolder(policyHolder);
        }

        // Generate insurance cards for policy holders
        for (PolicyHolder policyHolder : policyHolders) {
            InsuranceCard insuranceCard = insuranceCardController.generateInsuranceCard(policyHolder, policyHolder, "RMIT");
            insuranceCardController.addInsuranceCard(insuranceCard);
            policyHolder.setInsuranceCard(insuranceCard);
        }

        // Generate dependents
        List<Dependent> dependents = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            PolicyHolder policyHolder = policyHolders.get(i/5);
            Dependent dependent = generateDependent(policyHolder);
            dependents.add(dependent);
            dependentsController.addDependent(dependent);
            dependent.setInsuranceCard(policyHolder.getInsuranceCard());
        }

        // Assign dependents to policy holders
        for (int i = 0; i < 15; i+= 5) {
            PolicyHolder policyHolder = policyHolders.get(i/5);
            List<Dependent> policyHolderDependents = new ArrayList<>(dependents.subList(i, i + 5));
            policyHolder.setDependents(policyHolderDependents);
            policyHoldersController.setDependents(policyHolder, policyHolderDependents);
        }
    }

    private static PolicyHolder generatePolicyHolder() {
        CustomersController customersController = CustomersController.getInstance();
        String fullName = NameGenerator.generateFullName();
        return new PolicyHolder(customersController.generateUserID(), fullName, null);
    }

    private static Dependent generateDependent(PolicyHolder policyHolder) {
        String fullName = NameGenerator.generateFullName();
        InsuranceCard insuranceCard = policyHolder.getInsuranceCard();
        return new Dependent("c-" + UUID.randomUUID().toString().substring(0, 8), fullName, null, policyHolder);
    }

    private static void serializeData(AdminController adminController, PolicyHoldersController policyHoldersController, InsuranceCardController insuranceCardController, DependentsController dependentsController) {
        adminController.serializeAdminToFile("data/admins.dat");
        policyHoldersController.serializePolicyHoldersToFile("data/policyholders.dat");
        dependentsController.serializeDependentsToFile("data/dependents.dat");
        insuranceCardController.serializeInsuranceCardsToFile("data/insuranceCards.dat");

    }

    private static void deserializeAndPrintData(AdminController adminController, PolicyHoldersController policyHoldersController, DependentsController dependentsController, InsuranceCardController insuranceCardController) {
        policyHoldersController.deserializePolicyHoldersFromFile("data/policyholders.dat");
        dependentsController.deserializeAllDependents("data/dependents.dat");
        insuranceCardController.deserializeInsuranceCardsFromFile("data/insuranceCards.dat");
        adminController.deserializeAdminsFromFile("data/admins.dat");

        System.out.println("All admins:");
        for (Admin admin : adminController.getAdminList()) {
            System.out.println("Username: " + admin.getUsername());
            System.out.println("Password: " + admin.getPassword());
            System.out.println("-----------------------------------------------------\n");
        }

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
        System.out.println("-----------------------------------------------------\n");

        System.out.println("All dependents: ");
        for (Dependent dependent : dependentsController.getAllDependents()) {
            System.out.println(dependent);
        }
        System.out.println("-----------------------------------------------------\n");

        System.out.println("All insurance cards:");
        for (InsuranceCard insuranceCard : insuranceCardController.getInsuranceCards()) {
            System.out.println(insuranceCard.toString());
            System.out.println("\n");
        }
    }

    public static class NameGenerator {
        private static final String[] FIRST_NAMES = {"Hai", "Nghia", "Mai", "Dat", "Anh", "Linh", "Duy"};
        private static final String[] MIDDLE_NAMES = {"Ngoc", "Van", "Duc", "Mai", "Phuong", "Thi"};
        private static final String [] LAST_NAMES = {"Nguyen", "Pham", "Le", "Tran", "Dao"};
        private static final Random random = new Random();

        private static String generateFullName() {
            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String middleName = MIDDLE_NAMES[random.nextInt(MIDDLE_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            return lastName + " " + middleName + " " + firstName;
        }
    }
}
