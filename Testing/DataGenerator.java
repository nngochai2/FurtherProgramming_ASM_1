package Testing;

import Controller.*;
import Model.*;
import View.AdminView;

import java.util.*;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

public class DataGenerator {

    public static void main(String[] args) {
        CustomersController customersController = CustomersController.getInstance();
        PolicyHoldersController policyHoldersController = PolicyHoldersController.getInstance();
        InsuranceCardsController insuranceCardsController = InsuranceCardsController.getInstance();
        DependentsController dependentsController = DependentsController.getInstance();
        AdminController adminController = AdminController.getInstance();
        ClaimsController claimsController = ClaimsController.getInstance();

        // Generate sample data
        generateSampleData(customersController, policyHoldersController, insuranceCardsController, dependentsController, adminController);

        // Generate claims for existing customers
        List<PolicyHolder> policyHolders = new ArrayList<>(policyHoldersController.getAllPolicyHolders());
        for (PolicyHolder policyHolder : policyHolders) {
            for (int i = 0; i < 2; i++) {
                Claim claim = generateClaim(policyHolder, 2);
                claimsController.addClaim(claim);
            }
        }

        List<Dependent> dependents = new ArrayList<>(dependentsController.getAllDependents());
        for (Dependent dependent : dependents) {
            for (int i = 0; i < 2; i++) {
                Claim claim = generateClaim(dependent, 2);
                claimsController.addClaim(claim);
            }
        }

        // Serialize the generated data
        serializeData(adminController, policyHoldersController, insuranceCardsController, dependentsController, claimsController);

        // Deserialize and print the data
        deserializeAndPrintData(adminController, policyHoldersController, dependentsController, insuranceCardsController, claimsController);
    }

    private static void generateSampleData(CustomersController customersController, PolicyHoldersController policyHoldersController, InsuranceCardsController insuranceCardsController, DependentsController dependentsController, AdminController adminController) {
        // Generate sample admins
        Admin admin1 = new Admin("13052004", "Hai Nguyen", "123456");
        Admin admin2 = new Admin("0000001", "Demo Admin", "123456");

        // Add admins to the controller
        adminController.addAdmin(admin1);
        adminController.addAdmin(admin2);

        // Generate sample PolicyHolders
        PolicyHolder policyHolder1 = new PolicyHolder(customersController.generateUserID(), "Nguyen Ngoc Hai", null); // Constant
        PolicyHolder policyHolder2 = new PolicyHolder(customersController.generateUserID(), "Elton John", null);

        // Generate sample Insurance Cards for PolicyHolders
        InsuranceCard insuranceCard1 = insuranceCardsController.generateInsuranceCard(policyHolder1, policyHolder1, "RMIT");
        InsuranceCard insuranceCard2 = insuranceCardsController.generateInsuranceCard(policyHolder2, policyHolder2, "RMIT");
        insuranceCardsController.addInsuranceCard(insuranceCard1);
        insuranceCardsController.addInsuranceCard(insuranceCard2);

        // Set insurance cards for PolicyHolders
        policyHolder1.setInsuranceCard(insuranceCard1);
        policyHolder2.setInsuranceCard(insuranceCard2);

        // Add policyholders to the controller
        policyHoldersController.addPolicyHolder(policyHolder1);
        policyHoldersController.addPolicyHolder(policyHolder2);

        // Generate sample Dependents
        Dependent dependent1 = new Dependent(customersController.generateUserID(), "Dependent 1", insuranceCard1, policyHolder1);
        Dependent dependent2 = new Dependent(customersController.generateUserID(), "Dependent 2", insuranceCard1, policyHolder1);
        Dependent dependent3 = new Dependent(customersController.generateUserID(), "Dependent 3", insuranceCard2, policyHolder2);

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
            InsuranceCard insuranceCard = insuranceCardsController.generateInsuranceCard(policyHolder, policyHolder, "RMIT");
            insuranceCardsController.addInsuranceCard(insuranceCard);
            policyHolder.setInsuranceCard(insuranceCard);
        }

