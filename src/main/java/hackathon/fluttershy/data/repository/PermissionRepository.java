package hackathon.fluttershy.data.repository;


import hackathon.fluttershy.data.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
