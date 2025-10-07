package com.nuevo.taskmanager.controller;


import com.nuevo.taskmanager.model.Task;
import com.nuevo.taskmanager.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Log4j2
public class TasksApiController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        log.info("Petición para listar todas las tareas");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id) {
        log.info("Petición para obtener tarea con id={}", id);
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid Task task) {
        log.info("Petición para crear nueva tarea: {}", task);
        Task created = taskService.createTask(task);
        log.info("Tarea creada con ID {}", created.getId());
        return ResponseEntity.ok(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody @Valid Task task) {
        log.info("Petición para actualizar tarea con id={} con datos {}", id, task);
        Task updated = taskService.updateTask(id, task);
        log.info("Tarea con id={} actualizada correctamente", id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        log.info("Petición para eliminar tarea con id={}", id);
        taskService.deleteTask(id);
        log.info("Tarea con id={} eliminada", id);
        return ResponseEntity.noContent().build();
    }
}
