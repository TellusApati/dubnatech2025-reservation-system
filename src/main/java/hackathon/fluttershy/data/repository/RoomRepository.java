package hackathon.fluttershy.data.repository;


import hackathon.fluttershy.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r.id FROM Room r")
    List<Long> findAllRoomId();
}
