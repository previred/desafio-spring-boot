package com.nuevospa.tasksmanagement.dtos.request;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for user login requests.
 */
public record LoginRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,
        @NotBlank(message = "Password cannot be blank")
        String password
) {}
