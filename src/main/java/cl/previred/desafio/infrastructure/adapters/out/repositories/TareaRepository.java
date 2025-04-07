package cl.previred.desafio.infrastructure.adapters.out.repositories;

import cl.previred.desafio.domain.models.Tarea;
import cl.previred.desafio.domain.ports.out.repositories.ITareaRepository;
import cl.previred.desafio.infrastructure.db.jpa.entities.TareaEntity;
import cl.previred.desafio.infrastructure.db.jpa.repositories.TareaJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class TareaRepository implements ITareaRepository {
    private final TareaJpaRepository jpaRepository;

    @Override
    public Tarea save(Tarea tarea) {
        log.info("Guardando tarea {} desde JPA", tarea);
        return jpaRepository.save(TareaEntity.fromDomain(tarea)).toDomain();
    }

    @Override
    public Tarea findById(Long id) {
        log.info("Buscando tarea con id {} desde JPA", id);
        return jpaRepository.findById(id)
                .map(TareaEntity::toDomain)
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Eliminando tarea con id {} desde JPA", id);
        jpaRepository.deleteById(id);

    }

    @Override
    public List<Tarea> findAll() {
        log.info("Buscando todas las tareas desde JPA");
        return ((List<TareaEntity>)jpaRepository.findAll())
                .stream()
                .map(TareaEntity::toDomain)
                .toList();
    }

    @Override
    public List<Tarea> findByEstadoId(Long estadoId) {
        log.info("Buscando tareas por estado con id {} desde JPA", estadoId);
        return jpaRepository.findByEstadoTareaId(estadoId)
                .stream()
                .map(TareaEntity::toDomain)
                .toList();
    }
}
