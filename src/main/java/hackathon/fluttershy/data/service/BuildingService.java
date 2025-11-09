package hackathon.fluttershy.data.service;


import hackathon.fluttershy.data.dto.BuildingDto;

import java.util.List;

public interface BuildingService {
    List<String> getAllBuildingNames();

    List<Long> getAllBuildingId();

    String getDescriptionById(Long buildingId);

    BuildingDto createBuilding(BuildingDto buildingDto);

    void deleteBuilding(Long buildingId);
}
