package hackathon.fluttershy.data.service;


import hackathon.fluttershy.data.dto.RoomDto;

import java.util.List;

public interface RoomService {
    List<Long> getAllRoomId();

    RoomDto createRoom(RoomDto roomDto);

    void deleteRoom(Long roomId);
}
