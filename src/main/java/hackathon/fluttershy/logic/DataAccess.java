package hackathon.fluttershy.logic;

import hackathon.fluttershy.logic.datatypes.*;
import java.time.LocalDate;


public interface DataAccess {

    User[] getUsers();
    Building[] getBuildings();
    Room[] getRooms(Building building);
    Booking[] getBookings(String username);
    Booking[] getBookings(Room room, LocalDate date);

    void bookRoom(Booking booking);
    void removeBooking(Booking booking);
    void removeBookingUser(Booking booking, User user);


    User getUser(ConnectionType connectionType, String externalId);

    boolean hasSession(ConnectionType connectionType, String externalId);
}
