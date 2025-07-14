package com.nuevospa.tasksmanagement.services;

import com.nuevospa.tasksmanagement.model.AuthResponse;
import com.nuevospa.tasksmanagement.model.RegisterRequest;
import com.nuevospa.tasksmanagement.model.RegisterResponse;

/**
 * Interface for the authentication service.
 * Defines the contract for user authentication operations.
 */
public interface AuthService {

    /**
     * Register user
     * @param registerRequest DTO containing register user details.
     * @return A JWT token if register is successful.
     */
    RegisterResponse registerUser(RegisterRequest registerRequest);

    /**
     * Authenticates a user with the given username and password, then generates a JWT.
     *
     * @param username The user's username.
     * @param password The user's password.
     * @return A JWT token if authentication is successful.
     * @throws org.springframework.security.authentication.BadCredentialsException if authentication fails.
     */
    AuthResponse authenticateUser(String username, String password);
}
