package cl.previred.taskmanager.infrastructure.controller.dto;

public record UpdateTaskRequest(
        String title,
        String description,
        String status
) {}
