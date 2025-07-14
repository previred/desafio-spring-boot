package com.nuevospa.tasksmanagement.services.impl;

import com.nuevospa.tasksmanagement.entities.User;
import com.nuevospa.tasksmanagement.exceptions.DuplicateResourceException;
import com.nuevospa.tasksmanagement.mappers.RegisterMapper;
import com.nuevospa.tasksmanagement.model.AuthResponse;
import com.nuevospa.tasksmanagement.model.RegisterRequest;
import com.nuevospa.tasksmanagement.model.RegisterResponse;
import com.nuevospa.tasksmanagement.repositories.UserRepository;
import com.nuevospa.tasksmanagement.security.JwtTokenProvider;
import com.nuevospa.tasksmanagement.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for handling user authentication.
 * Implements the IAuthService interface.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse registerUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateResourceException("Username '" + request.getUsername() + "' already taken.");
        }
        User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(newUser);
        log.info("User registered successfully: {}", newUser.getUsername());
        return RegisterMapper.convertToDto(savedUser);
    }

    /**
     * Authenticates a user with the given username and password, then generates a JWT.
     *
     * @param username The user's username.
     * @param password The user's password.
     * @return A JWT token if authentication is successful.
     * @throws org.springframework.security.authentication.BadCredentialsException if authentication fails.
     */
    @Override // Implementing method from AuthService
    public AuthResponse authenticateUser(String username, String password) {
        log.debug("Authenticating user: {}", username);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenProvider.generateToken(authentication);
        log.debug("Generated JWT for user: {}", username);
        return new AuthResponse(jwtToken);
    }
}

