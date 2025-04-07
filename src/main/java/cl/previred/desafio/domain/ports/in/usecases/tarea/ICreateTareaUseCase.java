package cl.previred.desafio.domain.ports.in.usecases.tarea;

import cl.previred.desafio.domain.models.Tarea;

public interface ICreateTareaUseCase {
    Tarea execute(Tarea tarea,String username);
}
