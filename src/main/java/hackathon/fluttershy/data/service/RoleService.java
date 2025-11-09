package hackathon.fluttershy.data.service;


import hackathon.fluttershy.data.dto.RoleDto;

public interface RoleService {
    RoleDto createRole(RoleDto roleDto);

    RoleDto getRoleById(Long roleId);

}
