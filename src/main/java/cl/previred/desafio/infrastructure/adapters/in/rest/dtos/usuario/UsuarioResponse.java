package cl.previred.desafio.infrastructure.adapters.in.rest.dtos.usuario;

import cl.previred.desafio.domain.models.Usuario;

public record UsuarioResponse(
        Long id,
        String username
) {
    public static UsuarioResponse fromDomain(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername()
        );
    }
}
