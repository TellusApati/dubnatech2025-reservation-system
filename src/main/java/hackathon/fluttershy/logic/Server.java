package hackathon.fluttershy.logic;

import hackathon.fluttershy.logic.datatypes.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Server {

    private DataAccess dataAccess;

    private ArrayList<User> users = new ArrayList<>();


    public Server(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
        updateUserList();
        log("Server Initialized");
    }

    public void updateUserList() {
        for (User user : dataAccess.getUsers()) {
            users.add(user);
        }
    }

    public boolean hasExternalConnection(ConnectionType sessionType, String externalId) {
        return dataAccess.hasSession(sessionType, externalId);
    }

    public Building[] getBuildings() {
        return dataAccess.getBuildings();
    }

    public Room[] getRooms(Building building) {
        return dataAccess.getRooms(building);
    }

    public Booking[] getBookings(String username) {
        return dataAccess.getBookings(username);
    }

    public void bookRoom(Room room, LocalDate date, int hour, ConnectionType connectionType, String externalId) {
        Booking[] bookings = dataAccess.getBookings(room, date);
        Booking targetBooking = new Booking(date, hour, dataAccess.getUser(connectionType, externalId), room);
        for (Booking booking : bookings) {

            if (booking.isEqualTo(targetBooking)) {
                throw new BookingException("Room is booked");
            }
        }
        dataAccess.bookRoom(targetBooking);
    }

    public void removeBooking(Room room, LocalDate date, int hour, ConnectionType connectionType, String externalId) {
        Booking booking = new Booking(date, hour, dataAccess.getUser(connectionType, externalId), room);
        dataAccess.removeBookingUser(booking, dataAccess.getUser(connectionType, externalId));
    }









    public void clearOldBookings() {
        log("Pringles");
    }

    public void log(Exception e) {
        System.out.println("[" + LocalDateTime.now() + "] Exception encountered");
        System.getLogger("test").log(System.Logger.Level.ERROR, "[" + LocalDateTime.now() + "] Exception encountered");
        e.printStackTrace();
    }
    public void log(String text) {
        System.out.println("[" + LocalDateTime.now() + "] " + text);
        System.getLogger("test").log(System.Logger.Level.INFO, "[" + LocalDateTime.now() + "] " + text);
    }
}
