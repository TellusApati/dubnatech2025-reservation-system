package hackathon.fluttershy.data.service.impl;


import hackathon.fluttershy.data.dto.RoleDto;
import hackathon.fluttershy.data.dto.UserDto;
import hackathon.fluttershy.data.entity.User;
import hackathon.fluttershy.data.exception.ResourceNotFoundException;
import hackathon.fluttershy.data.mapper.RoleMapper;
import hackathon.fluttershy.data.mapper.UserMapper;
import hackathon.fluttershy.data.repository.UserRepository;
import hackathon.fluttershy.data.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Role is not exist with given id: " + userId));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<Long> getAllUserId() {
        return userRepository.findAllUserId();
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserByLoginAndPassword(String login, String password) {
        User user = userRepository.findUserByLoginAndPassword(login, password);
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public boolean hasUser(String login, String password) {
        Long count = userRepository.countUsersByLoginAndPassword(login, password);
        return count > 0;
    }
    @Transactional(readOnly = true)
    @Override
    public RoleDto getRoleByUserId(Long userId) {
        User user = userRepository.findByIdWithRole(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Role is not exist with given id: " + userId));
        return RoleMapper.mapToRoleDto(user.getRole());
    }
}
