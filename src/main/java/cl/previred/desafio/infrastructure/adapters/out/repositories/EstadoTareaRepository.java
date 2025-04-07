package cl.previred.desafio.infrastructure.adapters.out.repositories;

import cl.previred.desafio.domain.models.EstadoTarea;
import cl.previred.desafio.domain.ports.out.repositories.IEstadoTareaRepository;
import cl.previred.desafio.infrastructure.db.jpa.entities.EstadoTareaEntity;
import cl.previred.desafio.infrastructure.db.jpa.repositories.EstadoTareaJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EstadoTareaRepository implements IEstadoTareaRepository {
    private final EstadoTareaJpaRepository jpaRepository;

    @Override
    public EstadoTarea findById(Long id) {
        log.info("Buscando estado de tarea por id {} desde JPA", id);
        return jpaRepository.findById(id)
                .map(EstadoTareaEntity::toDomain)
                .orElse(null);
    }
}
