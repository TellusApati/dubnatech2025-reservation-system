package hackathon.fluttershy.data;


import hackathon.fluttershy.data.dto.UserDto;
import hackathon.fluttershy.logic.SessionType;

public interface DataAccess {

    UserDto getUserByLoginAndPassword(String login, String password);
    long[] getUserIDs();
    String[] getUserData(long userID);
    String getUserLogin(long userID);
    String getUserPassword(long userID);
    long getRoleID(long userID);

    String getRoleName(long roleID);
    String[] getRolePermissions(long roleID);



    boolean addUserConnection(SessionType sessionType, String externalID);

    long[] getBuildingIDs();
    String[] getBuildingNames();
    String getBuildingDescription(long buildingID);

    long[] getRoomIDs();
}
