package cl.cediaz.desafiospringboot.service;

import cl.cediaz.desafiospringboot.dto.UserRequest;
import cl.cediaz.desafiospringboot.dto.UserUpdateRequest;
import cl.cediaz.desafiospringboot.entity.Role;
import cl.cediaz.desafiospringboot.entity.User;
import cl.cediaz.desafiospringboot.exception.ResourceNotFoundException;
import cl.cediaz.desafiospringboot.mapper.UserMapper;
import cl.cediaz.desafiospringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       RoleService roleService,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + id + " no encontrado"));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con username " + username + " no encontrado"));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User createUser(UserRequest request) {
        Role role = roleService.findByName(request.role());
        User user = userMapper.toEntity(request, role);
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserUpdateRequest request) {
        User existingUser = findById(id);
        Role role = roleService.findById(request.roleId());
        User user = userMapper.toEntity(request, existingUser, role);

        return userRepository.save(user);
    }


    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + id));
        userRepository.delete(user);
    }

    public void updateLastLogin(User user) {
        user.setFechaUltLogin(LocalDateTime.now());
        userRepository.save(user);
    }
}