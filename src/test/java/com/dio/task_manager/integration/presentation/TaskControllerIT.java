package com.dio.task_manager.integration.presentation;


import com.dio.task_manager.application.dto.TaskRequest;
import com.dio.task_manager.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private UserRepository userRepository;

    private Task savedTask;
    private TaskStatus status;

    @BeforeEach
    void setUp() {
        var user = userRepository.findByUsername("admin").orElseThrow();
        status = taskStatusRepository.findById(1L).orElseThrow();

        taskRepository.save(new Task("Test tarea 1", "desc", LocalDate.of(2025, 7, 10), status, user));
        taskRepository.save(new Task("Test tarea 2", "desc", LocalDate.of(2025, 7, 9), status, user));
        savedTask = taskRepository.save(new Task("Tarea existente", "Descripción", LocalDate.now().plusDays(1), status, user));
    }


    @AfterEach
    void cleanUp() {
        taskRepository.deleteAll();
    }


    @Test
    @WithMockUser(username = "admin")
    void shouldCreateTaskSuccessfully() throws Exception {
        TaskRequest request = new TaskRequest(
                "Tarea integración",
                "Descripción integración",
                LocalDate.now().plusDays(1),
                status.getId(),
                null
        );

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Tarea integración"))
                .andExpect(jsonPath("$.status").value(status.getName()));
    }


    @Test
    @WithMockUser(username = "admin")
    void shouldReturnAllTasksOfUser() throws Exception {

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.content[*].username", everyItem(is("admin"))));

    }

    @Test
    @WithMockUser(username = "admin")
    void shouldUpdateTaskSuccessfully() throws Exception {
        TaskRequest updateRequest = new TaskRequest(
                "Tarea modificada",
                "Descripción modificada",
                LocalDate.now().plusDays(5),
                status.getId(),
                null
        );

        mockMvc.perform(put("/tasks/{id}", savedTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Tarea modificada"))
                .andExpect(jsonPath("$.description").value("Descripción modificada"));
    }

    @Test
    @WithMockUser(username = "admin")
    void shouldDeleteTaskSuccessfully() throws Exception {
        mockMvc.perform(delete("/tasks/{id}", savedTask.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[?(@.id == %s)]", savedTask.getId()).doesNotExist());
    }

    @Test
    @WithMockUser(username = "admin")
    void shouldFilterTasksByTitleAndStatus() throws Exception {
        mockMvc.perform(get("/tasks/filter")
                        .param("title", "Test")
                        .param("statusId", "1")
                        .param("size", "10")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].title", containsString("Test")));
    }

    @Test
    @WithMockUser(username = "admin")
    void shouldFilterTasksByDueBefore() throws Exception {
        mockMvc.perform(get("/tasks/filter")
                        .param("dueBefore", "2025-07-10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.content[0].dueDate", lessThanOrEqualTo("2025-07-10")));
    }

    @Test
    @WithMockUser(username = "admin")
    void shouldCreateTaskForAnotherUserSuccessfully() throws Exception {
        var otherUser = userRepository.findByUsername("user")
                .orElseThrow(() -> new RuntimeException("Usuario 'user' no existe"));

        TaskRequest request = new TaskRequest(
                "Tarea para user",
                "Tarea delegada",
                LocalDate.now().plusDays(3),
                status.getId(),
                otherUser.getId()
        );

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Tarea para user"));

    }

}