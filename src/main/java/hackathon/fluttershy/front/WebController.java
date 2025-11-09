package hackathon.fluttershy.front;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hackathon.fluttershy.logic.Server;
import hackathon.fluttershy.logic.datatypes.Building;
import hackathon.fluttershy.logic.datatypes.Room;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    private final Server server;

    public WebController(Server server) {
        this.server = server;
    }

    @RequestMapping(value = "/buildings", produces = "application/json")
    public String getBuildings() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(server.getBuildings());
    }

    @RequestMapping(value = "/rooms", produces = "application/json")
    public Room[] getRoomsOfBuilding() {
        return null;
    }




    @RequestMapping(value = "/greeting", produces = "application/json")
    public String greeting() {
        return "greeting";
    }


}
