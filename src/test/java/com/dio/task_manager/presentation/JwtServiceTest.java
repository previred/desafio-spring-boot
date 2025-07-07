package com.dio.task_manager.presentation;

import com.dio.task_manager.config.properties.JwtProperties;
import com.dio.task_manager.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        JwtProperties props = new JwtProperties();
        props.setExpirationMillis(10000L);
        props.setSecret("d86e91d5f6b849d2823a4c0c6c99a8a3d0e14951a9f7a6df403de48ffb2a79aa");
        jwtService = new JwtService(props);
    }

    @Test
    void shouldGenerateAndExtractUsernameFromToken() {
        String username = "admin";
        String token = jwtService.generateToken(username);

        String extractedUsername = jwtService.extractUsername(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void shouldValidateCorrectToken() {
        String username = "admin";
        String token = jwtService.generateToken(username);

        boolean isValid = jwtService.isTokenValid(token, username);

        assertTrue(isValid);
    }

    @Test
    void shouldInvalidateTokenWithWrongUsername() {
        String username = "admin";
        String token = jwtService.generateToken(username);

        boolean isValid = jwtService.isTokenValid(token, "other");

        assertFalse(isValid);
    }

    @Test
    void shouldDetectExpiredToken() {
        JwtProperties props = new JwtProperties();
        props.setExpirationMillis(1L);
        props.setSecret("d86e91d5f6b849d2823a4c0c6c99a8a3d0e14951a9f7a6df403de48ffb2a79aa");
        var ephemeralService = new JwtService(props);
        String token = ephemeralService.generateToken("admin");
        boolean isValid = ephemeralService.isTokenValid(token, "admin");
        assertFalse(isValid);
    }
}