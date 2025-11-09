package hackathon.fluttershy.data;


import hackathon.fluttershy.data.dto.RoleDto;
import hackathon.fluttershy.data.dto.UserDto;
import hackathon.fluttershy.data.service.impl.BuildingServiceImpl;
import hackathon.fluttershy.data.service.impl.RoleServiceImpl;
import hackathon.fluttershy.data.service.impl.UserServiceImpl;
import hackathon.fluttershy.logic.SessionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class DataAccessImpl implements DataAccess{
    private UserServiceImpl userServiceImpl;
    private RoleServiceImpl roleServiceImpl;
    private BuildingServiceImpl buildingServiceImpl;


    @Override
    public UserDto getUserByLoginAndPassword(String login, String password) {
        return userServiceImpl.getUserByLoginAndPassword(login, password);
    }

    @Override
    public long[] getUserIDs() {
        List<Long> userList = userServiceImpl.getAllUserId();
        long[] result = new long[userList.size()];
        for (int i = 0; i < userList.size(); i++) {
            result[i] = userList.get(i);
        }
        return result;
    }

    @Override
    public String[] getUserData(long userId) {
        UserDto userDto = userServiceImpl.getUserById(userId);
        String[] userData = {userDto.getLogin(), userDto.getPassword()};
        return userData;
    }

    @Override
    public String getUserLogin(long userID) {
        return "";
    }

    @Override
    public String getUserPassword(long userID) {
        return "";
    }
    @Transactional(readOnly = true)
    @Override
    public long getRoleID(long userID) {
        RoleDto roleDto = userServiceImpl.getRoleByUserId(userID);
        return roleDto.getId();
    }

    @Override
    public String getRoleName(long roleID) {
        return "";
    }

    @Transactional(readOnly = true)
    @Override
    public String[] getRolePermissions(long roleID) {
        RoleDto roleDto = roleServiceImpl.getRoleById(roleID);
        List<String> perms = roleDto.getPermission().getPermission();
        return perms.toArray(new String[0]);
    }

    @Override
    public boolean addUserConnection(SessionType sessionType, String externalID) {
        return false;
    }

    @Override
    public long[] getBuildingIDs() {
        return new long[0];
    }

    @Override
    public String[] getBuildingNames() {
        List<String> names = buildingServiceImpl.getAllBuildingNames();
        return names.toArray(new String[0]);
    }

    @Override
    public String getBuildingDescription(long buildingID) {
        return "";
    }

    @Override
    public long[] getRoomIDs() {
        return new long[0];
    }
}