        // Generate dependents
        List<Dependent> dependents = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            PolicyHolder policyHolder = policyHolders.get(i / 5);
            Dependent dependent = generateDependent(policyHolder);
            dependents.add(dependent);
            dependentsController.addDependent(dependent);
            dependent.setInsuranceCard(policyHolder.getInsuranceCard());
        }

        // Assign dependents to policy holders
        for (int i = 0; i < 30; i += 2) {
            PolicyHolder policyHolder = policyHolders.get(i / 2);
            List<Dependent> policyHolderDependents = new ArrayList<>(dependents.subList(i, i + 2));
            policyHolder.setDependents(policyHolderDependents);
            policyHoldersController.setDependents(policyHolder, policyHolderDependents);
        }

        // Generate and add claims for policy holders
        int totalClaims1 = 0;
        Random random = new Random();
        ClaimsController claimsController = ClaimsController.getInstance();
        while (totalClaims1 < 10) {
            int index = random.nextInt(policyHolders.size());
            PolicyHolder policyHolder = policyHolders.get(index);
            Claim claim = generateClaim(policyHolder, 1);
            claimsController.addClaim(claim);
            totalClaims1++;
        }

        // Generate and add claims for dependents
        int totalClaims2 = 0;
        while (totalClaims2 < 10) {
            int index = random.nextInt(dependents.size());
            Dependent dependent = dependents.get(index);
            Claim claim = generateClaim(dependent, 1);
            claimsController.addClaim(claim);
            totalClaims2++;
        }
    }

    // Method to generate policy holders
    private static PolicyHolder generatePolicyHolder() {
        CustomersController customersController = CustomersController.getInstance();
        String fullName = NameGenerator.generateFullName();
        return new PolicyHolder(customersController.generateUserID(), fullName, null);
    }

    // Method to generate dependents
    private static Dependent generateDependent(PolicyHolder policyHolder) {
        CustomersController customersController = CustomersController.getInstance();
        String fullName = NameGenerator.generateFullName();
        return new Dependent(customersController.generateUserID(), fullName, null, policyHolder);
    }

    // Method to generate random customer names
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

    // Method to generate claims
    private static Claim generateClaim(Customer customer, int index) {
        ClaimsController claimsController = ClaimsController.getInstance();
        // Generate claim details
        String claimID = claimsController.generateClaimID();
        Date claimDate = generateClaimDate(index);
        int cardNumber = customer.getInsuranceCard().getCardNumber();
        Date examDate = new Date();
        List<String> documents = generateRandomDocuments();
        int claimAmount = (int) (Math.random() * 1000);
        Claim.Status status = Claim.Status.NEW;
        String receiverBankingInfo;

        // Create new claim instance
        Claim claim = new Claim(claimID, claimDate, customer, cardNumber, examDate, documents, claimAmount, status, null);

        // Generate banking info
        BankingInfoGenerator bankingInfoGenerator = new BankingInfoGenerator(claim);
        String bankName = BankingInfoGenerator.BANK_NAME[new Random().nextInt(BankingInfoGenerator.BANK_NAME.length)];
        String claimantName = bankingInfoGenerator.getClaimantName();
        receiverBankingInfo = bankingInfoGenerator.generateBankNumber(bankName, claimantName);

        // Update the claim with banking info
        claim.setReceiverBankingInfo(receiverBankingInfo);

        // Add the claim to the customer
        customer.addClaim(claim);

        return claim;
    }

    // Method to generate random claim dates
    private static Date generateClaimDate(int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1); // Supposing the claims list started at 01/01/2023

        Random random = new Random();
        int daysToAdd = random.nextInt(365) + index; // Ensuring diversities in the claim dates
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return calendar.getTime();
    }

    // Method to generate random documents for the claims
    private static List<String> generateRandomDocuments() {
        List<String> documents = new ArrayList<>();
        String[] documentTypes = {"Insurance Card", "Doctor's Prescription", "Police Report", "Proof of Loss", "Medical Records", "Witness Statements",};
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(documentTypes.length);
            documents.add(documentTypes[index]);
        }
        return documents;
    }

    // Method to generate random banking information
    private record BankingInfoGenerator(Claim claim) {
        private static final String[] BANK_NAME = {"VP Bank", "Vietcombank", "Techcombank", "MB Bank", "BIDV", "SHB"};

        public String getClaimantName() {
            return claim.getInsuredPerson().getFullName();
        }

        public String generateBankNumber(String bankName, String claimantName) {
            Random random = new Random();

            // Remove spaces from claimant's name
            claimantName = claimantName.replaceAll("\\s", "");

            // Generate a random 12-digit bank number
            StringBuilder bankNumber = new StringBuilder(bankName);
            bankNumber.append("-").append(claimantName).append("-");

            int randomNumber;
            for (int i = 0; i < 12; i++) {
                randomNumber = random.nextInt(10);
                bankNumber.append(randomNumber); // Append a random digit (0-9)
            }

            return bankNumber.toString();
        }
    }

    // Method to serialize all generated data
    private static void serializeData(AdminController adminController, PolicyHoldersController policyHoldersController, InsuranceCardsController insuranceCardsController, DependentsController dependentsController, ClaimsController claimsController) {
        adminController.serializeAdminToFile("data/admins.dat");
        policyHoldersController.serializePolicyHoldersToFile("data/policyholders.dat");
        dependentsController.serializeDependentsToFile("data/dependents.dat");
        insuranceCardsController.serializeInsuranceCardsToFile("data/insuranceCards.dat");
        claimsController.serializeClaimsToFile("data/claims.dat");
    }

    // Method to read the data in the system
    private static void deserializeAndPrintData(AdminController adminController, PolicyHoldersController policyHoldersController, DependentsController dependentsController, InsuranceCardsController insuranceCardsController, ClaimsController claimsController) {
        policyHoldersController.deserializePolicyHoldersFromFile("data/policyholders.dat");
        dependentsController.deserializeAllDependents("data/dependents.dat");
        insuranceCardsController.deserializeInsuranceCardsFromFile("data/insuranceCards.dat");
        adminController.deserializeAdminsFromFile("data/admins.dat");
        claimsController.deserializeAllClaimsFromFile("data/claims.dat");
        claimsController.saveClaimsToTextFile("data/claims.txt");

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
        for (InsuranceCard insuranceCard : insuranceCardsController.getInsuranceCards()) {
            System.out.println(insuranceCard.toString());
            System.out.println("\n");
        }

        AdminView adminView = new AdminView();
        adminView.viewAllClaims();
    }
}
