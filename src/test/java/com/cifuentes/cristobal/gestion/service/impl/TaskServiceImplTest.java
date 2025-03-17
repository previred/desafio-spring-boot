package com.cifuentes.cristobal.gestion.service.impl;

import com.cifuentes.cristobal.gestion.model.dto.*;
import com.cifuentes.cristobal.gestion.model.entity.*;
import com.cifuentes.cristobal.gestion.repository.StatusRepository;
import com.cifuentes.cristobal.gestion.repository.TaskRepository;
import com.cifuentes.cristobal.gestion.repository.TaskStatusHistoryRepository;
import com.cifuentes.cristobal.gestion.repository.UserRepository;
import com.cifuentes.cristobal.gestion.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Mock
    private StatusRepository statusRepository;
    
    @Mock
    private TaskStatusHistoryRepository taskStatusHistoryRepository;
    
    @Mock
    private UserRepository userRepository;

    private User user;
    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        Status status = new Status("PENDING");

        task = new Task();
        task.setId(1L);
        task.setTitle("Tarea");
        task.setDescription("Descripción");
        task.setUser(user);
        task.setStatus(status);
        task.setDeleted(false);
    }

    @Test
    void createTask_success() {
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Tarea nueva");
        request.setDescription("Desc");

        when(userService.getUserByEmail("test@example.com")).thenReturn(user);
        when(statusRepository.findByName("PENDING")).thenReturn(Optional.of(new Status("PENDING")));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        TaskResponse response = taskService.createTask(request, 1L);

        assertEquals("Tarea", response.getTitle());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_statusNotFound() {
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Tarea");

        when(userService.getUserByEmail("test@example.com")).thenReturn(user);
        when(statusRepository.findByName("INVALID")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.createTask(request, 1L));
    }

    @Test
    void getTasks_success() {
        when(userService.getUserByEmail("test@example.com")).thenReturn(user);
        when(taskRepository.findByUser(user)).thenReturn(List.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<TaskResponse> responses = taskService.getTasks(1L);

        assertEquals(1, responses.size());
    }

    @Test
    void getTaskById_success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponse response = taskService.getTaskById(1L, 1L);

        assertEquals("Tarea", response.getTitle());
    }

    @Test
    void getTaskById_notFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.getTaskById(1L, 1L));
    }

    @Test
    void getTaskById_deleted() {
        task.setDeleted(true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertThrows(RuntimeException.class, () -> taskService.getTaskById(1L, 1L));
    }

    @Test
    void getTaskById_wrongUser() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        task.setUser(anotherUser);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertThrows(RuntimeException.class, () -> taskService.getTaskById(1L, 1L));
    }

    @Test
    void deleteTask_success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L, 1L);

        assertTrue(task.isDeleted());
        verify(taskRepository).save(task);
    }

    @Test
    void deleteTask_wrongUser() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        task.setUser(anotherUser);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertThrows(RuntimeException.class, () -> taskService.deleteTask(1L, 1L));
    }

    @Test
    void updateTask_success() {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("Nuevo título");
        request.setDescription("Nueva descripción");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskStatusHistoryRepository.findByTask(any(Task.class)))
        .thenReturn(Collections.emptyList());

        TaskResponse response = taskService.updateTask(1L, request, 1L);

        assertEquals("Nuevo título", response.getTitle());
    }

    @Test
    void updateTaskStatus_success() {
    	Status newStatus = new Status("IN_PROGRESS");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(statusRepository.findByName("IN_PROGRESS")).thenReturn(Optional.of(newStatus));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse response = taskService.updateTaskStatus(1L, "IN_PROGRESS", 1L);

        assertNotNull(response);
    }

    @Test
    void updateTaskStatus_invalidStatus() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(statusRepository.findByName("INVALID")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.updateTaskStatus(1L, "INVALID", 1L));
    }
}