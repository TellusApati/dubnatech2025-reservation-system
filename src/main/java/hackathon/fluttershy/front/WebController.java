package hackathon.fluttershy.front;

import hackathon.fluttershy.data.DataAccessImpl;
import hackathon.fluttershy.data.dto.UserDto;
import hackathon.fluttershy.logic.Server;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WebController {

    private final Server server;
    private final DataAccessImpl dataAccess;


    @GetMapping("/buildings")
    public String[] buildings() {
        return server.getNamesOfBuildings();
    }

    @GetMapping("/greeting")
    public String greeting() {
        return "greeting";
    }

    // ...
    @PostMapping
    public ResponseEntity<UserDto> auth(@RequestBody UserDto userDto) {
        UserDto gettedUser = dataAccess.getUserByLoginAndPassword(userDto.getLogin(), userDto.getPassword());
        return ResponseEntity.ok(gettedUser);
    }


}
