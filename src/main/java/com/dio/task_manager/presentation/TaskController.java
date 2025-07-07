package com.dio.task_manager.presentation;

import com.dio.task_manager.application.dto.TaskDTO;
import com.dio.task_manager.application.dto.TaskRequest;
import com.dio.task_manager.application.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @Operation(summary = "Listar tareas del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tareas listadas correctamente")
    })
    public ResponseEntity<Page<TaskDTO>> getTasks(@ParameterObject  @PageableDefault Pageable pageable, Authentication authentication) {
        String username = authentication.getName();
        LOGGER.info("getTasks|in. Tareas para usuario: {}", username);
        var response = ResponseEntity.ok(taskService.getTasks(username, pageable));
        LOGGER.info("getTasks|out. Tareas para usuario: {}", username);
        return response;
    }

    @PostMapping
    @Operation(summary = "Crear nueva tarea para el usuario autenticado o un usuario especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarea creada correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskRequest request, Authentication authentication) {
        String username = authentication.getName();
        LOGGER.info("createTask|in. Crear tarea para usuario: {}", username);
        var response = taskService.createTask(request, username);
        LOGGER.info("createTask|out. Crear tarea para usuario: {}", username);
        return response;
    }


    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una tarea si pertenece al usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada o acceso denegado"),
            @ApiResponse(responseCode = "403", description = "Usuario no autorizado")
    })
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskRequest request, Authentication authentication) {
        String username = authentication.getName();
        LOGGER.info("updateTask|in. Actualizar tarea id={} para usuario: {}", id, username);
        var response = taskService.updateTask(id, request, username);
        LOGGER.info("updateTask|out. Actualizar tarea id={} para usuario: {}", id, username);
        return response;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tarea si pertenece al usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarea eliminada correctamente"),
            @ApiResponse(responseCode = "403", description = "Usuario no autorizado"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        LOGGER.info("deleteTask|in. Eliminar tarea con id={} para usuario: {}", id, username);
        var response = taskService.deleteTask(id, username);
        LOGGER.info("deleteTask|out. Eliminar tarea con id={} para usuario: {}", id, username);
        return response;
    }

    @GetMapping("/filter")
    @Operation(summary = "Filtrar tareas por usuario, título, estado y fecha de vencimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tareas filtradas correctamente")
    })
    public ResponseEntity<Page<TaskDTO>> filterTasks(
            @ParameterObject @PageableDefault Pageable pageable,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long statusId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueBefore,
            Authentication authentication) {

        String username = authentication.getName();
        LOGGER.info("filterTasks|in. Usuario={}, title={}, statusId={}, dueBefore={}", username, title, statusId, dueBefore);

        Page<TaskDTO> result = taskService.getTasks(username, title, statusId, dueBefore, pageable);

        LOGGER.info("filterTasks|out. Tareas encontradas: {}", result.getTotalElements());
        return ResponseEntity.ok(result);
    }

}