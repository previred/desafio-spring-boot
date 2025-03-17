package com.cifuentes.cristobal.gestion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.cifuentes.cristobal.gestion.model.dto.TaskCreateRequest;
import com.cifuentes.cristobal.gestion.model.dto.TaskResponse;
import com.cifuentes.cristobal.gestion.model.dto.TaskUpdateRequest;
import com.cifuentes.cristobal.gestion.model.dto.UpdateTaskStatusRequest;
import com.cifuentes.cristobal.gestion.security.CustomUserDetails;
import com.cifuentes.cristobal.gestion.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@Operation(summary = "Crear una nueva tarea", description = "Crea una tarea asociada al usuario autenticado.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tarea creada exitosamente"),
			@ApiResponse(responseCode = "400", description = "Datos inválidos"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido – requiere token JWT") })
	@PostMapping
	public ResponseEntity<TaskResponse> createTask(@RequestBody TaskCreateRequest request, Authentication auth) {
		Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
		TaskResponse createdTask = taskService.createTask(request, userId);
		return ResponseEntity.ok(createdTask);
	}

	@Operation(summary = "Listar mis tareas", description = "Devuelve todas las tareas del usuario autenticado que no han sido eliminadas.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Listado de tareas obtenido exitosamente"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido – requiere token JWT") })
	@GetMapping
	public ResponseEntity<List<TaskResponse>> getMyTasks(Authentication auth) {
		Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
		List<TaskResponse> tasks = taskService.getTasks(userId);
		return ResponseEntity.ok(tasks);
	}

	@Operation(summary = "Obtener tarea por ID", description = "Devuelve los detalles de una tarea específica del usuario autenticado.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tarea encontrada"),
			@ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido – requiere token JWT") })
	@GetMapping("/{id}")
	public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id, Authentication auth) {
		Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
		TaskResponse task = taskService.getTaskById(id, userId);
		return ResponseEntity.ok(task);
	}

	@Operation(summary = "Eliminar tarea", description = "Elimina lógicamente una tarea del usuario autenticado (no se elimina físicamente).")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Tarea eliminada"),
			@ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido – requiere token JWT") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id, Authentication auth) {
		Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
		taskService.deleteTask(id, userId);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Actualizar tarea", description = "Actualiza el título y la descripción de una tarea específica.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente"),
			@ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido – requiere token JWT") })
	@PutMapping("/{id}")
	public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody TaskUpdateRequest request,
			Authentication auth) {
		Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
		TaskResponse updatedTask = taskService.updateTask(id, request, userId);
		return ResponseEntity.ok(updatedTask);
	}

	@Operation(summary = "Cambiar estado de la tarea", description = "Actualiza el estado de una tarea (por ejemplo: de PENDING a COMPLETED).")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido – requiere token JWT") })
	@PatchMapping("/{id}/status")
	public ResponseEntity<TaskResponse> updateTaskStatus(@PathVariable Long id,
			@RequestBody UpdateTaskStatusRequest request, Authentication auth) {
		Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
		TaskResponse response = taskService.updateTaskStatus(id, request.getStatusName(), userId);
		return ResponseEntity.ok(response);
	}
}