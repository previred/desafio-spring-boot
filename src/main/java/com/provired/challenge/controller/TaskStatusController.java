package com.provired.challenge.controller;

import com.provired.challenge.dto.ResponseWrapper;
import com.provired.challenge.dto.TaskStatusDTO;
import com.provired.challenge.service.TaskStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task-status")
@Tag(name = "Task Status Management", description = "Gestión de estados de tareas")
@RequiredArgsConstructor
public class TaskStatusController {

    private final TaskStatusService taskStatusService;

    @GetMapping
    @Operation(summary = "Listar estados", description = "Devuelve todos los estados registrados.")
    public ResponseEntity<ResponseWrapper<List<TaskStatusDTO>>> getAllStatuses() {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Estados obtenidos", taskStatusService.getAllStatuses()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Obtiene un estado específico.")
    public ResponseEntity<ResponseWrapper<TaskStatusDTO>> getStatusById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Estado obtenido", taskStatusService.getStatusById(id)));
    }

    @PostMapping
    @Operation(summary = "Crear estado", description = "Registra un nuevo estado.")
    public ResponseEntity<ResponseWrapper<TaskStatusDTO>> createStatus(@Valid @RequestBody TaskStatusDTO dto) {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Estado creado", taskStatusService.createStatus(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estado", description = "Modifica un estado existente.")
    public ResponseEntity<ResponseWrapper<TaskStatusDTO>> updateStatus(@PathVariable Long id, @Valid @RequestBody TaskStatusDTO dto) {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Estado actualizado", taskStatusService.updateStatus(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estado", description = "Elimina un estado por ID.")
    public ResponseEntity<ResponseWrapper<Void>> deleteStatus(@PathVariable Long id) {
        taskStatusService.deleteStatus(id);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Estado eliminado", null));
    }
}