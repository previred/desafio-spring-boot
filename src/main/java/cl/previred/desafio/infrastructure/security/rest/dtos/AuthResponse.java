package cl.previred.desafio.infrastructure.security.rest.dtos;

public record AuthResponse(
        String token,
        String username
) {
}
