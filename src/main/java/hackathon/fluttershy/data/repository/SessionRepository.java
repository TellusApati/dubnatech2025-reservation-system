package hackathon.fluttershy.data.repository;


import hackathon.fluttershy.data.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
