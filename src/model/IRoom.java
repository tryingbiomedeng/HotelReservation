package model;

public interface IRoom {
    //string to for accessing the room number
    String getRoomNumber();
    //accessing room price as a double type
    Double getRoomPrice();
    //accessing the enumerated RoomType
    RoomType getRoomType();
    //boolean value for if room is free or not
    boolean isFree();
}
