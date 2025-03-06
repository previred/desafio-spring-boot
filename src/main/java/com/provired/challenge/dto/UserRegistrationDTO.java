package com.provired.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para registro de usuario")
public class UserRegistrationDTO {

    @JsonProperty("username")
    @Schema(description = "Nombre de usuario", example = "Ronny")
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @JsonProperty("password")
    @Schema(description = "Contraseña", example = "Password123!")
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "Debe tener al menos 6 caracteres")
    private String password;
}