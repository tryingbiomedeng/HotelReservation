package model;

/**
 * Packages found from
 * https://www.javaguides.net/2019/07/list-of-useful-core-java-packages.html
 */

//importing packages
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Calendar;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class Reservation {
    //customer variable
    private final Customer customer;
    //room variable
    private final IRoom room;
    //checkin date variable
    private final Date checkInDate;
    //checkout date variable
    private final Date checkOutDate;
    //defining constructor
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    //get methods for variables
    public Customer getCustomer() {
        return customer;
    }
    public IRoom getRoom() {
        return room;
    }
    public Date getCheckInDate() {
        return checkInDate;
    }
    public Date getCheckOutDate() {
        return checkOutDate;
    }
    //returning string of the values
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return "Reservation\n" + customer +
                "\n" + room +
                "\nCheck in: " + checkInDate.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate().format(formatter) +
                "\nCheck out: " + checkOutDate.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate().format(formatter);
    }
    //validating the inputted dates from the user

    public static Date promptDate(Scanner scanner, String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        Date date = null;
        boolean isValidDate = false;

        while (!isValidDate) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                LocalDate localDate = LocalDate.parse(input, formatter);
                date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                isValidDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
        return date;
    }
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    public static boolean datesOverlap(Date checkInDate1, Date checkOutDate1, Date checkInDate2, Date checkOutDate2) {
        return checkInDate1.before(checkOutDate2) && checkOutDate1.after(checkInDate2);
    }

}
