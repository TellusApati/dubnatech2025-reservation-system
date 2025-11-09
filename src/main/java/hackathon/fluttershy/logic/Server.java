package hackathon.fluttershy.logic;

import hackathon.fluttershy.data.DataAccess;
import org.springframework.security.core.userdetails.User;

public class Server {

    private DataAccess dataAccess;
    private Users users;


    private final String passwordEncoding = "noop";

    public Server(DataAccess dataAccess, Users users) {
        this.dataAccess = dataAccess;
        this.users = users;
        updateUserList();
    }

    public void updateUserList() {
        for (long id : dataAccess.getUserIDs()) {
            String[] userData = dataAccess.getUserData(id);

            users.addUser(
                    User.builder()
                            .username(userData[0])
                            .password("{"+ passwordEncoding +"}" + userData[1])
                            .roles(dataAccess.getRolePermissions(dataAccess.getRoleID(id)))
                            .build()
            );
        }
    }

    public String[] getNamesOfBuildings() {
        return dataAccess.getBuildingNames();
    }

    public void LogInfo() {
        System.out.println("Pringing");
    }
}
