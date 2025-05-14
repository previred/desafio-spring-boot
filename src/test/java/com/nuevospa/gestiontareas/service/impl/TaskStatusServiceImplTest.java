package com.nuevospa.gestiontareas.service.impl;

import com.nuevospa.gestiontareas.model.TaskStatus;
import com.nuevospa.gestiontareas.repository.TaskStatusRepository;
import com.nuevospa.gestiontareas.service.TaskStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskStatusServiceImplTest {

    private TaskStatusRepository taskStatusRepository;
    private TaskStatusService taskStatusService;

    @BeforeEach
    void setUp() {
        taskStatusRepository = mock(TaskStatusRepository.class);
        taskStatusService = new TaskStatusServiceImpl(taskStatusRepository);
    }

    @Test
    void findAll_returnsListOfTaskStatuses() {
        TaskStatus status = new TaskStatus();
        when(taskStatusRepository.findAll()).thenReturn(List.of(status));

        List<TaskStatus> result = taskStatusService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void findById_existingId_returnsTaskStatus() {
        TaskStatus status = new TaskStatus();
        when(taskStatusRepository.findById(1L)).thenReturn(Optional.of(status));

        Optional<TaskStatus> result = taskStatusService.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void findById_nonexistentId_returnsEmpty() {
        when(taskStatusRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<TaskStatus> result = taskStatusService.findById(99L);
        assertFalse(result.isPresent());
    }
}