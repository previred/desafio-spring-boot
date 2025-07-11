package com.ejemplo.spa.repository;

import com.ejemplo.spa.model.Tarea;
import com.ejemplo.spa.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByUsuario(Usuario usuario);
    Optional<Tarea> findByIdAndUsuarioId(long id, Long usuarioId);
}
