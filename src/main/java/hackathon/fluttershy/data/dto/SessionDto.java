package hackathon.fluttershy.data.dto;


import hackathon.fluttershy.data.entity.User;
import hackathon.fluttershy.logic.SessionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {
    private Long id;
    private SessionType type;
    private String externalId;
    private UserDto user;
}
