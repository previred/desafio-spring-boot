package com.dio.task_manager.application.dto;

import java.time.LocalDate;

public record TaskDTO(
        String title,
        String description,
        LocalDate dueDate,
        String status,
        String username
) {}