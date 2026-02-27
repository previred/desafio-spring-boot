package cl.previred.taskmanager.infrastructure.controller.dto;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        Long userId,
        String username,
        String status,
        LocalDateTime createdAt
) {}
