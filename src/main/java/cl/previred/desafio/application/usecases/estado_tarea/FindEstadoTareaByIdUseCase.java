package cl.previred.desafio.application.usecases.estado_tarea;

import cl.previred.desafio.domain.exceptions.EntityNotFoundException;
import cl.previred.desafio.domain.models.EstadoTarea;
import cl.previred.desafio.domain.ports.in.usecases.estado_tarea.IFindEstadoTareaByIdUseCase;
import cl.previred.desafio.domain.ports.out.repositories.IEstadoTareaRepository;
import cl.previred.desafio.shared.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static cl.previred.desafio.domain.models.constants.PreviredConstant.ESTADO_TAREA_CONSTANT;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class FindEstadoTareaByIdUseCase implements IFindEstadoTareaByIdUseCase {
    private final IEstadoTareaRepository repository;

    @Override
    public EstadoTarea execute(Long estadoTareaId) {
        log.info("Buscando estado de tarea con id {}", estadoTareaId);
        var found= repository.findById(estadoTareaId);
        if(found==null){
            log.error("No se encontró el estado de tarea con id {}", estadoTareaId);
            throw  new EntityNotFoundException(ESTADO_TAREA_CONSTANT, estadoTareaId.toString());
        }
        return found;
    }
}
