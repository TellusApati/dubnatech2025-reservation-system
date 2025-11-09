package hackathon.fluttershy.logic.datatypes;

import hackathon.fluttershy.logic.ConnectionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Session {
    private ConnectionType type;
    private String externalId;
    private User user;
}
