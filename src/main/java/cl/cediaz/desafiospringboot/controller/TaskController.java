package cl.cediaz.desafiospringboot.controller;

import cl.cediaz.desafiospringboot.dto.TaskRequest;
import cl.cediaz.desafiospringboot.dto.TaskResponse;
import cl.cediaz.desafiospringboot.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Operaciones relacionadas con las tareas")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Crear una nueva tarea",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.save(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener tarea por ID",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        TaskResponse response = taskService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener tareas por usuario",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponse>> getTasksByUser(@PathVariable Long userId) {
        List<TaskResponse> tasks = taskService.findByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @Operation(
            summary = "Asignar tarea a usuario",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskResponse> assignTaskToUser(@PathVariable Long taskId,
                                                         @PathVariable Long userId) {
        TaskResponse response = taskService.assignTaskToUser(taskId, userId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Actualizar tarea",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                   @Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Eliminar tarea",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener todas las tareas",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping
    public List<TaskResponse> getTasks() {
        return taskService.findAll();
    }
}