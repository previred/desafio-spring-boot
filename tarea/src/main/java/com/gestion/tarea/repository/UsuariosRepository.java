package com.gestion.tarea.repository;

import com.gestion.tarea.dto.UsuarioDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface UsuariosRepository extends JpaRepository<UsuarioDto, Long> {
    boolean existsByUserName(String userName);
    Optional<UsuarioDto> findByUserName(String userName);
}
