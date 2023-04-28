package service;

//importing packages and models
import java.util.ArrayList;
import java.util.Collection;
import model.Customer;

public class CustomerService {
    //static instance of customerservice class
    private static final CustomerService instance = new CustomerService();
    //collection that stores customer type objects
    private final Collection<Customer> customers = new ArrayList<>();

    //private constructor to prevent external instantiation
    private CustomerService() {}

    //return static instance of customerservice
    public static CustomerService getInstance() {
        return instance;
    }

    //add new customer to collection
    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.add(customer);
    }

    //customer object based on email
    public Customer getCustomer(String customerEmail) {
        for (Customer customer : customers) {
            if (customer.getEmail().equalsIgnoreCase(customerEmail)) {
                return customer;
            }
        }
        return null;
    }

    //returns all the added information (objects) in the collection
    public Collection<Customer> getAllCustomers() {
        return customers;
    }
}

