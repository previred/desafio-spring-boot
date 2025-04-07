package cl.previred.desafio.application.usecases.tarea.find;

import cl.previred.desafio.domain.models.Tarea;
import cl.previred.desafio.domain.ports.in.usecases.estado_tarea.IFindEstadoTareaByIdUseCase;
import cl.previred.desafio.domain.ports.in.usecases.tarea.find.IFindTareaByEstadoIdUseCase;
import cl.previred.desafio.domain.ports.out.repositories.ITareaRepository;
import cl.previred.desafio.shared.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class FindTareaByEstadoIdUseCase implements IFindTareaByEstadoIdUseCase {
    private final ITareaRepository repository;
    private final IFindEstadoTareaByIdUseCase findEstadoTareaByIdUseCase;
    @Override
    public List<Tarea> execute(Long estadoId) {
        log.info("Buscando tareas por estado con id {}", estadoId);
        // Se busca antes de eliminar para manejar la exception
        findEstadoTareaByIdUseCase.execute(estadoId);
        return repository.findByEstadoId(estadoId);
    }
}
