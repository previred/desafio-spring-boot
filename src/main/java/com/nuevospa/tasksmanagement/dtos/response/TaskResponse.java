package com.nuevospa.tasksmanagement.dtos.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for task responses, used to expose task details via the API.
 */
@Builder
public record TaskResponse(
        Long id,
        String code,
        String title,
        String description,
        String status,
        String user,
        LocalDate dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}