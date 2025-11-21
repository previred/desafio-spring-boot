package com.taskmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(
    @JsonProperty("accessToken")
    String accessToken,
    @JsonProperty("tokenType")
    String tokenType,
    @JsonProperty("expiresIn")
    Long expiresIn,
    UserResponse user
) {
    public AuthResponse(String accessToken, Long expiresIn, UserResponse user) {
        this(accessToken, "Bearer", expiresIn, user);
    }
}
