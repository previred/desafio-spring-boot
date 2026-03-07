package com.previred.desafiostareas.controller;

import com.previred.desafiostareas.controller.dto.TareaRequest;
import com.previred.desafiostareas.controller.dto.TareaResponse;
import com.previred.desafiostareas.service.TareaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador de tareas.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class TareaController {

    private final TareaService tareaService;

    @Operation(
            operationId = "obtenerTodasLasTareas",
            summary = "Listar todas las tareas",
            description = "Obtiene la lista completa de tareas",
            tags = {"Tareas"},
            responses = {
                @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida exitosamente", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TareaResponse.class)))
                }),
                @ApiResponse(responseCode = "401", description = "No autorizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema())
                })
            },
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping(value = "tareas", produces = {"application/json"})
    public ResponseEntity<List<TareaResponse>> obtenerTodasLasTareas() {
        log.info("Petición recibida para obtener todas las tareas");
        return ResponseEntity.ok(tareaService.obtenerTodasLasTareas());
    }

    @Operation(
            operationId = "obtenerTareaPorId",
            summary = "Obtener una tarea por ID",
            description = "Obtiene los detalles de una tarea específica",
            tags = {"Tareas"},
            responses = {
                @ApiResponse(responseCode = "200", description = "Tarea encontrada", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TareaResponse.class))
                }),
                @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = {
                    @Content(mediaType = "application/json", schema = @Schema())
                }),
                @ApiResponse(responseCode = "401", description = "No autorizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema())
                })
            },
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping(value = "tarea/{id}", produces = {"application/json"})
    public ResponseEntity<TareaResponse> obtenerTareaPorId(
            @Parameter(name = "id", description = "ID de la tarea", required = true, in = ParameterIn.PATH)
            @PathVariable("id") Long id) {
        log.info("Petición recibida para obtener tarea con ID: {}", id);
        return ResponseEntity.ok(tareaService.obtenerTareaPorId(id));
    }

    @Operation(
            operationId = "crearTarea",
            summary = "Crear una nueva tarea",
            description = "Crea una nueva tarea. El usuario se obtiene del token JWT.",
            tags = {"Tareas"},
            responses = {
                @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TareaResponse.class))
                }),
                @ApiResponse(responseCode = "400", description = "Datos inválidos", content = {
                    @Content(mediaType = "application/json", schema = @Schema())
                }),
                @ApiResponse(responseCode = "401", description = "No autorizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema())
                })
            },
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PostMapping(value = "tarea", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<TareaResponse> create(
            @RequestHeader(value = "Authorization") String bearerToken,
            @Valid @RequestBody TareaRequest request) {
        log.info("Petición recibida para crear nueva tarea");
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaService.crearTarea(bearerToken, request));
    }

    @Operation(
            operationId = "actualizarTarea",
            summary = "Actualizar una tarea",
            description = "Actualiza los datos de una tarea existente. El usuario se obtiene del token JWT.",
            tags = {"Tareas"},
            responses = {
                @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TareaResponse.class))
                }),
                @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = {
                    @Content(mediaType = "application/json", schema = @Schema())
                }),
                @ApiResponse(responseCode = "400", description = "Datos inválidos", content = {
                    @Content(mediaType = "application/json", schema = @Schema())
                }),
                @ApiResponse(responseCode = "401", description = "No autorizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema())
                })
            },
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PutMapping(value = "tarea/{id}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<TareaResponse> update(
            @Parameter(name = "id", description = "ID de la tarea", required = true, in = ParameterIn.PATH)
            @PathVariable Long id,
            @RequestHeader(value = "Authorization") String bearerToken,
            @Valid @RequestBody TareaRequest request) {
        log.info("Peticion recibida para actualizar tarea id: {}", id);
        return ResponseEntity.ok(tareaService.actualizarTarea(id, bearerToken, request));
    }

    @Operation(
            operationId = "eliminarTarea",
            summary = "Eliminar una tarea",
            description = "Elimina una tarea del sistema",
            tags = {"Tareas"},
            responses = {
                @ApiResponse(responseCode = "204", description = "Tarea eliminada exitosamente"),
                @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = {
                    @Content(mediaType = "application/json", schema = @Schema())
                }),
                @ApiResponse(responseCode = "401", description = "No autorizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema())
                })
            },
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @DeleteMapping(value = "tarea/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(name = "id", description = "ID de la tarea", required = true, in = ParameterIn.PATH)
            @PathVariable("id") Long id) {
        log.info("Petición recibida para eliminar tarea con ID: {}", id);
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }
}
