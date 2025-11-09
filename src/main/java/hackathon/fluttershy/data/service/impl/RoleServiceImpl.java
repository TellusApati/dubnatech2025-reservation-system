package hackathon.fluttershy.data.service.impl;


import hackathon.fluttershy.data.dto.RoleDto;
import hackathon.fluttershy.data.entity.Role;
import hackathon.fluttershy.data.exception.ResourceNotFoundException;
import hackathon.fluttershy.data.mapper.RoleMapper;
import hackathon.fluttershy.data.repository.RoleRepository;
import hackathon.fluttershy.data.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// Сервис создает бин
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    // Внедрено через DI
    private RoleRepository roleRepository;

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = RoleMapper.mapToRole(roleDto);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.mapToRoleDto(savedRole);
    }

    @Override
    public RoleDto getRoleById(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role is not exist with given id: " + roleId));
        return RoleMapper.mapToRoleDto(role);
    }
}
