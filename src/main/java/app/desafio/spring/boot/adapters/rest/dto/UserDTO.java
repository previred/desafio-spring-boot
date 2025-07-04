package app.desafio.spring.boot.adapters.rest.dto;

import jakarta.validation.constraints.NotNull;

public record UserDTO(
        @NotNull String username,
        @NotNull String password
) {}
