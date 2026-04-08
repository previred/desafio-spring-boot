package io.github.srdejo.task_manager.service.impl;

import io.github.srdejo.task_manager.controller.exception.ResourceNotFoundException;
import io.github.srdejo.task_manager.entity.Usuario;
import io.github.srdejo.task_manager.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuario;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .build();

        userDetails = User.builder()
                .username("testuser")
                .password("password")
                .roles("USER")
                .build();
    }

    @Test
    void getCurrentUser_Successful() {
        // Arrange
        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(userDetails);
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(usuario));

            // Act
            Usuario result = usuarioService.getCurrentUser();

            // Assert
            assertNotNull(result);
            assertEquals(usuario, result);
            verify(securityContext).getAuthentication();
            verify(authentication, times(1)).getPrincipal();
            verify(usuarioRepository).findByUsername("testuser");
        }
    }

    @Test
    void getCurrentUser_NoAuthentication() {
        // Arrange
        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            when(securityContext.getAuthentication()).thenReturn(null);
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Act & Assert
            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                    () -> usuarioService.getCurrentUser());
            assertEquals("Usuario no autenticado", exception.getMessage());
            verify(securityContext).getAuthentication();
            verify(authentication, never()).getPrincipal();
            verify(usuarioRepository, never()).findByUsername(any());
        }
    }

    @Test
    void getCurrentUser_InvalidPrincipal() {
        // Arrange
        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn("invalid");
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Act & Assert
            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                    () -> usuarioService.getCurrentUser());
            assertEquals("Usuario no autenticado", exception.getMessage());
            verify(securityContext).getAuthentication();
            verify(authentication).getPrincipal();
            verify(usuarioRepository, never()).findByUsername(any());
        }
    }

    @Test
    void getCurrentUser_UserNotFound() {
        // Arrange
        try (MockedStatic<SecurityContextHolder> securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(userDetails);
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.empty());

            // Act & Assert
            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                    () -> usuarioService.getCurrentUser());
            assertEquals("Usuario no encontrado", exception.getMessage());
            verify(securityContext).getAuthentication();
            verify(authentication, times(1)).getPrincipal();
            verify(usuarioRepository).findByUsername("testuser");
        }
    }
}