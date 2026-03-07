package cl.previred.desafio.repository;

import cl.previred.desafio.entity.EstadoTareaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoTareaRepository extends JpaRepository<EstadoTareaEntity, Long> {
}