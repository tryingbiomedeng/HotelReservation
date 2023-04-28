package api;

//importing packages and models
import java.util.*;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

public class HotelResource {
    //static instance of hotelresource class
    private static HotelResource instance = new HotelResource();
    //return static instance of hotelresource
    public static HotelResource getInstance() {
        return instance;
    }
    //returning customer email
    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }
    //creating a new customer with input from the user
    public void createACustomer(String email, String firstName, String lastName) {
        CustomerService.getInstance().addCustomer(email, firstName, lastName);
    }
    //get room based on room number
    public IRoom getRoom(String roomNumber) {
        return ReservationService.getInstance().getARoom(roomNumber);
    }
    //book room for customer
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = CustomerService.getInstance().getCustomer(customerEmail);
        if (customer != null)
            return ReservationService.getInstance().reserveARoom(customer, room, checkInDate, checkOutDate);
        else
            return null;
    }
    //collection of reservations for the customer
    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = CustomerService.getInstance().getCustomer(customerEmail);
        if (customer != null)
            return ReservationService.getInstance().getCustomersReservation(customer);
        else
            return null;
    }
    //collection of rooms with their availability based on inputted dates
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return ReservationService.getInstance().findRooms(checkIn, checkOut);
    }
}
