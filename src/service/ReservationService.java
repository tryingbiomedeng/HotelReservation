package service;

import java.util.*;
import model.Customer;
import model.IRoom;
import model.Reservation;

public class ReservationService {
    // static instance of a LinkedHashSet for the rooms
    private static final Set<IRoom> rooms = new LinkedHashSet<>();
    // static instance of list of reservations
    private static final List<Reservation> reservations = new ArrayList<>();
    // static instance of reservation service
    private static final ReservationService instance = new ReservationService();

    // private constructor to prevent external instantiation
    private ReservationService() {}

    // return static instance of reservationservice
    public static ReservationService getInstance() {
        return instance;
    }

    // adding a room to rooms set
    public void addRoom(IRoom room) {
        rooms.add(room);
    }

    // finding room from room set using a roomId
    public IRoom getARoom(String roomId) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    // creates a new reservation with info inputted by the user
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    // returns the rooms as a collection
    public Collection<IRoom> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    // returns the reservations as a collection
    public Collection<Reservation> getAllReservations() {
        return reservations;
    }

    // checking for room availability
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        // Remove rooms which have overlapping reservations
        Collection<IRoom> availableRooms = new ArrayList<>(getAllRooms());
        for (Reservation reservation : reservations) {
            if (availableRooms.contains(reservation.getRoom())
                    && (reservation.getCheckInDate().compareTo(checkOutDate) <= 0
                    && reservation.getCheckOutDate().compareTo(checkInDate) >= 0)) {
                availableRooms.remove(reservation.getRoom());
            }
        }
        return availableRooms;
    }

    // creating a reservation collection for the customer
    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    // prints reservation information
    public void printAllReservation() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}
