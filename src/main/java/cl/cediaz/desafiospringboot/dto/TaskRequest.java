package cl.cediaz.desafiospringboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record TaskRequest(
        @NotBlank(message = "El título es obligatorio")
        String title,

        @NotBlank(message = "La descripción es obligatoria")
        String description,

        @NotNull(message = "El estado es obligatorio")
        Long statusId,

        @NotNull(message = "Debe asignar al menos un usuario")
        List<Long> userIds,

        @NotNull(message = "La fecha de vencimiento es obligatoria")
        LocalDateTime dueDate
) {
}

