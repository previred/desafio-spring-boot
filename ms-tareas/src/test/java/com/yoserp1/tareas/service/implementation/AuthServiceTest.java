package com.yoserp1.tareas.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.yoserp1.tareas.dto.JwtResponseDTO;
import com.yoserp1.tareas.dto.LoginRequestDTO;
import com.yoserp1.tareas.exception.AplicacionException;
import com.yoserp1.tareas.security.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthServiceTest {
  @Mock private UserDetailsService userDetailsService;

  @Mock private JwtUtils jwtUtil;

  @Mock private AuthenticationManager authenticationManager;

  @InjectMocks private AuthService authService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Genera token JWT para credenciales válidas")
  void generaTokenJwtParaCredencialesValidas() {
    LoginRequestDTO request = new LoginRequestDTO("user", "password");
    Authentication authentication = mock(Authentication.class);
    UserDetails userDetails = mock(UserDetails.class);

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(userDetails.isEnabled()).thenReturn(true);
    when(userDetails.getUsername()).thenReturn("user");
    when(jwtUtil.generateToken(userDetails)).thenReturn("jwt-token");

    JwtResponseDTO response = authService.login(request);

    assertEquals("jwt-token", response.token());
    assertEquals("user", response.username());
    verify(authenticationManager, times(1))
        .authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtUtil, times(1)).generateToken(userDetails);
  }

  @Test
  @DisplayName("Lanza excepción para credenciales inválidas")
  void lanzaExcepcionParaCredencialesInvalidas() {
    LoginRequestDTO request = new LoginRequestDTO("user", "wrongPassword");

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new BadCredentialsException("Bad credentials"));

    AplicacionException exception =
        assertThrows(AplicacionException.class, () -> authService.login(request));

    assertEquals("Credenciales incorrectas", exception.getMessage());
    verify(authenticationManager, times(1))
        .authenticate(any(UsernamePasswordAuthenticationToken.class));
  }

  @Test
  @DisplayName("Lanza excepción para usuario inactivo")
  void lanzaExcepcionParaUsuarioInactivo() {
    LoginRequestDTO request = new LoginRequestDTO("user", "password");
    Authentication authentication = mock(Authentication.class);
    UserDetails userDetails = mock(UserDetails.class);

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(userDetails.isEnabled()).thenReturn(false);

    AplicacionException exception =
        assertThrows(AplicacionException.class, () -> authService.login(request));

    assertEquals("Error en autenticación", exception.getMessage());
    verify(authenticationManager, times(1))
        .authenticate(any(UsernamePasswordAuthenticationToken.class));
  }

  @Test
  @DisplayName("Obtiene perfil de usuario existente")
  void obtienePerfilDeUsuarioExistente() {
    UserDetails userDetails = mock(UserDetails.class);

    when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
    when(userDetails.isEnabled()).thenReturn(true);
    when(userDetails.getUsername()).thenReturn("user");

    LoginRequestDTO perfil = authService.getPerfil("user");

    assertEquals("user", perfil.username());
    assertEquals("********", perfil.password());
    verify(userDetailsService, times(1)).loadUserByUsername("user");
  }

  @Test
  @DisplayName("Lanza excepción para usuario inexistente al obtener perfil")
  void lanzaExcepcionParaUsuarioInexistenteAlObtenerPerfil() {
    when(userDetailsService.loadUserByUsername("nonexistentUser"))
        .thenThrow(new UsernameNotFoundException("User not found"));

    AplicacionException exception =
        assertThrows(AplicacionException.class, () -> authService.getPerfil("nonexistentUser"));

    assertEquals("Usuario no encontrado: nonexistentUser", exception.getMessage());
    verify(userDetailsService, times(1)).loadUserByUsername("nonexistentUser");
  }

  @Test
  @DisplayName("Lanza excepción para usuario inactivo al obtener perfil")
  void lanzaExcepcionParaUsuarioInactivoAlObtenerPerfil() {
    UserDetails userDetails = mock(UserDetails.class);

    when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
    when(userDetails.isEnabled()).thenReturn(false);

    AplicacionException exception =
        assertThrows(AplicacionException.class, () -> authService.getPerfil("user"));

    assertEquals("Error al obtener el perfil del usuario", exception.getMessage());
    verify(userDetailsService, times(1)).loadUserByUsername("user");
  }
}
