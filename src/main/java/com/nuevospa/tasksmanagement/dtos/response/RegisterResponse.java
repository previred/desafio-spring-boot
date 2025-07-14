package com.nuevospa.tasksmanagement.dtos.response;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * DTO for register responses, used to expose user details via the API (without password).
 * Uses Java 17 record for immutability and conciseness.
 */
@Builder
public record RegisterResponse(
        Long id,
        String username,
        String role,
        LocalDateTime dateCreation
) {}