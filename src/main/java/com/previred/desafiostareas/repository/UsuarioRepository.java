package com.previred.desafiostareas.repository;

import com.previred.desafiostareas.repository.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio entidad UsuarioEntity
 */
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    
    Optional<UsuarioEntity> findByNombreUsuario(String nombreUsuario);
    Optional<UsuarioEntity> findByEmail(String correo);
    
    boolean existsByNombreUsuario(String nombreUsuario);

    boolean existsByEmail(String email);
}
