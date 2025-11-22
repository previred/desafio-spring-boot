package com.yoserp1.tareas.controller;

import com.yoserp1.tareas.dto.TareaDTO;
import com.yoserp1.tareas.service.ITareaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tareas")
@Tag(name = "Tareas", description = "API para la gestión de tareas")
public class TareaController {

  @Autowired private ITareaService tareaService;

  @Operation(summary = "Crear nueva tarea", description = "Crea una nueva tarea en el sistema")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
      })
  @SecurityRequirement(name = "bearerAuth")
  @PostMapping
  public ResponseEntity<TareaDTO> crearTarea(@RequestBody TareaDTO tareaDTO) {
    TareaDTO creada = tareaService.crearTarea(tareaDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(creada);
  }

  @Operation(
      summary = "Listar todas las tareas",
      description = "Obtiene la lista completa de tareas")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Tareas listadas exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
      })
  @SecurityRequirement(name = "bearerAuth")
  @GetMapping
  public ResponseEntity<List<TareaDTO>> listarTareas() {
    return ResponseEntity.ok(tareaService.listarTareas());
  }

  @Operation(
      summary = "Obtener tarea por ID",
      description = "Obtiene los detalles de una tarea específica")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Tarea encontrada"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
      })
  @SecurityRequirement(name = "bearerAuth")
  @GetMapping("/{id}")
  public ResponseEntity<TareaDTO> obtenerTarea(@PathVariable Long id) {
    return ResponseEntity.ok(tareaService.obtenerTarea(id));
  }

  @Operation(
      summary = "Actualizar tarea",
      description = "Actualiza los datos de una tarea existente")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Tarea actualizada"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
      })
  @SecurityRequirement(name = "bearerAuth")
  @PutMapping("/{id}")
  public ResponseEntity<TareaDTO> actualizarTarea(
      @PathVariable Long id, @RequestBody TareaDTO tareaDTO) {
    return ResponseEntity.ok(tareaService.actualizarTarea(id, tareaDTO));
  }

  @Operation(summary = "Eliminar tarea", description = "Elimina una tarea del sistema")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Tarea eliminada"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
      })
  @SecurityRequirement(name = "bearerAuth")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
    tareaService.eliminarTarea(id);
    return ResponseEntity.noContent().build();
  }
}
