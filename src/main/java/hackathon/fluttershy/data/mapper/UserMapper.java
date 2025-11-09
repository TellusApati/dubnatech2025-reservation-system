package hackathon.fluttershy.data.mapper;


import hackathon.fluttershy.data.dto.UserDto;
import hackathon.fluttershy.data.entity.User;

public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                RoleMapper.mapToRoleDto(user.getRole())
        );
    }

    public static User mapToUser(UserDto userdto) {
        return new User(
                userdto.getId(),
                userdto.getLogin(),
                userdto.getPassword(),
                RoleMapper.mapToRole(userdto.getRole())
        );
    }
}
