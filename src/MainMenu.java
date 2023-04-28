//importing packages and models
import java.util.Scanner;
import java.util.Collection;
import java.util.Date;

import model.Customer;
import model.IRoom;
import model.Reservation;
import api.HotelResource;
import service.ReservationService;


public class MainMenu {
    // Scanner object reads inputs from the user
    private static final Scanner scanner = new Scanner(System.in);

    // display menu for the main menu
    public static void displayMenu() {
        String choice = "";
        while (!choice.equals("5")) {
            System.out.println("MAIN MENU");
            System.out.println("1) Find and reserve a room");
            System.out.println("2) See my reservation");
            System.out.println("3) Create an account");
            System.out.println("4) Admin");
            System.out.println("5) Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();
            // based on int input selecting the case
            switch (choice) {
                case "1":
                    reserveARoom();
                    break;
                case "2":
                    seeMyReservation();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    // accessing admin menu
                    AdminMenu.displayMenu();
                    break;
                case "5":
                    // exit
                    System.out.println("Thank you for using the Hotel Reservation System.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void reserveARoom() {
        // checking the entered dates and then finding a room to book
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        Customer customer = HotelResource.getInstance().getCustomer(email);
        if (customer == null) {
            System.out.println("Customer not found with email " + email);
            System.out.println("If you have no account please create an account first.");
            return;
        }

        Date checkInDate = Reservation.promptDate(scanner, "Enter check-in date (mm/dd/yyyy): ");
        Date checkOutDate = Reservation.promptDate(scanner, "Enter check-out date (mm/dd/yyyy): ");

        // Check if the customer already has a reservation for the selected dates
        for (Reservation reservation : HotelResource.getInstance().getCustomersReservations(customer.getEmail())) {
            if (Reservation.datesOverlap(checkInDate, checkOutDate, reservation.getCheckInDate(), reservation.getCheckOutDate())) {
                System.out.print("You already have a booking for these dates. Do you want to book another room? (y/n): ");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("y")) {
                    break;
                } else if (response.equalsIgnoreCase("n")) {
                    return;
                } else {
                    throw new IllegalArgumentException("Invalid input. Please enter 'y' or 'n'.");
                }
            }
        }

        Collection<IRoom> availableRooms = HotelResource.getInstance().findARoom(checkInDate, checkOutDate);

        // Remove any rooms that are already booked for the chosen dates
        for (Reservation reservation : ReservationService.getInstance().getAllReservations()) {
            if (Reservation.datesOverlap(checkInDate, checkOutDate, reservation.getCheckInDate(), reservation.getCheckOutDate())) {
                availableRooms.remove(reservation.getRoom());
            }
        }

        if (availableRooms.isEmpty()) {
            // if no rooms are available, search for recommended rooms on alternative dates
            Date alternateCheckInDate = Reservation.addDays(checkInDate, 7);
            Date alternateCheckOutDate = Reservation.addDays(checkOutDate, 7);
            System.out.println("No rooms available for the specified dates. Here are some alternate dates to consider: ");
            System.out.println("Check-in date: " + alternateCheckInDate);
            System.out.println("Check-out date: " + alternateCheckOutDate);
            Collection<IRoom> alternateRooms = HotelResource.getInstance().findARoom(alternateCheckInDate, alternateCheckOutDate);
            if (alternateRooms.isEmpty()) {
                System.out.println("No rooms available for the alternate dates either.");
            } else {
                System.out.println("Recommended rooms for the alternate dates:");
                for (IRoom room : alternateRooms) {
                    System.out.println(room);
                }
                System.out.print("Enter room number: ");
                String roomNumber = scanner.nextLine();
                IRoom selectedRoom = HotelResource.getInstance().getRoom(roomNumber);
                if (selectedRoom == null) {
                    System.out.println("Invalid room number.");
                } else {
                    Reservation reservation = HotelResource.getInstance().bookARoom(email, selectedRoom, alternateCheckInDate, alternateCheckOutDate);
                    System.out.println("Reservation created:\n" + reservation);
                }
            }
        } else {
            System.out.println("Available rooms:");
            for (IRoom room : availableRooms) {
                System.out.println(room);
            }
            IRoom selectedRoom = null;
            while (selectedRoom == null) {
                System.out.print("Enter room number: ");
                String roomNumber = scanner.nextLine();
                selectedRoom = HotelResource.getInstance().getRoom(roomNumber);
                if (selectedRoom == null) {
                    System.out.println("Invalid room number.");
                } else if (!availableRooms.contains(selectedRoom)) {
                    System.out.println("Room is not available for the chosen dates. Please choose another room.");
                    selectedRoom = null;
                }
            }
            Reservation reservation = HotelResource.getInstance().bookARoom(email, selectedRoom, checkInDate, checkOutDate);
            System.out.println("Reservation created:\n" + reservation);
        }
    }




    public static void seeMyReservation() {
        // checking reservation
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        Customer customer = HotelResource.getInstance().getCustomer(email);
        if (customer == null) {
            System.out.println("Customer not found with email " + email);
            return;
        }
        Collection<Reservation> reservations = HotelResource.getInstance().getCustomersReservations(email);
        if (reservations.isEmpty()) {
            System.out.println("You have no reservations.");
        } else {
            System.out.println("Your reservations:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    public static void createAccount() {
        // creating account for user
        String email = "";
        while (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}")) {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"))
                System.out.println("Invalid email format. Pleae try again.");
        }
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();
        HotelResource.getInstance().createACustomer(email, firstName, lastName);
        System.out.println("Account created!\n" + HotelResource.getInstance().getCustomer(email));
    }
}
