package hackathon.fluttershy.data.repository;

import hackathon.fluttershy.data.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    @Query("SELECT b.id FROM Building b")
    List<Long> findAllBuildingId();

    @Query("SELECT b.id FROM Building b WHERE b.id = :id")
    String findDescriptionById(@Param("id") Long id);

    @Query("SELECT b.name FROM Building b")
    List<String> findAllBuildingName();
}
