package cl.previred.desafio.domain.ports.in.usecases.estado_tarea;

import cl.previred.desafio.domain.models.EstadoTarea;

public interface IFindEstadoTareaByIdUseCase {
    EstadoTarea execute(Long estadoTareaId);
}
