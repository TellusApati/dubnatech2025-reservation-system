package hackathon.fluttershy.data.service.impl;

import hackathon.fluttershy.data.dto.BuildingDto;
import hackathon.fluttershy.data.entity.Building;
import hackathon.fluttershy.data.exception.ResourceNotFoundException;
import hackathon.fluttershy.data.mapper.BuildingMapper;
import hackathon.fluttershy.data.repository.BuildingRepository;
import hackathon.fluttershy.data.service.BuildingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BuildingServiceImpl implements BuildingService {
    private BuildingRepository buildingRepository;

    @Override
    public List<String> getAllBuildingNames() {
        return buildingRepository.findAllBuildingName();
    }

    @Override
    public List<Long> getAllBuildingId() {
        return buildingRepository.findAllBuildingId();
    }

    @Override
    public String getDescriptionById(Long buildingId) {
        return buildingRepository.findDescriptionById(buildingId);
    }

    @Override
    public BuildingDto createBuilding(BuildingDto buildingDto) {
        Building building = BuildingMapper.mapToBuilding(buildingDto);
        Building savedBuilding = buildingRepository.save(building);
        return  BuildingMapper.mapToBuildingDto(savedBuilding);
    }

    @Override
    public void deleteBuilding(Long buildingId) {
        Building building = buildingRepository.findById(buildingId).orElseThrow(
                () -> new ResourceNotFoundException("Building is not exist with given id: " + buildingId)
        );
        buildingRepository.deleteById(buildingId);
    }
}
