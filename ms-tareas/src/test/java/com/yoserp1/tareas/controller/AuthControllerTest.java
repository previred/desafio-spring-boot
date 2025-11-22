package com.yoserp1.tareas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.yoserp1.tareas.dto.JwtResponseDTO;
import com.yoserp1.tareas.dto.LoginRequestDTO;
import com.yoserp1.tareas.exception.AplicacionException;
import com.yoserp1.tareas.service.IAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthControllerTest {
  @Mock private IAuthService authService;

  @InjectMocks private AuthController authController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Devuelve JWT para credenciales válidas")
  void devuelveJwtParaCredencialesValidas() {
    LoginRequestDTO request = new LoginRequestDTO("user", "password");
    JwtResponseDTO jwtResponse = new JwtResponseDTO("token", "user");
    when(authService.login(request)).thenReturn(jwtResponse);

    ResponseEntity<JwtResponseDTO> response = authController.login(request);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(jwtResponse, response.getBody());
    verify(authService, times(1)).login(request);
  }

  @Test
  @DisplayName("Lanza excepción para credenciales inválidas")
  void lanzaExcepcionParaCredencialesInvalidas() {
    LoginRequestDTO request = new LoginRequestDTO("user", "wrongPassword");
    when(authService.login(request)).thenThrow(new AplicacionException("Credenciales inválidas"));

    assertThrows(AplicacionException.class, () -> authController.login(request));
    verify(authService, times(1)).login(request);
  }

  @Test
  @DisplayName("Devuelve perfil para usuario autenticado")
  void devuelvePerfilParaUsuarioAutenticado() {
    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn("user");
    LoginRequestDTO userProfile = new LoginRequestDTO("user", null);
    when(authService.getPerfil("user")).thenReturn(userProfile);

    ResponseEntity<LoginRequestDTO> response = authController.perfil(userDetails);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(userProfile, response.getBody());
    verify(authService, times(1)).getPerfil("user");
  }

  @Test
  @DisplayName("Lanza excepción para usuario no autenticado")
  void lanzaExcepcionParaUsuarioNoAutenticado() {
    AplicacionException exception =
        assertThrows(AplicacionException.class, () -> authController.perfil(null));

    assertEquals("No autenticado", exception.getMessage());
    verify(authService, never()).getPerfil(anyString());
  }
}
