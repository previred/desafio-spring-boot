package com.previred.task_manager.dto.user.token;

import com.previred.task_manager.components.validator.ValidPasswordFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record  LoginReqDto(
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Schema(description = "Email del usuario", example = "usuario@ejemplo.com")
    String email,
    @NotBlank(message = "La contraseña es obligatoria")
    @Schema(description = "Contraseña del usuario", example = "Test@123")
    @ValidPasswordFormat String password
){}
