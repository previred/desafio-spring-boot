package cl.previred.desafio.infrastructure.adapters.in.rest;

import cl.previred.desafio.infrastructure.adapters.in.rest.dtos.tarea.TareaCreateRequest;
import cl.previred.desafio.infrastructure.adapters.in.rest.dtos.tarea.TareaResponse;
import cl.previred.desafio.infrastructure.adapters.in.rest.dtos.tarea.TareaUpdateRequest;
import cl.previred.desafio.infrastructure.exceptions.dto.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
@Tag(name = "Tarea",description = "CRUD de Tarea")
public interface ITareaController {
    @Operation(summary = "Crear Tarea",description = "Crea una nueva tarea",responses = {
            @ApiResponse(responseCode = "201",description = "Tarea creada exitosamente",content = @Content(mediaType = "application/json",schema = @Schema(implementation = TareaResponse.class))),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content(mediaType = "application/json",schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404",description = "Usuario/Estado no encontrado",content = @Content(mediaType = "application/json",schema = @Schema(implementation = ExceptionResponse.class)))
    })
    ResponseEntity<TareaResponse> create(TareaCreateRequest tareaRequest, Authentication authResult);

    @Operation(summary = "Actualizar Tarea",description = "Actualiza una tarea existente",responses = {
            @ApiResponse(responseCode = "200",description = "Tarea Actualizada exitosamente",content = @Content(mediaType = "application/json",schema = @Schema(implementation = TareaResponse.class))),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content(mediaType = "application/json",schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404",description = "Tarea/Usuario/Estado no encontrado",content = @Content(mediaType = "application/json",schema = @Schema(implementation = ExceptionResponse.class)))
    })
    ResponseEntity<TareaResponse>update(TareaUpdateRequest tareaUpdateRequest);

    @Operation(summary = "Listar Tareas",description = "Lista todas las tareas",responses = {
            @ApiResponse(responseCode = "200",description = "Tareas encontradas",content = @Content(mediaType = "application/json",schema = @Schema(implementation = TareaResponse[].class))),
    })
    ResponseEntity<List<TareaResponse>>readAll();

    @Operation(summary = "Eliminar Tarea",description = "Elimina una tarea existente",responses = {
            @ApiResponse(responseCode = "204",description = "Tarea eliminada exitosamente",content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",description = "Tarea no encontrada",content = @Content(mediaType = "application/json",schema = @Schema(implementation = ExceptionResponse.class)))
    })
    ResponseEntity<Void>delete(Long tareaId);

    @Operation(summary = "Buscar Tarea por ID",description = "Busca una tarea por su ID",responses = {
            @ApiResponse(responseCode = "204",description = "Tarea encontrada",content = @Content(mediaType = "application/json",schema = @Schema(implementation = TareaResponse.class))),
            @ApiResponse(responseCode = "404",description = "Tarea no encontrada",content = @Content(mediaType = "application/json",schema = @Schema(implementation = ExceptionResponse.class)))
    })
    ResponseEntity<TareaResponse>readById(Long tareaId);

    @Operation(summary = "Asignar Tarea",description = "Asigna una tarea a un usuario")
    ResponseEntity<Void>assignTarea(Long tareaId, Long userId);

    @Operation(summary = "Buscar Tareas por Estado",description = "Busca tareas por su estado")
    ResponseEntity<List<TareaResponse>>readByEstadoTareaId(Long estadoTareaId);
}
