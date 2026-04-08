package io.github.srdejo.task_manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.srdejo.task_manager.config.SecurityConfig;
import io.github.srdejo.task_manager.controller.exception.UnauthorizedException;
import io.github.srdejo.task_manager.model.AuthRequest;
import io.github.srdejo.task_manager.model.AuthResponse;
import io.github.srdejo.task_manager.repository.UsuarioRepository;
import io.github.srdejo.task_manager.service.AuthService;
import io.github.srdejo.task_manager.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private JwtService jwtService;


    @Test
    void login_Successful() throws Exception {
        // Arrange
        AuthRequest request = new AuthRequest("testuser", "password");
        AuthResponse response = new AuthResponse();
        response.setToken("jwt.token.here");

        when(authService.login(any(AuthRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("jwt.token.here"));
    }

    @Test
    void login_InvalidCredentials() throws Exception {
        // Arrange
        AuthRequest request = new AuthRequest("testuser", "wrongpassword");

        when(authService.login(any(AuthRequest.class)))
                .thenThrow(new UnauthorizedException("Credenciales inválidas"));

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.message").value("Credenciales inválidas"));
    }

    @Test
    void login_InvalidRequestBody() throws Exception {
        // Arrange
        String invalidJson = "{\"username\": \"\", \"password\": \"\"}";

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}