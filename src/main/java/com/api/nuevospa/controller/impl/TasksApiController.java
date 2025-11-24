package com.api.nuevospa.controller.impl;

import com.api.nuevospa.controller.TasksApi;
import com.api.nuevospa.model.TaskPatchRequest;
import com.api.nuevospa.model.TaskRequest;
import com.api.nuevospa.model.TaskResponse;
import com.api.nuevospa.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TasksApiController implements TasksApi {

    private final TaskService taskService;

    @Override
    public ResponseEntity<List<TaskResponse>> tasksGet() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @Override
    public ResponseEntity<Void> tasksIdDelete(Integer id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskResponse> tasksIdGet(Integer id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @Override
    public ResponseEntity<TaskResponse> tasksIdPatch(Integer id, TaskPatchRequest taskPatchRequest) {
        return ResponseEntity.ok(taskService.updatePatch(id, taskPatchRequest));
    }

    @Override
    public ResponseEntity<TaskResponse> tasksIdPut(Integer id, TaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.update(id, taskRequest));
    }

    @Override
    public ResponseEntity<TaskResponse> tasksPost(TaskRequest taskRequest) {
        return ResponseEntity.status(201).body(taskService.create(taskRequest));
    }
}