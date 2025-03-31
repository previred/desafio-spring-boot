package gestion.tareas.demo.repository;

import gestion.tareas.demo.model.Tareas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tareas, Long> {
    List<Tareas> findByUsuarioId(Long usuarioId);
}