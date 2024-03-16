package Controller;

import Model.Customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomersController implements Serializable {
    private static CustomersController instance;
    private final ArrayList<Customer> customers;
    private static int lastUserID = 0;

    public static CustomersController getInstance() {
        if (instance == null) {
            instance = new CustomersController();
        }
        return instance;
    }

    // Method to generate a new user ID
    public String generateUserID() {
        lastUserID++;
        return "c" + String.format("%07d", lastUserID);
    }

    public CustomersController() {
        this.customers = new ArrayList<>();
    }

    public List<Customer> getAllCustomer() {
        return customers;
    }
}
