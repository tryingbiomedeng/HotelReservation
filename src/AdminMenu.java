//importing packages and models
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import model.Customer;
import model.FreeRoom;
import model.IRoom;
import model.Room;
import model.RoomType;
import model.Reservation;
import service.ReservationService;
import api.AdminResource;

public class AdminMenu {
    //Scanner object reads inputs from the user
    private static Scanner scanner = new Scanner(System.in);
    //admin menu display
    public static void displayMenu() {
        System.out.println("ADMIN MENU");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        //based on int input selecting the case
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> {
                displayAllCustomers();
                displayMenu();
            }
            case "2" -> {
                displayAllRooms();
                displayMenu();
            }
            case "3" -> {
                displayAllReservations();
                displayMenu();
            }
            case "4" -> {
                addRoom();
                displayMenu();
            }
            case "5" -> MainMenu.displayMenu();
            default -> {
                System.out.println("Invalid selection. Please try again.");
                displayMenu();
            }
        }
    }
    //display all the customers
    private static void displayAllCustomers() {
        if (AdminResource.getInstance().getAllCustomers().isEmpty()) {
            System.out.println("There are no customers.");
            return;
        }
        System.out.println("ALL CUSTOMERS:");
        for (Customer customer : AdminResource.getInstance().getAllCustomers()) {
            System.out.println(customer);
        }
    }
    //display all the rooms
    private static void displayAllRooms() {
        if (AdminResource.getInstance().getAllRooms().isEmpty()) {
            System.out.println("There are no rooms.");
            return;
        }
        System.out.println("ALL ROOMS:");
        for (IRoom room : AdminResource.getInstance().getAllRooms()) {
            System.out.println(room);
        }
    }
    //display all reservations
    private static void displayAllReservations() {
        if (AdminResource.getInstance().getAllCustomers().isEmpty()) {
            System.out.println("There are no reservations.");
            return;
        }
        System.out.println("ALL RESERVATIONS:");
        for (Customer customer : AdminResource.getInstance().getAllCustomers()) {
            Collection<Reservation> reservations = ReservationService.getInstance().getCustomersReservation(customer);
            if (!reservations.isEmpty()) {
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            } else {
                System.out.println("No reservations for " + customer);
            }
        }
    }
    //adding a room
    private static void addRoom() {
        System.out.println("ADDING A ROOM:");
        String roomNumber;
        while (true) {
            System.out.print("Room number: ");
            try {
                roomNumber = scanner.nextLine();
                Integer.parseInt(roomNumber);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid room number. Please enter a valid number.");
            }
        }
        // Check if the room number already exists
        if (Boolean.TRUE.equals(AdminResource.getInstance().roomExists(roomNumber))) {
            System.out.println("Room number already exists. Please enter a different room number.");
            addRoom(); // Call the method recursively to allow the user to enter a new room number
            return; // Stop execution of this instance of the method to prevent adding the room with a duplicate number
        }

        double price = -1.0;
        while (price < 0.0) {
            System.out.print("Price per night: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
                if (price < 0.0)
                    System.out.println("Invalid price. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please try again.");
            }
        }
        String typeString = "";
        while (!typeString.equals("S") && !typeString.equals("D")) {
            System.out.print("Room type (S for single bed, D for double bed): ");
            typeString = scanner.nextLine();
            if (!typeString.equals("S") && !typeString.equals("D"))
                System.out.println("Invalid room type. Please try again.");

        }
        RoomType roomType = typeString.equals("S") ? RoomType.SINGLE : RoomType.DOUBLE;
        IRoom room = new Room(roomNumber, price, roomType);
        if (price == 0.0)
            room = new FreeRoom(roomNumber, roomType);
        AdminResource.getInstance().addRoom(List.of(room));
        System.out.println("Room added:\n" + room);
    }
}
