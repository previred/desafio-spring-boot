package com.nuevospa.tasksmanagement.services.impl;

import com.nuevospa.tasksmanagement.entities.User;
import com.nuevospa.tasksmanagement.exceptions.ResourceNotFoundException;
import com.nuevospa.tasksmanagement.mappers.UserMapper;
import com.nuevospa.tasksmanagement.model.UserRequest;
import com.nuevospa.tasksmanagement.model.UserResponse;
import com.nuevospa.tasksmanagement.repositories.UserRepository;
import com.nuevospa.tasksmanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing user-related business logic.
 * Handles CRUD operations for users.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves all users from the database.
     * @return A list of UserResponse DTOs.
     */
    public List<UserResponse> getAllUsers() {
        log.debug("Fetching all users.");
        return userRepository.findAll().stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a user by their ID.
     * @param id The ID of the user.
     * @return UserResponse DTO.
     * @throws ResourceNotFoundException if the user is not found.
     */
    public UserResponse getUserById(Long id) {
        log.debug("Fetching user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserMapper.convertToDto(user);
    }

    /**
     * Creates a new user.
     * @param userRequest DTO containing details for the new user.
     * @return UserResponse DTO of the created user.
     * @throws DataIntegrityViolationException if username already exists.
     */
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        log.info("Creating new user with username: {}", userRequest.getUsername());

        // Check if username already exists
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new DataIntegrityViolationException("Username '" + userRequest.getUsername() + "' already exists.");
        }

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))  // Encode password
                .role(userRequest.getRole()) // Ensure role is valid (e.g., "ROLE_USER", "ROLE_ADMIN")
                .build();

        User savedUser = userRepository.save(user);
        log.info("User created with ID: {}", savedUser.getId());
        return UserMapper.convertToDto(savedUser);
    }

    /**
     * Updates an existing user.
     * @param id The ID of the user to update.
     * @param userRequest DTO containing updated user details.
     * @return UserResponse DTO of the updated user.
     * @throws ResourceNotFoundException if the user is not found.
     * @throws DataIntegrityViolationException if attempting to change username to an existing one.
     */
    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        log.info("Updating user with ID: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        // Check if username is changed and already exists for another user
        if (!existingUser.getUsername().equals(userRequest.getUsername()) &&
                userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new DataIntegrityViolationException("Username '" + userRequest.getUsername() + "' already exists for another user.");
        }

        existingUser.setUsername(userRequest.getUsername());
        // Only update password if a new password is provided in the request
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        existingUser.setRole(userRequest.getRole());

        User updatedUser = userRepository.save(existingUser);
        log.info("User ID {} updated successfully.", updatedUser.getId());
        return UserMapper.convertToDto(updatedUser);
    }

    /**
     * Deletes a user by their ID.
     * @param id The ID of the user to delete.
     * @throws ResourceNotFoundException if the user is not found.
     */
    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
        log.info("User ID {} deleted successfully.", id);
    }
}
