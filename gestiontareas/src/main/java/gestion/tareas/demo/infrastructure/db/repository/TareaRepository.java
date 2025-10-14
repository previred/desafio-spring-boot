package gestion.tareas.demo.infrastructure.db.repository;

import gestion.tareas.demo.infrastructure.db.entity.Tareas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tareas, Long> {
    List<Tareas> findByUsuarioId(Long usuarioId);
}