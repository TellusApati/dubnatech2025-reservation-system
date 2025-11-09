package hackathon.fluttershy.data.repository;


import hackathon.fluttershy.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
