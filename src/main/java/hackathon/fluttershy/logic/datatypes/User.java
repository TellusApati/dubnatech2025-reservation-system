package hackathon.fluttershy.logic.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private String login;
    private String password;
    private String[] roles;
}
