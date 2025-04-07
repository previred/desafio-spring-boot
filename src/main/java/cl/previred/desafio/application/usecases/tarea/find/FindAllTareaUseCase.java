package cl.previred.desafio.application.usecases.tarea.find;

import cl.previred.desafio.domain.models.Tarea;
import cl.previred.desafio.domain.ports.in.usecases.tarea.find.IFindAllTareUseCase;
import cl.previred.desafio.domain.ports.out.repositories.ITareaRepository;
import cl.previred.desafio.shared.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class FindAllTareaUseCase implements IFindAllTareUseCase {
    private final ITareaRepository repository;

    @Override
    public List<Tarea> execute() {
        log.info("Buscando todas las tareas");
        return repository.findAll();
    }
}
