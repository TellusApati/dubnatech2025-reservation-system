package hackathon.fluttershy.data.mapper;


import hackathon.fluttershy.data.dto.PermissionDto;
import hackathon.fluttershy.data.entity.Permission;

public class PermissionMapper {
    public static PermissionDto mapToPermissionDto(Permission permission) {
        return new PermissionDto(
                permission.getId(),
                permission.getPermission()
        );
    }

    public static Permission mapToPermission(PermissionDto permissionDto) {
        return new Permission(
                permissionDto.getId(),
                permissionDto.getPermission()
        );
    }
}
