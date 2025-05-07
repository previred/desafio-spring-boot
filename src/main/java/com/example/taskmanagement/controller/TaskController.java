package com.example.taskmanagement.controller;

import com.example.taskmanagement.api.ApiApi;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.model.LoginRequest;
import com.example.taskmanagement.model.LoginResponse;
import com.example.taskmanagement.model.TaskRequest;
import com.example.taskmanagement.security.JwtService;
import com.example.taskmanagement.service.TaskService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskController implements ApiApi {

    private final TaskService taskService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public TaskController(TaskService taskService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.taskService = taskService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse().token(token));
    }

    @Override
    public ResponseEntity<List<com.example.taskmanagement.model.Task>> getAllTasks() {
        List<com.example.taskmanagement.model.Task> tasks = taskService.getAllTasks().stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    @Override
    public ResponseEntity<com.example.taskmanagement.model.Task> getTaskById(Long id) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
        return ResponseEntity.ok(convertToDto(task));
    }

    @Override
    public ResponseEntity<com.example.taskmanagement.model.Task> createTask(TaskRequest taskRequest) {
        Task task = taskService.createTask(taskRequest);
        return ResponseEntity.status(201).body(convertToDto(task));
    }

    @Override
    public ResponseEntity<com.example.taskmanagement.model.Task> updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskService.updateTask(id, taskRequest);
        return ResponseEntity.ok(convertToDto(task));
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long id) {
        taskService.deleteTask(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Message", "La tarea se encontró y se borró");
        return ResponseEntity.status(204)
                .headers(headers)
                .build();
    }

    private com.example.taskmanagement.model.Task convertToDto(Task task) {
        com.example.taskmanagement.model.Task taskDto = new com.example.taskmanagement.model.Task();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setUserId(task.getUser().getId());

        com.example.taskmanagement.model.TaskStatus statusDto = new com.example.taskmanagement.model.TaskStatus();
        statusDto.setId(task.getStatus().getId());
        statusDto.setName(task.getStatus().getName());
        taskDto.setStatus(statusDto);

        return taskDto;
    }
}