package com.yoserp1.tareas.jpa.repository;

import com.yoserp1.tareas.jpa.entity.UsuarioEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
  Optional<UsuarioEntity> findByUsername(String username);

  boolean existsByUsername(String username);
}
