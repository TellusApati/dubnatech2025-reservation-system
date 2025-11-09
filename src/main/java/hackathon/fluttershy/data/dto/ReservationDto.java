package hackathon.fluttershy.data.dto;


import hackathon.fluttershy.data.entity.Room;
import hackathon.fluttershy.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private LocalDateTime startDate;
    private Long length;
    private UserDto user;
    private RoomDto room;
}
