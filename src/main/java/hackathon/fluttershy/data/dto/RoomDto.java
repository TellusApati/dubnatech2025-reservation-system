package hackathon.fluttershy.data.dto;



import hackathon.fluttershy.data.entity.Building;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private BuildingDto building;
}
