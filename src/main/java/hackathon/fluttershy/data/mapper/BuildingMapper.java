package hackathon.fluttershy.data.mapper;


import hackathon.fluttershy.data.dto.BuildingDto;
import hackathon.fluttershy.data.entity.Building;

public class BuildingMapper {
    public static BuildingDto mapToBuildingDto(Building building) {
        return new BuildingDto(
          building.getId(),
          building.getName(),
          building.getDescription(),
          building.getImageUrl()
        );
    }

    public static Building mapToBuilding(BuildingDto buildingDto) {
        return new Building(
          buildingDto.getId(),
          buildingDto.getName(),
          buildingDto.getDescription(),
          buildingDto.getImageUrl()
        );
    }
}
