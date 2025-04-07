package cl.previred.desafio.application.usecases.tarea;

import cl.previred.desafio.domain.models.Tarea;
import cl.previred.desafio.domain.ports.in.usecases.estado_tarea.IFindEstadoTareaByIdUseCase;
import cl.previred.desafio.domain.ports.in.usecases.tarea.IUpdateTareaUseCase;
import cl.previred.desafio.domain.ports.in.usecases.tarea.find.IFindTareaByIdUseCase;
import cl.previred.desafio.domain.ports.in.usecases.usuario.IFindUsuarioByIdUseCase;
import cl.previred.desafio.domain.ports.out.repositories.ITareaRepository;
import cl.previred.desafio.shared.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class UpdateTareaUseCase implements IUpdateTareaUseCase {
    private final IFindTareaByIdUseCase findTareaByIdUseCase;
    private final IFindEstadoTareaByIdUseCase findEstadoTareaByIdUseCase;
    private final IFindUsuarioByIdUseCase findUsuarioByIdUseCase;
    private final ITareaRepository repository;
    @Override
    public Tarea execute(Tarea tarea) {
        log.info("Actualizando tarea con id: {}", tarea.getId());
        var exist= findTareaByIdUseCase.execute(tarea.getId());
        if (tarea.getEstado()!=null&&tarea.getEstado().getId()!=null) {
            var estado=findEstadoTareaByIdUseCase.execute(tarea.getEstado().getId());
            exist.setEstado(estado);
        }
        exist.setNombre(tarea.getNombre());
        exist.setDescription(tarea.getDescription());
        if(tarea.getUsuarioAsignado()!= null && tarea.getUsuarioAsignado().getId() != null) {
            var usuarioAsignado= findUsuarioByIdUseCase.execute(tarea.getUsuarioAsignado().getId());
            exist.setFechaAsignacion(LocalDateTime.now());
            exist.setUsuarioAsignado(usuarioAsignado);
        }
        return repository.save(exist);
    }
}
