package com.previred.task_manager.dto.user;

import com.previred.task_manager.components.validator.ValidPasswordFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserReqDto implements Serializable {
    @NotBlank(message = "El nombre es requerido")
    @Schema(name = "name", description = "name", example = "rudy")
    private String name;

    @Schema(name = "lastname", description = "lastname", example = "sorto")
    private String lastname;
    @NotBlank(message = "El email es requerido")
    @Pattern(
            regexp = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Za-z]{2,}$",
            message = "Formato de email inválido"
    )
    @Schema(name = "email", description = "name", example = "rudy@gmail.com")
    private String email;
    @NotBlank(message = "La contraseña es requerida")
    @ValidPasswordFormat
    @Schema(name = "password", description = "name", example = "Test@123")
    private String password;
}
