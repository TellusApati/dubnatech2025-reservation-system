package hackathon.fluttershy.data;

import hackathon.fluttershy.data.dto.UserDto;
import hackathon.fluttershy.logic.SessionType;

import java.util.List;

public class TestingDataAccess implements DataAccess {


    private final long[] userIDs = new long[] { 1, 2 };
    private final String[] userLogin = new String[] { "test", "admin" };
    private final String[] userPassword = new String[] { "123", "admin" };
    private final long[] userRoleID = new long[] { 1, 2 };

    private final String[] roleName = new String[] { "User", "Admin" };
    private final String[] rolePermissions = new String[] { "BOOK", "EDIT_ROOM" };


    @Override
    public UserDto getUserByLoginAndPassword(String login, String password) {
        return null;
    }

    @Override
    public long[] getUserIDs() {
        return userIDs;
    }

    @Override
    public String[] getUserData(long userID) {
        return new String[] { userLogin[(int) userID-1], userPassword[(int) userID-1] };
    }

    @Override
    public String getUserLogin(long userID) {
        return userLogin[(int) (userID-1)];
    }

    @Override
    public String getUserPassword(long userID) {
        return userPassword[(int) (userID-1)];
    }

    @Override
    public long getRoleID(long userID) {
        return userRoleID[(int) (userID-1)];
    }

    @Override
    public String getRoleName(long roleID) {
        return roleName[(int) (roleID-1)];
    }

    @Override
    public String[] getRolePermissions(long roleID) {
        return new String[] { rolePermissions[(int) (roleID-1)] };
    }


    @Override
    public boolean addUserConnection(SessionType sessionType, String externalID) {
        return false;
    }

    @Override
    public long[] getBuildingIDs() {
        return new long[] {1, 2};
    }

    @Override
    public String[] getBuildingNames() {
        return new String[]{ "Корпус 1", "Корпус А" };
    }

    @Override
    public String getBuildingDescription(long buildingID) {
        return "";
    }

    @Override
    public long[] getRoomIDs() {
        return new long[] {1,2,3};
    }
}
