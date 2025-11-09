package hackathon.fluttershy.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
}
