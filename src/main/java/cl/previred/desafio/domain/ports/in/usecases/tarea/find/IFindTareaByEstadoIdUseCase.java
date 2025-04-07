package cl.previred.desafio.domain.ports.in.usecases.tarea.find;

import cl.previred.desafio.domain.models.Tarea;

import java.util.List;

public interface IFindTareaByEstadoIdUseCase {
    List<Tarea>execute(Long estadoId);
}
