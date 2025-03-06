package com.provired.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la respuesta de autenticación")
public class AuthResponse {

    @JsonProperty("message")
    @Schema(description = "Mensaje del resultado", example = "Inicio de sesión exitoso")
    private String message;

    @JsonProperty("token")
    @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}