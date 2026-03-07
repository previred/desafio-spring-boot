package com.previred.desafiostareas.controller.dto;

import com.previred.desafiostareas.validation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto de usuarioEntity request
 */
@Getter
@Setter
@Builder
@Schema(description = "Objeto de usuarioEntity request")
public class LoginRequest {

    @NotBlank
    @Email(message = "El formato del correo es incorrecto")
    @Schema(description = "Ejemplo correo", example = "test@test.org")
    private String email;
    @NotBlank
    @ValidPassword
    @Schema(description = "Password debe tener al menos una letra mayúscula, al menos un dígito y una longitud mínima de 8 caracteres,solo letras y números.", example = "Previred123")
    private String password;
}
