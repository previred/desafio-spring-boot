package com.nuevospa.tasksmanagement.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating or updating user requests.
 * Uses Java 17 record for immutability and conciseness.
 */
public record UserRequest(
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        // Password is required for creation, optional for update (if not provided, current password remains)
        @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
        @NotBlank(message = "Password cannot be blank")
        String password, // Will be null for update if not explicitly provided

        @NotBlank(message = "Role cannot be blank")
        @Pattern(regexp = "^(ROLE_ADMIN|ROLE_USER)$", message = "Role must be 'ROLE_ADMIN' or 'ROLE_USER'")
        String role
) {}
