package cl.previred.desafio.infrastructure.db.jpa.repositories;

import cl.previred.desafio.infrastructure.db.jpa.entities.TareaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TareaJpaRepository extends CrudRepository<TareaEntity,Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM tareas t WHERE t.estado_id = ?1")
    List<TareaEntity>findByEstadoTareaId(Long estadoTareaId);
}
