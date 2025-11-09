package hackathon.fluttershy.data.repository;


import hackathon.fluttershy.data.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
