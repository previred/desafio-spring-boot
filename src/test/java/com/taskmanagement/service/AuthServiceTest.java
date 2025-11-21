package com.taskmanagement.service;

import com.taskmanagement.dto.request.LoginRequest;
import com.taskmanagement.dto.request.RegisterRequest;
import com.taskmanagement.dto.response.AuthResponse;
import com.taskmanagement.dto.response.UserResponse;
import com.taskmanagement.entity.User;
import com.taskmanagement.exception.DuplicateResourceException;
import com.taskmanagement.mapper.UserMapper;
import com.taskmanagement.repository.UserRepository;
import com.taskmanagement.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Tests")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private AuthService authService;

    private User testUser;
    private UserResponse testUserResponse;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userRepository, userMapper, passwordEncoder, jwtService, authenticationManager);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("mgarcia");
        testUser.setEmail("maria@test.com");
        testUser.setPassword("encodedPassword");
        testUser.setFullName("María García");
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setUpdatedAt(LocalDateTime.now());

        testUserResponse = new UserResponse(1L, "mgarcia", "maria@test.com", "María García",
                testUser.getCreatedAt(), testUser.getUpdatedAt());
    }

    @Test
    @DisplayName("Should register new user successfully")
    void shouldRegisterUser() {
        RegisterRequest request = new RegisterRequest("mgarcia", "maria@test.com", "password123", "María García");

        when(userRepository.existsByUsername("mgarcia")).thenReturn(false);
        when(userRepository.existsByEmail("maria@test.com")).thenReturn(false);
        when(userMapper.toEntity(request)).thenReturn(testUser);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toResponse(testUser)).thenReturn(testUserResponse);

        UserResponse result = authService.register(request);

        assertThat(result.username()).isEqualTo("mgarcia");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when username already exists")
    void shouldThrowExceptionWhenUsernameExists() {
        RegisterRequest request = new RegisterRequest("mgarcia", "maria@test.com", "password123", "María García");

        when(userRepository.existsByUsername("mgarcia")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("Username already exists");
    }

    @Test
    @DisplayName("Should login user and return token")
    void shouldLoginUser() {
        LoginRequest request = new LoginRequest("mgarcia", "password123");

        when(userRepository.findByUsername("mgarcia")).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwt-token");
        when(jwtService.getExpirationTime()).thenReturn(3600000L);
        when(userMapper.toResponse(testUser)).thenReturn(testUserResponse);

        AuthResponse result = authService.login(request);

        assertThat(result.accessToken()).isEqualTo("jwt-token");
        assertThat(result.tokenType()).isEqualTo("Bearer");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("Should throw exception on invalid credentials")
    void shouldThrowExceptionOnInvalidCredentials() {
        LoginRequest request = new LoginRequest("mgarcia", "wrongpassword");

        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager).authenticate(any());

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BadCredentialsException.class);
    }
}
