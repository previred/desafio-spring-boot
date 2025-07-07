package com.dio.task_manager.application.dto;

import java.time.LocalDate;

public record CreateTaskRequest(
        String title,
        String description,
        LocalDate dueDate,
        Long statusId,
        Long userId
) {}