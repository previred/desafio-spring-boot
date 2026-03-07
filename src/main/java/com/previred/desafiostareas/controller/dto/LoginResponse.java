package com.previred.desafiostareas.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Respuesta de login
 */
@Data
@Builder
@Schema(description = "Respuesta de login")
public class LoginResponse {

    private String token;
    private String username;
    private String email;
}
