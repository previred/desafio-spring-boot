package com.dio.task_manager.application.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskRequest(
        @NotBlank(message = "El título no puede estar vacío")
        @Size(max = 255, message = "El título no puede exceder los 255 caracteres")
        String title,

        @Size(max = 1000, message = "La descripción no puede exceder los 1000 caracteres")
        String description,

        @NotNull(message = "La fecha de vencimiento es obligatoria")
        @FutureOrPresent(message = "La fecha de vencimiento no puede estar en el pasado")
        LocalDate dueDate,

        @NotNull(message = "El estado es obligatorio")
        Long statusId,
        Long userId
) {
}