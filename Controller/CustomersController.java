package Controller;

import Model.Customer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Nguyen Ngoc Hai - s3978281
 */

public class CustomersController implements Serializable {
    private static CustomersController instance;
    private ArrayList<Customer> customers;
    private static int lastUserID = 0; // Ensuring each generated ID is unique

    public static CustomersController getInstance() {
        if (instance == null) {
            instance = new CustomersController();
        }
        return instance;
    }

    // Method to generate a new user ID
    public String generateUserID() {
        lastUserID++;
        return "c-" + String.format("%07d", lastUserID);
    }

    public CustomersController() {
        this.customers = new ArrayList<>();
    }

    public Customer findCustomerByID(String customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerID().equals(customerID)) {
                return customer;
            }
        }
        return null;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }
}
