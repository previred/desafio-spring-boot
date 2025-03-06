package com.provired.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para el estado de una tarea")
public class TaskStatusDTO {

    @JsonProperty("id_status")
    @Schema(description = "ID único del estado", example = "1")
    private Long id;

    @JsonProperty("name")
    @Schema(description = "Nombre del estado", example = "Pendiente")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "Debe tener entre 2 y 50 caracteres")
    private String name;
}