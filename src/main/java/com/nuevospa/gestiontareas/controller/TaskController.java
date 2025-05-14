package com.nuevospa.gestiontareas.controller;

import com.nuevospa.gestiontareas.dto.CreateTaskRequest;
import com.nuevospa.gestiontareas.dto.UpdateTaskRequest;
import com.nuevospa.gestiontareas.model.Task;
import com.nuevospa.gestiontareas.model.TaskStatus;
import com.nuevospa.gestiontareas.model.User;
import com.nuevospa.gestiontareas.service.TaskService;
import com.nuevospa.gestiontareas.service.TaskStatusService;
import com.nuevospa.gestiontareas.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final TaskStatusService taskStatusService;

    public TaskController(TaskService taskService, UserService userService, TaskStatusService taskStatusService) {
        this.taskService = taskService;
        this.userService = userService;
        this.taskStatusService = taskStatusService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAll() {
        List<Task> tasks = taskService.findAll();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return taskService.findById(id)
                .<ResponseEntity<Object>>map(task -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Task with ID not found: " + id));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateTaskRequest request, Authentication authentication) {
        try {
            String username = authentication.getName();
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            TaskStatus state = taskStatusService.findById(1L)
                    .orElseThrow(() -> new IllegalArgumentException("State not found"));

            Task task = Task.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .creationDate(LocalDateTime.now())
                    .user(user)
                    .state(state)
                    .build();

            Task created = taskService.create(task);
            return ResponseEntity.created(URI.create("/api/tasks/" + created.getId())).body(created);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody UpdateTaskRequest request) {
        try {
            Task existing = taskService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Task not found"));

            User user = (request.getUserId() != null)
                    ? userService.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"))
                    : existing.getUser();

            TaskStatus state = (request.getStatusId() != null)
                    ? taskStatusService.findById(request.getStatusId())
                    .orElseThrow(() -> new IllegalArgumentException("State not found"))
                    : existing.getState();

            Task taskToUpdate = Task.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .creationDate(LocalDateTime.now())
                    .user(user)
                    .state(state)
                    .build();

            Task updated = taskService.update(id, taskToUpdate);
            return ResponseEntity.ok(updated);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la tarea");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            taskService.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
            taskService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with ID " + id + " not found");
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<Task>> getUserTasks(Authentication authentication) {
        String username = authentication.getName();
        List<Task> tasks = taskService.findByUser(username);
        return ResponseEntity.ok(tasks);
    }
}