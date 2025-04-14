package com.nuevospa.taskmanager.repository;

import com.nuevospa.taskmanager.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByNombre(String nombre);
    
}
