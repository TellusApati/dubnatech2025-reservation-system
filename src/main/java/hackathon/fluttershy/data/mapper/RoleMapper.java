package hackathon.fluttershy.data.mapper;


import hackathon.fluttershy.data.dto.RoleDto;
import hackathon.fluttershy.data.entity.Role;

// Сделать интерфейс для маппера
public class RoleMapper {
    public static RoleDto mapToRoleDto(Role role){
        return new RoleDto(
                role.getId(),
                role.getName(),
                PermissionMapper.mapToPermissionDto(role.getPermission())
        );
    }

    public static Role mapToRole(RoleDto roleDto) {
        return new Role(
                roleDto.getId(),
                roleDto.getName(),
                PermissionMapper.mapToPermission(roleDto.getPermission())
        );
    }
}
