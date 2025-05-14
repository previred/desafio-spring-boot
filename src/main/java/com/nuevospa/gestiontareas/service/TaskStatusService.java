package com.nuevospa.gestiontareas.service;

import com.nuevospa.gestiontareas.model.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskStatusService {
    List<TaskStatus> findAll();

    Optional<TaskStatus> findById(Long id);
}
