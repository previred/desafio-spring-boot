package cl.previred.desafio.domain.ports.in.usecases.tarea.find;

import cl.previred.desafio.domain.models.Tarea;

public interface IFindTareaByIdUseCase {
    Tarea execute(Long tareaId);
}
