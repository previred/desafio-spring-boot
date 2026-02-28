package cl.previred.taskmanager.infrastructure.controller;

import cl.previred.taskmanager.application.usecase.*;
import cl.previred.taskmanager.domain.model.Task;
import cl.previred.taskmanager.domain.model.TaskStatus;
import cl.previred.taskmanager.infrastructure.controller.dto.CreateTaskRequest;
import cl.previred.taskmanager.infrastructure.controller.dto.TaskResponse;
import cl.previred.taskmanager.infrastructure.controller.dto.UpdateTaskRequest;
import cl.previred.taskmanager.infrastructure.controller.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetAllTasksUseCase getAllTasksUseCase;
    private final GetTaskByIdUseCase getTaskByIdUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody CreateTaskRequest request) {

        Task task = createTaskUseCase.execute(
                request.title(),
                request.description(),
                request.userId(),
                parseStatus(request.status())
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TaskMapper.toResponse(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAll() {

        List<TaskResponse> response = getAllTasksUseCase.execute()
                .stream()
                .map(TaskMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id) {

        Task task = getTaskByIdUseCase.execute(id);

        return ResponseEntity.ok(TaskMapper.toResponse(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateTaskRequest request
    ) {

        Task task = updateTaskUseCase.execute(
                id,
                request.title(),
                request.description(),
                request.status() != null ? parseStatus(request.status()) : null
        );

        return ResponseEntity.ok(TaskMapper.toResponse(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        deleteTaskUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

    private TaskStatus parseStatus(String status) {
        if (status == null || status.isBlank()) {
            return TaskStatus.PENDING;
        }

        try {
            return TaskStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido: " + status);
        }
    }
}
