package com.taskmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record UserResponse(
    Long id,
    String username,
    String email,
    @JsonProperty("fullName")
    String fullName,
    @JsonProperty("createdAt")
    LocalDateTime createdAt,
    @JsonProperty("updatedAt")
    LocalDateTime updatedAt
) {}
