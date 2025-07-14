package com.nuevospa.tasksmanagement.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * DTO for creating or updating task requests.
 */
public record TaskRequest(

        @NotBlank(message = "Title cannot be blank")
        @Size(max = 100, message = "Title cannot exceed 100 characters")
        String title,

        @NotBlank(message = "Description cannot be blank")
        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @NotNull(message = "Task status ID cannot be null")
        Long statusId,

        @NotNull(message = "User ID cannot be null")
        Long userId,

        LocalDate dueDate // Optional due date
) {}
