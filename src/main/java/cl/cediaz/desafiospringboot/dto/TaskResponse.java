package cl.cediaz.desafiospringboot.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record TaskResponse(
        Long id,
        @NotBlank(message = "El título es obligatorio")
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime dueDate,
        String status,
        List<Long> userIds
) {
}

