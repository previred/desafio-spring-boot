package com.nuevospa.gestiontareas.service.impl;

import com.nuevospa.gestiontareas.model.Task;
import com.nuevospa.gestiontareas.repository.TaskRepository;
import com.nuevospa.gestiontareas.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskServiceImpl(taskRepository);
    }

    @Test
    void findAll_returnsListOfTasks() {
        Task task = new Task();
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<Task> result = taskService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void findById_existingId_returnsTask() {
        Task task = new Task();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void findById_nonexistentId_returnsEmpty() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.findById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void create_savesAndReturnsTask() {
        Task task = new Task();
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.create(task);
        assertEquals(task, result);
    }

    @Test
    void update_existingId_updatesTask() {
        Task task = new Task();
        when(taskRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.update(1L, task);
        assertEquals(task, result);
        assertEquals(1L, task.getId());
    }

    @Test
    void update_nonexistentId_throwsException() {
        Task task = new Task();
        when(taskRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> taskService.update(1L, task));
    }

    @Test
    void delete_callsRepositoryDeleteById() {
        taskService.delete(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByUser_returnsUserTasks() {
        Task task = new Task();
        when(taskRepository.findByUserUsername("user")).thenReturn(List.of(task));

        List<Task> result = taskService.findByUser("user");
        assertEquals(1, result.size());
    }
}