package com.taskmanagement.controller;

import com.taskmanagement.dto.response.TaskStateResponse;
import com.taskmanagement.service.TaskStateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task-states")
@Tag(name = "Task States", description = "Task state operations")
@SecurityRequirement(name = "bearerAuth")
public class TaskStateController {

    private final TaskStateService taskStateService;

    public TaskStateController(TaskStateService taskStateService) {
        this.taskStateService = taskStateService;
    }

    @GetMapping
    @Operation(summary = "Get all task states")
    public ResponseEntity<List<TaskStateResponse>> getAllTaskStates() {
        List<TaskStateResponse> states = taskStateService.getAllTaskStates();
        return ResponseEntity.ok(states);
    }
}
