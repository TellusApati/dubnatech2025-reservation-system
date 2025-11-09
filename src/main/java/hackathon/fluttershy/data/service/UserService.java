package hackathon.fluttershy.data.service;


import hackathon.fluttershy.data.dto.RoleDto;
import hackathon.fluttershy.data.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUserById(Long userId);

    List<Long> getAllUserId();

    UserDto createUser(UserDto userDto);

    UserDto getUserByLoginAndPassword(String login, String password);

    boolean hasUser(String login, String password);

    RoleDto getRoleByUserId(Long userId);
}
