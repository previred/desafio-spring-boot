package cl.previred.desafio.domain.ports.out.repositories;

import cl.previred.desafio.domain.models.EstadoTarea;

public interface IEstadoTareaRepository {
    EstadoTarea findById(Long id);
}
