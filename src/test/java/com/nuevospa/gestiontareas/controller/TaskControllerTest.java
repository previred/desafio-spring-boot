package com.nuevospa.gestiontareas.controller;

import com.nuevospa.gestiontareas.dto.CreateTaskRequest;
import com.nuevospa.gestiontareas.dto.UpdateTaskRequest;
import com.nuevospa.gestiontareas.model.Task;
import com.nuevospa.gestiontareas.model.TaskStatus;
import com.nuevospa.gestiontareas.model.User;
import com.nuevospa.gestiontareas.service.TaskService;
import com.nuevospa.gestiontareas.service.TaskStatusService;
import com.nuevospa.gestiontareas.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    private TaskService taskService;
    private UserService userService;
    private TaskStatusService taskStatusService;
    private TaskController controller;
    private Authentication auth;

    @BeforeEach
    void setUp() {
        taskService = mock(TaskService.class);
        userService = mock(UserService.class);
        taskStatusService = mock(TaskStatusService.class);
        controller = new TaskController(taskService, userService, taskStatusService);
        auth = mock(Authentication.class);
    }

    @Test
    void getAll_returnsTasks() {
        Task task = new Task();
        when(taskService.findAll()).thenReturn(List.of(task));

        ResponseEntity<List<Task>> response = controller.getAll();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getById_found() {
        Task task = new Task();
        when(taskService.findById(1L)).thenReturn(Optional.of(task));

        ResponseEntity<Object> response = controller.getById(1L);
        assertEquals(200, response.getStatusCode().value());
        assertInstanceOf(Task.class, response.getBody());
    }

    @Test
    void getById_notFound() {
        when(taskService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = controller.getById(1L);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void create_validRequest_returnsCreated() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Test");
        request.setDescription("Desc");

        User user = new User(); user.setUsername("test");
        TaskStatus state = new TaskStatus(); state.setId(1L);
        Task task = Task.builder().id(1L).title("Test").description("Desc").creationDate(LocalDateTime.now()).user(user).state(state).build();

        when(auth.getName()).thenReturn("test");
        when(userService.findByUsername("test")).thenReturn(Optional.of(user));
        when(taskStatusService.findById(1L)).thenReturn(Optional.of(state));
        when(taskService.create(any())).thenReturn(task);

        ResponseEntity<Object> response = controller.create(request, auth);
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void create_userNotFound_returnsBadRequest() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Test");

        when(auth.getName()).thenReturn("test");
        when(userService.findByUsername("test")).thenReturn(Optional.empty());

        ResponseEntity<Object> response = controller.create(request, auth);
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void update_valid_returnsUpdated() {
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setTitle("Updated");
        request.setDescription("Desc");

        Task task = new Task();
        when(taskService.findById(1L)).thenReturn(Optional.of(task));
        when(taskService.update(eq(1L), any())).thenReturn(task);

        ResponseEntity<Object> response = controller.update(1L, request);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void update_notFound_returnsBadRequest() {
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setTitle("Updated");

        when(taskService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = controller.update(1L, request);
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void delete_found_returnsNoContent() {
        Task task = new Task();
        when(taskService.findById(1L)).thenReturn(Optional.of(task));

        ResponseEntity<Object> response = controller.delete(1L);
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void delete_notFound_returnsNotFound() {
        when(taskService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = controller.delete(1L);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void getUserTasks_returnsUserTasks() {
        when(auth.getName()).thenReturn("test");
        when(taskService.findByUser("test")).thenReturn(Collections.emptyList());

        ResponseEntity<List<Task>> response = controller.getUserTasks(auth);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}