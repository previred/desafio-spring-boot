package io.github.srdejo.task_manager.service.impl;

import io.github.srdejo.task_manager.controller.exception.ResourceNotFoundException;
import io.github.srdejo.task_manager.controller.exception.UnauthorizedException;
import io.github.srdejo.task_manager.entity.Usuario;
import io.github.srdejo.task_manager.model.AuthRequest;
import io.github.srdejo.task_manager.model.AuthResponse;
import io.github.srdejo.task_manager.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtServiceImpl jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private AuthRequest authRequest;
    private Usuario usuario;
    private String token;

    @BeforeEach
    void setUp() {
        authRequest = new AuthRequest("testuser", "password");
        usuario = Usuario.builder()
                .id(1L)
                .username("testuser")
                .password("encodedPassword")
                .build();
        token = "jwt.token.here";
    }

    @Test
    void login_Successful() {
        // Arrange
        when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken(usuario)).thenReturn(token);

        // Act
        AuthResponse response = authService.login(authRequest);

        // Assert
        assertNotNull(response);
        assertEquals(token, response.getToken());
        verify(usuarioRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("password", "encodedPassword");
        verify(jwtService).generateToken(usuario);
    }

    @Test
    void login_UserNotFound() {
        // Arrange
        when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> authService.login(authRequest));
        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioRepository).findByUsername("testuser");
        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void login_InvalidPassword() {
        // Arrange
        when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(false);

        // Act & Assert
        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                () -> authService.login(authRequest));
        assertEquals("Credenciales inválidas", exception.getMessage());
        verify(usuarioRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("password", "encodedPassword");
        verify(jwtService, never()).generateToken(any());
    }
}