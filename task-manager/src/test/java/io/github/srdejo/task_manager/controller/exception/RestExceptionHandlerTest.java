package io.github.srdejo.task_manager.controller.exception;

import io.github.srdejo.task_manager.config.SecurityConfig;
import io.github.srdejo.task_manager.controller.TaskController;
import io.github.srdejo.task_manager.repository.UsuarioRepository;
import io.github.srdejo.task_manager.service.JwtService;
import io.github.srdejo.task_manager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({TaskController.class, RestExceptionHandler.class})
@Import(SecurityConfig.class)
class RestExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    @WithMockUser
    void handleResourceNotFoundException() throws Exception {
        // Arrange
        when(taskService.findById(1L))
                .thenThrow(new ResourceNotFoundException("Recurso no encontrado"));

        // Act & Assert
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Recurso no encontrado"))
                .andExpect(jsonPath("$.details").doesNotExist());
    }

    @Test
    @WithMockUser
    void handleUnauthorizedException() throws Exception {
        // Arrange
        when(taskService.create(any()))
                .thenThrow(new UnauthorizedException("No autorizado"));

        // Act & Assert
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"test\",\"estadoId\":1}")
                .with(csrf()))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.message").value("No autorizado"));
    }

    @Test
    @WithMockUser
    void handleMethodArgumentNotValidException() throws Exception {
        // Arrange - Spring Boot automatically validates @Valid annotations
        // This test simulates validation errors

        // Act & Assert - This would be triggered by invalid request data
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"\",\"estadoId\":null}")
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void handleGenericException() throws Exception {
        // Arrange
        when(taskService.findAll())
                .thenThrow(new RuntimeException("Error interno del servidor"));

        // Act & Assert
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isInternalServerError());
    }
}