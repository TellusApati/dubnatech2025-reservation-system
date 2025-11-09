package hackathon.fluttershy.logic.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Booking {
    private LocalDate date;
    private int hour;
    private User user;
    private Room room;


    public boolean isEqualTo(Booking booking) {
        if (booking.getDate().getMonth() == date.getMonth() && booking.getDate().getDayOfMonth() == date.getDayOfMonth() && booking.getRoom() == room && booking.getHour() == hour) {
            return true;
        }
        return false;
    }
}
