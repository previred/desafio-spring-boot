package cl.previred.desafio.domain.ports.out.repositories;

import cl.previred.desafio.domain.models.Tarea;

import java.util.List;

public interface ITareaRepository {
    Tarea save(Tarea tarea);
    Tarea findById(Long id);
    void deleteById(Long id);
    List<Tarea> findAll();
    List<Tarea> findByEstadoId(Long estadoId);
}
