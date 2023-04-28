package model;

public class Customer {
    //first name variable
    private String firstName;
    //last name variable
    private String lastName;
    //email variable
    private String email;

    //defining constructor
    //regex to check the email address
    public Customer(String firstName, String lastName, String email) {
        if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    //returning string of the values
    @Override
    public String toString() {
        return "Customer Name: " + firstName + " " + lastName + ", Email: " + email + " ";
    }
}