package hackathon.fluttershy.data;

import hackathon.fluttershy.logic.ConnectionType;
import hackathon.fluttershy.logic.DataAccess;
import hackathon.fluttershy.logic.datatypes.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestingDataAccess implements DataAccess {


    private final User[] users = new User[] {
            new User("test", "123", new String[] {"BOOKING"}),
            new User("admin", "admin", new String[] {"BOOKING", "EDITING", "ADMIN_PANEL"})
    };

    private final Building[] buildings = new Building[] {
            new Building(1L, "Корпус 1", "Крутой корпус", "test", 9, 21),
            new Building(2L, "Корпус 2", "Так себе корпус", "test", 9, 21),
            new Building(3L, "Корпус А", "Исключительный", "test", 7, 12)
    };

    private final Room[] rooms = new Room[] {
            new Room(1L, "Комната А", "Крутая комната", "test", buildings[0]),
            new Room(2L, "Комната Б", "da комната", "test", buildings[0]),
            new Room(3L, "Комната С", " комната", "test", buildings[0]),
            new Room(4L, "Комната Д", "qr комната", "test", buildings[1]),
            new Room(5L, "Комната Е", "gg комната", "test", buildings[1]),
            new Room(6L, "Комната 1", "sfsd комната", "test", buildings[2])
    };

    private final ArrayList<Booking> bookingList = new ArrayList<>();

    private final Session[] sessions = new Session[] {
            new Session(ConnectionType.TELEGRAM, "kovarovich", users[0])
    };

    @Override
    public User[] getUsers() {
        return users;
    }

    @Override
    public Building[] getBuildings() {
        return buildings;
    }

    @Override
    public Room[] getRooms(Building building) {
        Room[] rooms;

        if (building == buildings[0]) {
            rooms = new Room[] {
                    this.rooms[0],
                    this.rooms[1],
                    this.rooms[2]
            };
        } else
        if (building == buildings[1]) {
            rooms = new Room[] {
                    this.rooms[3],
                    this.rooms[4]
            };
        } else
        if (building == buildings[2]) {
            rooms = new Room[] {
                    this.rooms[5]
            };
        } else {
            rooms = null;
        }
        return rooms;
    }

    @Override
    public Booking[] getBookings(String username) {
        return bookingList.toArray(Booking[]::new);
    }

    @Override
    public Booking[] getBookings(Room room, LocalDate date) {
        List<Booking> bookingList1 = new ArrayList<>();

        for (Booking booking : bookingList) {
            if (booking.getRoom() == room && booking.getDate().getMonth() == date.getMonth() && booking.getDate().getDayOfMonth() == date.getDayOfMonth()) {
                bookingList1.add(booking);
            }
        }
        return bookingList1.toArray(Booking[]::new);
    }

    @Override
    public void bookRoom(Booking booking) {
        bookingList.add(booking);
    }

    @Override
    public void removeBooking(Booking booking) {
        for (int i =0 ; i <bookingList.size(); i ++) {
            Booking currentBooking = bookingList.get(i);
            if (booking.getRoom() == currentBooking.getRoom() && booking.getDate().getMonth() == currentBooking.getDate().getMonth() && booking.getDate().getDayOfMonth() == currentBooking.getDate().getDayOfMonth()) {
                bookingList.remove(i);
                break;
            }
        }
    }

    @Override
    public void removeBookingUser(Booking booking, User user) {
        for (int i =0 ; i <bookingList.size(); i ++) {
            Booking currentBooking = bookingList.get(i);
            if (booking.getRoom() == currentBooking.getRoom() && booking.getDate().getMonth() == currentBooking.getDate().getMonth() && booking.getDate().getDayOfMonth() == currentBooking.getDate().getDayOfMonth()) {
                if (currentBooking.getUser() == user) {
                    bookingList.remove(i);
                }
                break;
            }
        }
    }

    @Override
    public User getUser(ConnectionType connectionType, String externalId) {
        for (Session session : sessions) {
            if (Objects.equals(session.getExternalId(), externalId) && session.getType() == connectionType) {
                return session.getUser();
            }
        }
        return null;
    }

    @Override
    public boolean hasSession(ConnectionType connectionType, String externalId) {
        for (Session session : sessions) {
            if (session.getType() == connectionType && Objects.equals(session.getExternalId(), externalId)) {
                return true;
            }
        }
        return false;
    }
}
