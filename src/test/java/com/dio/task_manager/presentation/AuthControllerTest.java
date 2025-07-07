package com.dio.task_manager.presentation;


import com.dio.task_manager.infrastructure.security.JwtService;
import com.dio.task_manager.presentation.exception.AuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private AuthController authController;

    @BeforeEach
    void setUp() {
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        JwtService jwtService = mock(JwtService.class);
        authController = new AuthController(authenticationManager, jwtService);
    }

    @ParameterizedTest
    @CsvSource({
            "null,password",
            "'   ',password",
            "admin,null",
            "admin,'   '"
    })
    void shouldThrowExceptionWhenUsernameOrPasswordInvalid(String username, String password) {
        if ("null".equals(username)) username = null;
        if ("null".equals(password)) password = null;

        String finalUsername = username;
        String finalPassword = password;
        AuthenticationException ex = assertThrows(AuthenticationException.class, () ->
                authController.login(finalUsername, finalPassword)
        );
        assertEquals("Username o password vacíos", ex.getMessage());
    }
}