package app.desafio.spring.boot.adapters.rest.web;

import app.desafio.spring.boot.adapters.rest.dto.TaskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface ITaskController {

    @Operation(summary = "Crear tarea", description = "Crea una nueva tarea con los datos proporcionados.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    ResponseEntity<?> createTask(TaskDTO task);

    @Operation(summary = "Obtener tarea por ID", description = "Devuelve los detalles de una tarea específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarea encontrada"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    ResponseEntity<?> getTaskById(Long id);

    @Operation(summary = "Listar todas las tareas", description = "Devuelve una lista de todas las tareas registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    ResponseEntity<?> getAllTasks();

    @Operation(summary = "Actualizar tarea", description = "Actualiza los datos de una tarea existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    ResponseEntity<?> updateTask(Long id, TaskDTO task);

    @Operation(summary = "Eliminar tarea", description = "Elimina una tarea específica por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tarea eliminada"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    ResponseEntity<?> deleteTask(Long id);
}