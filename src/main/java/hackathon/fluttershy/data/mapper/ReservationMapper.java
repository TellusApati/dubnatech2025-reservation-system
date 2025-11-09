package hackathon.fluttershy.data.mapper;


import hackathon.fluttershy.data.dto.ReservationDto;
import hackathon.fluttershy.data.entity.Reservation;

public class ReservationMapper {
    public static ReservationDto mapToReservationDto(Reservation reservation){
        return new ReservationDto(
          reservation.getId(),
          reservation.getStartDate(),
          reservation.getLength(),
          UserMapper.mapToUserDto(reservation.getUser()),
          RoomMapper.mapToRoomDto(reservation.getRoom())
        );
    }

    public static Reservation mapToReservation(ReservationDto reservationDto) {
        return new Reservation(
          reservationDto.getId(),
          reservationDto.getStartDate(),
          reservationDto.getLength(),
          UserMapper.mapToUser(reservationDto.getUser()),
          RoomMapper.mapToRoom(reservationDto.getRoom())
        );
    }
}
