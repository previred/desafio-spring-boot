package com.nuevospa.gestiontareas.controller;

import com.nuevospa.gestiontareas.dto.AuthRequest;
import com.nuevospa.gestiontareas.dto.AuthResponse;
import com.nuevospa.gestiontareas.model.User;
import com.nuevospa.gestiontareas.service.AuthService;
import com.nuevospa.gestiontareas.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Test
    void login_returnsToken_whenCredentialsAreValid() {

        String passwordTest = "passwordTest";
        String usernameTest = "usernameTest";

        AuthRequest request = new AuthRequest();
        request.setUsername(usernameTest);
        request.setPassword(passwordTest);

        User user = new User();
        user.setUsername(usernameTest);
        user.setPassword(passwordTest);

        UserService userService = mock(UserService.class);
        AuthService authService = mock(AuthService.class);

        when(userService.findByUsername(usernameTest)).thenReturn(Optional.of(user));
        when(authService.generateToken(user)).thenReturn("mocked-token");

        AuthController controller = new AuthController(userService, authService);

        ResponseEntity<AuthResponse> response = controller.login(request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("mocked-token", response.getBody().getToken());
    }

    @Test
    void login_throwsException_whenCredentialsAreInvalid() {
        AuthRequest request = new AuthRequest();

        UserService userService = mock(UserService.class);
        AuthService authService = mock(AuthService.class);

        when(userService.findByUsername("invalid")).thenReturn(Optional.empty());

        AuthController controller = new AuthController(userService, authService);

        assertThrows(RuntimeException.class, () -> controller.login(request));
    }
}