package Controller;

import Model.Customer;
import Model.InsuranceCard;
import Model.PolicyHolder;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsuranceCardController implements Serializable {
    private static InsuranceCardController instance;
    public List<InsuranceCard> insuranceCards;

    public InsuranceCardController() {
        insuranceCards = new ArrayList<>();
    }

    public static InsuranceCardController getInstance() {
        if (instance == null) {
            instance = new InsuranceCardController();
        }
        return instance;
    }

    public InsuranceCard generateInsuranceCard(Customer cardHolder, PolicyHolder policyOwner) {
        String cardNumber = generateCardNumber();
        Date expirationDate = getDefaultExpirationDate();

        // Create insurance card with provided customer and policy owner information
        return new InsuranceCard(cardNumber, cardHolder, policyOwner, expirationDate);
    }


    // Method to generate a random card number
    private String generateCardNumber() {
        return String.valueOf((int) (Math.random() * 9_000_000_000L) + 1_000_000_000L);
    }

    // Method to generate default expiration date
    private Date getDefaultExpirationDate() {
        // Considering the expiration date for an insurance card is one year from the date it is issued
        long oneYearInMillis = 365L * 24 * 60 * 60 * 1000; // Converting a year into milliseconds
        return new Date(System.currentTimeMillis() + oneYearInMillis);
    }

    public void serializeInsuranceCardsToFile(String filePath) {
        createFileIfNotExists("data/insuranceCards.dat");
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ){
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Create parent directory
            objectOutputStream.writeObject(insuranceCards);
            System.out.println("Products have been saved products to " + filePath);
        } catch (IOException e) {
            System.err.println("Error: Unable to save products to " + filePath);
        }
    }

    public void deserializeInsuranceCardsFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream("data/insuranceCards.dat");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            Object importedObject = objectInputStream.readObject();

            if (importedObject instanceof ArrayList<?> importedData && !((ArrayList<?>) importedObject).isEmpty()) {

                if (importedData.get(0) instanceof InsuranceCard) {
                    insuranceCards = (ArrayList<InsuranceCard>) importedData;
                    System.out.println("Products have been deserialized and imported from data/insuranceCards.dat");
                    return;
                }
            }
            System.err.println("Error: Unexpected data format in the file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createFileIfNotExists(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + filePath);
                } else {
                    System.err.println("Error: Unable to create file " + filePath);
                }
            } catch (IOException e) {
                System.err.println("Error: Unable to create file " + filePath);
            }
        }
    }

    public void addInsuranceCard(InsuranceCard insuranceCard) {
        insuranceCards.add(insuranceCard);
    }
}
