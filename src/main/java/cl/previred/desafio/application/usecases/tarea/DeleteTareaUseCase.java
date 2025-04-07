package cl.previred.desafio.application.usecases.tarea;

import cl.previred.desafio.domain.ports.in.usecases.tarea.IDeleteTareaUseCase;
import cl.previred.desafio.domain.ports.in.usecases.tarea.find.IFindTareaByIdUseCase;
import cl.previred.desafio.domain.ports.out.repositories.ITareaRepository;
import cl.previred.desafio.shared.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class DeleteTareaUseCase implements IDeleteTareaUseCase {
    private final IFindTareaByIdUseCase findTareaByIdUseCase;
    private final ITareaRepository repository;
    @Override
    public void execute(Long tareaId) {
        log.info("Eliminando tarea con id {}", tareaId);
        //Se busca antes de eliminar para manejar la exception
        findTareaByIdUseCase.execute(tareaId);
        repository.deleteById(tareaId);
    }
}
