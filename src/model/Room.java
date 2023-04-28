package model;

import java.util.Objects;

//initialising Room while implementing IRoom interface
public class Room implements IRoom {
    // string for the roomnumber
    private final String roomNumber;
    // price variable in Double type
    private final Double price;
    // RoomType variable (either SINGLE or DOUBLE)
    private final RoomType roomType;

    // defining constructor
    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    // Method to check if room is free
    @Override
    public boolean isFree() {
        return price == 0.0;
    }

    // hash code to check roomNumber implementation and if room is free
    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    // checking if two rooms are equal aka not free
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber);
    }

    // returning string of the values
    @Override
    public String toString() {
        return "Room #" + roomNumber + ", Type: " + roomType + ", Price: $" + String.format("%.2f", price);
    }
}
