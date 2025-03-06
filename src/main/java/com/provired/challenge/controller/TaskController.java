package com.provired.challenge.controller;

import com.provired.challenge.dto.ResponseWrapper;
import com.provired.challenge.dto.TaskDTO;
import com.provired.challenge.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Task Management", description = "Gestión de tareas")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @Operation(summary = "Listar tareas", description = "Devuelve todas las tareas registradas.")
    public ResponseEntity<ResponseWrapper<List<TaskDTO>>> getAllTasks() {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Tareas obtenidas", taskService.getAllTasks()));
    }

    @GetMapping("/{id:\\d+}")
    @Operation(summary = "Buscar por ID", description = "Obtiene los detalles de una tarea específica.")
    public ResponseEntity<ResponseWrapper<TaskDTO>> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Tarea encontrada", taskService.getTaskById(id)));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar por palabra clave", description = "Filtra tareas según su descripción.")
    public ResponseEntity<ResponseWrapper<List<TaskDTO>>> getTasksByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Tareas filtradas", taskService.getTasksByKeyword(keyword)));
    }

    @GetMapping("/grouped-status")
    @Operation(summary = "Agrupar por estado", description = "Devuelve tareas organizadas por estado.")
    public ResponseEntity<ResponseWrapper<Map<String, List<TaskDTO>>>> groupTasksByStatus() {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Tareas agrupadas", taskService.groupTasksByStatus()));
    }

    @PostMapping
    @Operation(summary = "Crear tarea", description = "Registra una nueva tarea.")
    public ResponseEntity<ResponseWrapper<TaskDTO>> createTask(@Valid @RequestBody TaskDTO dto) {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Tarea creada", taskService.createTask(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tarea", description = "Modifica una tarea existente.")
    public ResponseEntity<ResponseWrapper<TaskDTO>> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO dto) {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Tarea actualizada", taskService.updateTask(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tarea", description = "Elimina una tarea por ID.")
    public ResponseEntity<ResponseWrapper<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Tarea eliminada", null));
    }
}