package cl.previred.desafio.application.usecases.tarea.find;

import cl.previred.desafio.domain.exceptions.EntityNotFoundException;
import cl.previred.desafio.domain.models.Tarea;
import cl.previred.desafio.domain.ports.in.usecases.tarea.find.IFindTareaByIdUseCase;
import cl.previred.desafio.domain.ports.out.repositories.ITareaRepository;
import cl.previred.desafio.shared.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static cl.previred.desafio.domain.models.constants.PreviredConstant.TAREA_CONSTANT;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class FindTareaByIdUseCase implements IFindTareaByIdUseCase {
    private final ITareaRepository repository;

    @Override
    public Tarea execute(Long tareaId) {
        log.info("Buscado Tarea por Id: {}", tareaId);
        var tarea = repository.findById(tareaId);
        if (tarea==null) {
            log.error("Tarea con id: {}, no encontrada", tareaId);
            throw new EntityNotFoundException(TAREA_CONSTANT, tareaId.toString());
        }
        return tarea;
    }
}
