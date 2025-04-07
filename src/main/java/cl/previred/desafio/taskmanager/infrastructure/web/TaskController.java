package cl.previred.desafio.taskmanager.infrastructure.web;

import cl.previred.desafio.taskmanager.domain.model.BasicTask;
import cl.previred.desafio.taskmanager.domain.model.Task;
import cl.previred.desafio.taskmanager.domain.usecase.CreateTaskUseCase;
import cl.previred.desafio.taskmanager.infrastructure.web.dto.CreateTaskRequest;
import cl.previred.desafio.taskmanager.infrastructure.web.dto.TaskDtoMapper;
import cl.previred.desafio.taskmanager.infrastructure.web.dto.TaskResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
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

    private Map<String, String> buildErrorResponse(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
