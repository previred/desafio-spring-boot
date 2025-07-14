package com.nuevospa.tasksmanagement.controllers;

import com.nuevospa.tasksmanagement.api.UsersApi;
import com.nuevospa.tasksmanagement.model.UserRequest;
import com.nuevospa.tasksmanagement.model.UserResponse;
import com.nuevospa.tasksmanagement.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for managing user information.
 * Provides CRUD operations for users. Accessible only by ADMIN role.
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final UserService userService;

    /**
     * Retrieves all users.
     * Accessible only by users with ROLE_ADMIN.
     * @return ResponseEntity with a list of UserResponse DTOs.
     */
    @Override
    public ResponseEntity<List<UserResponse>> _getAllUsers() {
        log.info("Fetching all users.");
        List<UserResponse> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Retrieves a user by their ID.
     * Accessible only by users with ROLE_ADMIN.
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity with the UserResponse DTO.
     */
    @Override
    public ResponseEntity<UserResponse> _getUserById(@PathVariable Long id) {
        log.info("Fetching user with ID: {}", id);
        UserResponse user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Creates a new user.
     * Accessible only by users with ROLE_ADMIN.
     * @param userRequest DTO containing user details.
     * @return ResponseEntity with the created UserResponse DTO.
     */
    @Override
    public ResponseEntity<UserResponse> _createUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Creating new user: {}", userRequest.getUsername());
        UserResponse createdUser = userService.createUser(userRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Updates an existing user.
     * Accessible only by users with ROLE_ADMIN.
     * @param id The ID of the user to update.
     * @param userRequest DTO containing updated user details.
     * @return ResponseEntity with the updated UserResponse DTO.
     */
    @Override
    public ResponseEntity<UserResponse> _updateUser(@PathVariable Long id,
                                                    @Valid @RequestBody UserRequest userRequest) {
        log.info("Updating user with ID: {}", id);
        UserResponse updatedUser = userService.updateUser(id, userRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Deletes a user by their ID.
     * Accessible only by users with ROLE_ADMIN.
     * @param id The ID of the user to delete.
     * @return ResponseEntity indicating success.
     */
    @Override
    public ResponseEntity<Void> _deleteUser(@PathVariable Long id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}