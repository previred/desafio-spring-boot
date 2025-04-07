package cl.previred.desafio.domain.ports.in.usecases.tarea;

import cl.previred.desafio.domain.models.Tarea;

public interface IUpdateTareaUseCase {
    Tarea execute(Tarea tarea);
}
