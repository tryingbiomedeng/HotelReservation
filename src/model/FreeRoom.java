package model;

//extending the room class
public class FreeRoom extends Room {
    //defining constructor
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }
    //returning string of the values
    @Override
    public String toString() {
        return "Free " + super.toString();
    }
}

