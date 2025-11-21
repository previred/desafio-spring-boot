package com.taskmanagement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateTaskRequest(
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    String title,

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    String description,

    @NotNull(message = "State ID is required")
    @JsonProperty("stateId")
    Long stateId,

    @JsonProperty("dueDate")
    LocalDateTime dueDate,

    @JsonProperty("assignedUserId")
    Long assignedUserId
) {}
