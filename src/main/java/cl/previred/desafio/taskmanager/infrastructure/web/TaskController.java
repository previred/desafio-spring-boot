package cl.previred.desafio.taskmanager.infrastructure.web;

import cl.previred.desafio.taskmanager.domain.model.BasicTask;
import cl.previred.desafio.taskmanager.domain.model.Task;
import cl.previred.desafio.taskmanager.domain.model.TaskStatus;
import cl.previred.desafio.taskmanager.domain.usecase.CreateTaskUseCase;
import cl.previred.desafio.taskmanager.domain.usecase.DeleteTaskUseCase;
import cl.previred.desafio.taskmanager.domain.usecase.ListTasksUseCase;
import cl.previred.desafio.taskmanager.domain.usecase.UpdateTaskUseCase;
import cl.previred.desafio.taskmanager.infrastructure.web.dto.CreateTaskRequest;
import cl.previred.desafio.taskmanager.infrastructure.web.dto.TaskDtoMapper;
import cl.previred.desafio.taskmanager.infrastructure.web.dto.TaskResponse;
import cl.previred.desafio.taskmanager.infrastructure.web.dto.UpdateTaskRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final ListTasksUseCase listTasksUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;

    private final TaskDtoMapper taskDtoMapper;

    @PostMapping
    public ResponseEntity<?> createTask(
            @Valid @RequestBody CreateTaskRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(buildErrorResponse(bindingResult));
        }

        Task task = taskDtoMapper.toDomain(request);

        BasicTask createdTask = (BasicTask) createTaskUseCase.execute(task);

        return ResponseEntity.ok(taskDtoMapper.toResponse(createdTask));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> listAllTasks() {

        List<Task> tasks = listTasksUseCase.execute();

        List<TaskResponse> response =
                tasks.stream()
                .map(task -> (BasicTask) task)
                .map(taskDtoMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable String taskId,
            @Valid @RequestBody UpdateTaskRequest request) {

        Task task = BasicTask.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(TaskStatus.getByValue(request.getStatus()))
                .build();

        Task updatedTask = updateTaskUseCase.execute(taskId, task);

        return ResponseEntity.ok(taskDtoMapper.toResponse((BasicTask) updatedTask));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable String taskId) {

        boolean wasDeleted = deleteTaskUseCase.execute(taskId);

        if (!wasDeleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }


    private Map<String, String> buildErrorResponse(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
