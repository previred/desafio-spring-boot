package cl.cediaz.desafiospringboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UserUpdateRequest(

        @NotBlank(message = "El username es obligatorio")
        String username,

        @NotNull(message = "El rol es obligatorio")
        Long roleId,

        String password
) {
}
