package cl.previred.desafio.application.usecases.tarea;

import cl.previred.desafio.domain.ports.in.usecases.tarea.IAssignTareaUseCase;
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
public class AssignTareaUseCase implements IAssignTareaUseCase {
    private final IFindTareaByIdUseCase findTareaByIdUseCase;
    private final IFindUsuarioByIdUseCase findUsuarioByIdUseCase;
    private final ITareaRepository repository;

    @Override
    public void execute(Long tareaId, Long usuarioId) {
        log.info("Asignando tarea con id {} a usuario con id {}", tareaId, usuarioId);
        var tarea = findTareaByIdUseCase.execute(tareaId);
        var usuario = findUsuarioByIdUseCase.execute(usuarioId);
        tarea.setUsuarioAsignado(usuario);
        tarea.setFechaAsignacion(LocalDateTime.now());
        repository.save(tarea);
    }
}
