package hackathon.fluttershy.data.mapper;


import hackathon.fluttershy.data.dto.RoomDto;
import hackathon.fluttershy.data.entity.Room;

public class RoomMapper {
    public static RoomDto mapToRoomDto(Room room) {
        return new RoomDto(
                room.getId(),
                room.getName(),
                room.getDescription(),
                room.getImageUrl(),
                BuildingMapper.mapToBuildingDto(room.getBuilding())
        );
    }

    public static Room mapToRoom(RoomDto roomDto) {
        return new Room(
          roomDto.getId(),
          roomDto.getName(),
          roomDto.getDescription(),
          roomDto.getImageUrl(),
          BuildingMapper.mapToBuilding(roomDto.getBuilding())
        );
    }
}
