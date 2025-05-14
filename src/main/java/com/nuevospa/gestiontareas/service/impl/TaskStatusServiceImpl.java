package com.nuevospa.gestiontareas.service.impl;

import com.nuevospa.gestiontareas.model.TaskStatus;
import com.nuevospa.gestiontareas.repository.TaskStatusRepository;
import com.nuevospa.gestiontareas.service.TaskStatusService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    public TaskStatusServiceImpl(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }

    @Override
    public List<TaskStatus> findAll() {
        return taskStatusRepository.findAll();
    }

    @Override
    public Optional<TaskStatus> findById(Long id) {
        return taskStatusRepository.findById(id);
    }
}