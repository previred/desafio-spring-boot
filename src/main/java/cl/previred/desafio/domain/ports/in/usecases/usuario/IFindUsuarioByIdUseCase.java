package cl.previred.desafio.domain.ports.in.usecases.usuario;

import cl.previred.desafio.domain.models.Usuario;

public interface IFindUsuarioByIdUseCase {
    Usuario execute(Long usuarioId);
}
