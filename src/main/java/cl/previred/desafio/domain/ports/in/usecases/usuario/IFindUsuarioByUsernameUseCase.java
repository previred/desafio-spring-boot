package cl.previred.desafio.domain.ports.in.usecases.usuario;

import cl.previred.desafio.domain.models.Usuario;

public interface IFindUsuarioByUsernameUseCase {
    Usuario execute(String username);
}
