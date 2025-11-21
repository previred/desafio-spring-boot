package com.taskmanagement.service;

import com.taskmanagement.dto.request.CreateTaskRequest;
import com.taskmanagement.dto.response.TaskResponse;
import com.taskmanagement.dto.response.TaskStateResponse;
import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.TaskState;
import com.taskmanagement.entity.User;
import com.taskmanagement.exception.ResourceNotFoundException;
import com.taskmanagement.mapper.TaskMapper;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.TaskStateRepository;
import com.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TaskService Tests")
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskStateRepository taskStateRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    private TaskService taskService;

    private User testUser;
    private TaskState todoState;
    private Task testTask;
    private TaskResponse testTaskResponse;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository, taskStateRepository, userRepository, taskMapper);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("mgarcia");

        todoState = new TaskState("TODO", "Pendiente");
        todoState.setId(1L);

        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setDescription("Description");
        testTask.setUser(testUser);
        testTask.setState(todoState);
        testTask.setCreatedAt(LocalDateTime.now());
        testTask.setUpdatedAt(LocalDateTime.now());

        testTaskResponse = new TaskResponse(1L, "Test Task", "Description",
                new TaskStateResponse(1L, "TODO", "Pendiente"),
                1L, null, testTask.getCreatedAt(), testTask.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create task successfully")
    void shouldCreateTask() {
        CreateTaskRequest request = new CreateTaskRequest("Test Task", "Description", 1L, null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(taskStateRepository.findById(1L)).thenReturn(Optional.of(todoState));
        when(taskMapper.toEntity(request, testUser, todoState)).thenReturn(testTask);
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);
        when(taskMapper.toResponse(testTask)).thenReturn(testTaskResponse);

        TaskResponse result = taskService.createTask(request, 1L);

        assertThat(result.title()).isEqualTo("Test Task");
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw exception when state not found")
    void shouldThrowExceptionWhenStateNotFound() {
        CreateTaskRequest request = new CreateTaskRequest("Test Task", "Description", 999L, null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(taskStateRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.createTask(request, 1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Task state not found");
    }

    @Test
    @DisplayName("Should get tasks by user with pagination")
    void shouldGetTasksByUser() {
        Page<Task> taskPage = new PageImpl<>(List.of(testTask));

        when(taskRepository.findByUserId(eq(1L), any(PageRequest.class))).thenReturn(taskPage);
        when(taskMapper.toResponse(testTask)).thenReturn(testTaskResponse);

        var result = taskService.getTasksByUser(1L, null, 0, 10);

        assertThat(result.content()).hasSize(1);
        assertThat(result.totalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should get task by id and user")
    void shouldGetTaskByIdAndUser() {
        when(taskRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testTask));
        when(taskMapper.toResponse(testTask)).thenReturn(testTaskResponse);

        TaskResponse result = taskService.getTaskById(1L, 1L);

        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should delete task")
    void shouldDeleteTask() {
        when(taskRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testTask));

        taskService.deleteTask(1L, 1L);

        verify(taskRepository).delete(testTask);
    }
}
