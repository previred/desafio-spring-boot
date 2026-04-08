package io.github.srdejo.task_manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.srdejo.task_manager.config.SecurityConfig;
import io.github.srdejo.task_manager.controller.exception.ResourceNotFoundException;
import io.github.srdejo.task_manager.repository.UsuarioRepository;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import io.github.srdejo.task_manager.model.TaskEstado;
import io.github.srdejo.task_manager.model.TaskRequest;
import io.github.srdejo.task_manager.model.TaskResponse;
import io.github.srdejo.task_manager.service.TaskService;
import io.github.srdejo.task_manager.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import(SecurityConfig.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getTasks_Successful() throws Exception {
        // Arrange
        TaskResponse task1 = new TaskResponse();
        task1.setId(1L);
        task1.setTitulo("Task 1");
        task1.setEstado("PENDIENTE");

        TaskResponse task2 = new TaskResponse();
        task2.setId(2L);
        task2.setTitulo("Task 2");
        task2.setEstado("EN_PROGRESO");

        List<TaskResponse> tasks = List.of(task1, task2);

        when(taskService.findAll()).thenReturn(tasks);

        // Act & Assert
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Task 1"))
                .andExpect(jsonPath("$[1].titulo").value("Task 2"));
    }

    @Test
    @WithMockUser
    void createTask_Successful() throws Exception {
        // Arrange
        TaskRequest request = new TaskRequest("New Task", TaskEstado.NUMBER_1);
        request.setDescripcion("Task description");

        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitulo("New Task");
        response.setDescripcion("Task description");
        response.setEstado("PENDIENTE");

        when(taskService.create(any(TaskRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("New Task"));
    }

    @Test
    @WithMockUser
    void createTask_UserNotAuthenticated() throws Exception {
        // Arrange
        TaskRequest request = new TaskRequest("New Task", TaskEstado.NUMBER_1);

        when(taskService.create(any(TaskRequest.class)))
                .thenThrow(new ResourceNotFoundException("Usuario no autenticado"));

        // Act & Assert
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Usuario no autenticado"));
    }

    @Test
    @WithMockUser
    void createTask_EstadoNotFound() throws Exception {
        // Arrange
        TaskRequest request = new TaskRequest("New Task", TaskEstado.NUMBER_1);

        when(taskService.create(any(TaskRequest.class)))
                .thenThrow(new ResourceNotFoundException("Estado no encontrado"));

        // Act & Assert
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Estado no encontrado"));
    }

    @Test
    @WithMockUser
    void getTaskById_Successful() throws Exception {
        // Arrange
        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitulo("Test Task");
        response.setEstado("PENDIENTE");

        when(taskService.findById(1L)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Test Task"));
    }

    @Test
    @WithMockUser
    void getTaskById_TaskNotFound() throws Exception {
        // Arrange
        when(taskService.findById(1L))
                .thenThrow(new ResourceNotFoundException("Tarea no encontrada"));

        // Act & Assert
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Tarea no encontrada"));
    }

    @Test
    @WithMockUser
    void updateTask_Successful() throws Exception {
        // Arrange
        TaskRequest request = new TaskRequest("Updated Task", TaskEstado.NUMBER_2);
        request.setDescripcion("Updated description");

        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitulo("Updated Task");
        response.setEstado("EN_PROGRESO");

        when(taskService.update(eq(1L), any(TaskRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.titulo").value("Updated Task"))
                .andExpect(jsonPath("$.estado").value("EN_PROGRESO"));
    }

    @Test
    @WithMockUser
    void updateTask_TaskNotFound() throws Exception {
        // Arrange
        TaskRequest request = new TaskRequest("Updated Task", TaskEstado.NUMBER_2);

        when(taskService.update(eq(1L), any(TaskRequest.class)))
                .thenThrow(new ResourceNotFoundException("Tarea no encontrada"));

        // Act & Assert
        mockMvc.perform(put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Tarea no encontrada"));
    }

    @Test
    @WithMockUser
    void updateTask_EstadoNotFound() throws Exception {
        // Arrange
        TaskRequest request = new TaskRequest("Updated Task", TaskEstado.NUMBER_2);

        when(taskService.update(eq(1L), any(TaskRequest.class)))
                .thenThrow(new ResourceNotFoundException("Estado no encontrado"));

        // Act & Assert
        mockMvc.perform(put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Estado no encontrado"));
    }

    @Test
    @WithMockUser
    void deleteTask_Successful() throws Exception {
        // Arrange - No exception thrown means success

        // Act & Assert
        mockMvc.perform(delete("/tasks/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void deleteTask_TaskNotFound() throws Exception {
        // Arrange
        doThrow(new ResourceNotFoundException("Tarea no encontrada"))
                .when(taskService).delete(1L);

        // Act & Assert
        mockMvc.perform(delete("/tasks/1").with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Tarea no encontrada"));
    }

    @Test
    @WithMockUser
    void createTask_InvalidRequestBody() throws Exception {
        // Arrange
        String invalidJson = "{\"titulo\": \"\", \"estadoId\": null}";

        // Act & Assert
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson)
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }
}