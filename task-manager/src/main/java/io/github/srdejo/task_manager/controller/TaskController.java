package io.github.srdejo.task_manager.controller;

import io.github.srdejo.task_manager.api.TasksApi;
import io.github.srdejo.task_manager.model.TaskRequest;
import io.github.srdejo.task_manager.model.TaskResponse;
import io.github.srdejo.task_manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi {

    private final TaskService taskService;

    @Override
    public ResponseEntity<List<TaskResponse>> getTasks() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @Override
    public ResponseEntity<TaskResponse> createTask(TaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.create(taskRequest));
    }

    @Override
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @Override
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                   @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.update(id, taskRequest));
    }

    @Override
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
