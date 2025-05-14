package com.nuevospa.gestiontareas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTaskRequest {

    @NotNull(message = "El titulo no puede ser nulo")
    private String title;

    @NotNull(message = "El description no puede ser nulo")
    private String description;

    private Long userId;
    private Long statusId;
}