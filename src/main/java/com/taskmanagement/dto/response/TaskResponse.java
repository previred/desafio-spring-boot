package com.taskmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TaskResponse(
    Long id,
    String title,
    String description,
    TaskStateResponse state,
    @JsonProperty("userId")
    Long userId,
    @JsonProperty("dueDate")
    LocalDateTime dueDate,
    @JsonProperty("createdAt")
    LocalDateTime createdAt,
    @JsonProperty("updatedAt")
    LocalDateTime updatedAt
) {}
