package com.nuevospa.tasksmanagement.dtos.response;

import lombok.Builder;

/**
 * DTO for authentication responses, containing the JWT token.
 */
@Builder
public record AuthResponse(
        String token
) {}