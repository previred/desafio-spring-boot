package cl.previred.taskmanager.infrastructure.controller.dto;

public record CreateTaskRequest(
        String title,
        String description,
        Long userId,
        String status
) {}
