package com.previred.desafiostareas.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Respuesta de la tarea
 */
@Data
@Builder
@Schema (description = "Respuesta de la tarea")
public class TareaResponse {

    @Schema(description = "ID de la tarea", example = "1231231")
    private Long id;

    @Schema(description = "titulo de la tarea", example = "TareaEntity test")
    private String titulo;

    @Schema(description = "Descripción de la tarea", example = "Descripción test")
    private String descripcion;

    @Schema(description = "Fecha de creación de la tarea", example = "2026-03-06T15:30:00+01:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime fechaCreacion;

    @Schema(description = "Prioridad de la tarea", example = "BAJA")
    private String prioridad;


    @Schema(description = "Nombre del estado de la tarea", example = "PENDIENTE")
    private String estadoNombre;


    @Schema(description = "nombre del usuarioEntity asignado a la tarea", example = "usuario1")
    private String nombreUsuario;

}
