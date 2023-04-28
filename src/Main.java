//importing packages
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // main intro message
        System.out.println("Welcome to the Hotel Reservation Service!");
        String choice = "";
        while (!choice.equals("1") && !choice.equals("2")) {
            // options of service
            System.out.println("What would you like to do: ");
            System.out.println("1) Main menu");
            System.out.println("2) Leave");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();
            switch (choice) {
                case "1" -> {
                    // display main menu
                    MainMenu.displayMenu();
                    break;
                } case "2" -> {
                    // leave the reservation service
                    System.out.println("Exiting Hotel Reservation Service...");
                    System.exit(0);
                    break;
                } default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
