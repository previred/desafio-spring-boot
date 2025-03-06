package com.provired.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la información de una tarea")
public class TaskDTO {

    @JsonProperty("id_task")
    @Schema(description = "ID único de la tarea", example = "1")
    private Long id;

    @JsonProperty("created_at")
    @Schema(description = "Fecha de creación", example = "2025-03-01")
    @NotNull(message = "La fecha de creación no puede ser nula")
    @PastOrPresent(message = "La fecha de creación no puede ser en el futuro")
    private LocalDate createdAt;

    @JsonProperty("description")
    @Schema(description = "Descripción de la tarea", example = "Completar el informe")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 3, max = 255, message = "Debe tener entre 3 y 255 caracteres")
    private String description;

    @JsonProperty("user_id")
    @Schema(description = "ID del usuario asignado", example = "1")
    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long userId;

    @JsonProperty("status_id")
    @Schema(description = "ID del estado de la tarea", example = "2")
    @NotNull(message = "El ID del estado no puede ser nulo")
    private Long statusId;
}