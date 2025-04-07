package cl.previred.desafio.application.usecases.usuario;

import cl.previred.desafio.domain.exceptions.EntityNotFoundException;
import cl.previred.desafio.domain.models.Usuario;
import cl.previred.desafio.domain.ports.in.usecases.usuario.IFindUsuarioByIdUseCase;
import cl.previred.desafio.domain.ports.out.repositories.IUsuarioRepository;
import cl.previred.desafio.shared.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static cl.previred.desafio.domain.models.constants.PreviredConstant.USUARIO_CONSTANT;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class FindUsuarioByIdUseCase implements IFindUsuarioByIdUseCase {
    private final IUsuarioRepository repository;

    @Override
    public Usuario execute(Long usuarioId) {
        log.info("Buscando Usuario por Id: {}", usuarioId);
        var usuario = repository.findById(usuarioId);
        if (usuario==null){
            log.error("Usuario con id: {}, no encontrado", usuarioId);
            throw new EntityNotFoundException(USUARIO_CONSTANT, usuarioId.toString());
        }
        log.info("Usuario encontrado: {}", usuario);
        return usuario;
    }
}
