package cl.previred.desafio.repository;

import cl.previred.desafio.entity.TareaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<TareaEntity, Long> {
    // Buscamos tareas por el ID del usuario para cumplir con la lógica de negocio
    List<TareaEntity> findByUsuarioId(Long usuarioId);
}
