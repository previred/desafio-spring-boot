package com.nuevospa.gestiontareas.service;

import com.nuevospa.gestiontareas.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll();
    Optional<Task> findById(Long id);
    Task create(Task task);
    Task update(Long id, Task task);
    void delete(Long id);
    List<Task> findByUser(String username);
}