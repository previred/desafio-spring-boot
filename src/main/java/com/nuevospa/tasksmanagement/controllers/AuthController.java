package com.nuevospa.tasksmanagement.controllers;

import com.nuevospa.tasksmanagement.api.AuthApi;
import com.nuevospa.tasksmanagement.model.AuthResponse;
import com.nuevospa.tasksmanagement.model.LoginRequest;
import com.nuevospa.tasksmanagement.model.RegisterRequest;
import com.nuevospa.tasksmanagement.model.RegisterResponse;
import com.nuevospa.tasksmanagement.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for user authentication.
 * Handles login requests and issues JWT tokens.
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    /**
     * Registers a new user and returns a JWT token upon successful registration.
     *
     * @param registerRequest DTO containing username, password, email, and role.
     * @return ResponseEntity with AuthResponse containing the JWT token.
     */
    @Override
    public ResponseEntity<RegisterResponse> _registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Attempting to register user: {}", registerRequest.getUsername());
        RegisterResponse response = authService.registerUser(registerRequest);
        log.info("User {} registered successfully.", registerRequest.getUsername());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Authenticates a user and returns a JWT token upon successful login.
     *
     * @param loginRequest DTO containing username and password.
     * @return ResponseEntity with AuthResponse containing the JWT token.
     */
    @Override
    public ResponseEntity<AuthResponse> _loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Attempting to authenticate user: {}", loginRequest.getUsername());
        AuthResponse response = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        log.info("User {} authenticated successfully.", loginRequest.getUsername());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
