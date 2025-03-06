package com.provired.challenge.service;

import com.provired.challenge.dto.UserDTO;
import com.provired.challenge.entity.User;
import com.provired.challenge.exception.ResourceNotFoundException;
import com.provired.challenge.exception.UserAlreadyExistsException;
import com.provired.challenge.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    @Transactional
    public UserDTO createUser(String username, String rawPassword) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("El usuario con el nombre: " + username + " ya existe");
                });

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .build();

        return convertToDTO(userRepository.save(user));
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        if (userDTO.getUsername() != null && !userDTO.getUsername().isBlank()) {
            user.setUsername(userDTO.getUsername());
        }

        return convertToDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }
}