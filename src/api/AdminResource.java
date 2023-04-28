package api;

//importing packages and models
import java.util.Collection;
import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class AdminResource {
    //static instance of adminresource class
    private static AdminResource instance = new AdminResource();
    //return static instance of adminresource
    public static AdminResource getInstance() {
        return instance;
    }
    //returning customer email
    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }
    //adding a room to IRoom collection
    public void addRoom(Collection<IRoom> rooms) {
        for (IRoom room : rooms)
            ReservationService.getInstance().addRoom(room);
    }
    public boolean roomExists(String roomNumber) {
        Collection<IRoom> rooms = ReservationService.getInstance().getAllRooms();
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return true;
            }
        }
        return false;
    }
    //getting all the rooms from the Room collection
    public Collection<IRoom> getAllRooms() {
        return ReservationService.getInstance().getAllRooms();
    }
    //getting all the customers from the Customer collection
    public Collection<Customer> getAllCustomers() {
        return CustomerService.getInstance().getAllCustomers();
    }
    //getting all the reservations from the reservation collection
    public void displayAllReservations() {
        ReservationService.getInstance().printAllReservation();
    }
}
