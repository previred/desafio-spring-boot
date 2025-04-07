package cl.previred.desafio.application.usecases.usuario;

import cl.previred.desafio.domain.exceptions.UsernameNotFoundException;
import cl.previred.desafio.domain.models.Usuario;
import cl.previred.desafio.domain.ports.in.usecases.usuario.IFindUsuarioByUsernameUseCase;
import cl.previred.desafio.domain.ports.out.repositories.IUsuarioRepository;
import cl.previred.desafio.shared.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class FindUsuarioByUsernameUseCase implements IFindUsuarioByUsernameUseCase {
    private final IUsuarioRepository repository;
    @Override
    public Usuario execute(String username) {
        log.info("Buscando usuario por username {}", username);
        var usuario = repository.findByUsername(username);
        if (usuario==null){
            log.error("No se encontró el usuario con username {}", username);
            throw new UsernameNotFoundException(username);
        }
        return usuario;
    }
}
