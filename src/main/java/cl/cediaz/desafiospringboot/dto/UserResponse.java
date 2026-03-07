package cl.cediaz.desafiospringboot.dto;

public record UserResponse(
        Long id,
        String username,
        String role
) {}
