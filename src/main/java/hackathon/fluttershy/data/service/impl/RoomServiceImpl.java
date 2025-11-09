package hackathon.fluttershy.data.service.impl;


import hackathon.fluttershy.data.dto.RoomDto;
import hackathon.fluttershy.data.entity.Room;
import hackathon.fluttershy.data.exception.ResourceNotFoundException;
import hackathon.fluttershy.data.mapper.RoomMapper;
import hackathon.fluttershy.data.repository.RoomRepository;
import hackathon.fluttershy.data.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;

    @Override
    public List<Long> getAllRoomId() {
        return roomRepository.findAllRoomId();
    }

    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        Room room = RoomMapper.mapToRoom(roomDto);
        Room savedRoom = roomRepository.save(room);
        return  RoomMapper.mapToRoomDto(savedRoom);
    }

    @Override
    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new ResourceNotFoundException("Room is not exist with given id: " + roomId)
        );
        roomRepository.deleteById(roomId);
    }
}
