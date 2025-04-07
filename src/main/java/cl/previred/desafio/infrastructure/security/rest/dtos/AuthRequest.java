package cl.previred.desafio.infrastructure.security.rest.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank String username,
        @NotBlank String password
) {
}
