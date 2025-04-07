package cl.previred.desafio.application.usecases.tarea;

import cl.previred.desafio.domain.models.Tarea;
import cl.previred.desafio.domain.ports.in.usecases.estado_tarea.IFindEstadoTareaByIdUseCase;
import cl.previred.desafio.domain.ports.in.usecases.tarea.ICreateTareaUseCase;
import cl.previred.desafio.domain.ports.in.usecases.usuario.IFindUsuarioByIdUseCase;
import cl.previred.desafio.domain.ports.in.usecases.usuario.IFindUsuarioByUsernameUseCase;
import cl.previred.desafio.domain.ports.out.repositories.ITareaRepository;
import cl.previred.desafio.shared.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class CreateTareaUseCase implements ICreateTareaUseCase {
    private final ITareaRepository repository;
    private final IFindEstadoTareaByIdUseCase findEstadoTareaByIdUseCase;
    private final IFindUsuarioByUsernameUseCase findUsuarioByUsernameUseCase;
    private final IFindUsuarioByIdUseCase findUsuarioByIdUseCase;
    @Override
    public Tarea execute(Tarea tarea,String username) {
        log.info("Creando Tarea: {} con usuario username: {}", tarea,username);
        var usuarioCreater=findUsuarioByUsernameUseCase.execute(username);
        tarea.setFechaCreacion(LocalDateTime.now());
        tarea.setUsuarioCreador(usuarioCreater);
        validEstadoTarea(tarea);
        validUsuarioAsignado(tarea);
        return repository.save(tarea);
    }

    private void validUsuarioAsignado(Tarea tarea) {
        if(tarea.getUsuarioAsignado() != null) {
            var usuarioAsignado = findUsuarioByIdUseCase.execute(tarea.getUsuarioAsignado().getId());
            tarea.setUsuarioAsignado(usuarioAsignado);
            tarea.setFechaAsignacion(LocalDateTime.now());
        }
    }

    private void validEstadoTarea(Tarea tarea) {
        if (tarea.getEstado() != null && tarea.getEstado().getId() != null) {
            var estado = findEstadoTareaByIdUseCase.execute(tarea.getEstado().getId());
            tarea.setEstado(estado);
        } else {
            var estado = findEstadoTareaByIdUseCase.execute(1L);
            //Estado inicial
            tarea.setEstado(estado);
        }
    }
}
