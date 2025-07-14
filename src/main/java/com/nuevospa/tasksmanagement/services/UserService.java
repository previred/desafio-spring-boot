package com.nuevospa.tasksmanagement.services;

import com.nuevospa.tasksmanagement.model.UserRequest;
import com.nuevospa.tasksmanagement.model.UserResponse;

import java.util.List;

/**
 * Interface for the user management service.
 * Defines the contract for CRUD operations on users.
 */
public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(Long id, UserRequest userRequest);
    void deleteUser(Long id);
}
