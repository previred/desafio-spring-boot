package cl.previred.desafio.infrastructure.db.jpa.repositories;

import cl.previred.desafio.infrastructure.db.jpa.entities.EstadoTareaEntity;
import org.springframework.data.repository.CrudRepository;

public interface EstadoTareaJpaRepository extends CrudRepository<EstadoTareaEntity,Long> {
}
