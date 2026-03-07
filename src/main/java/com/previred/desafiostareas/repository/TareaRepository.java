package com.previred.desafiostareas.repository;

import com.previred.desafiostareas.repository.entity.TareaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio entidad TareaEntity
 */
@Repository
public interface TareaRepository extends JpaRepository<TareaEntity, Long> {
    
    List<TareaEntity> findByUsuarioEntity_UsuarioId(Long usuarioId);

    List<TareaEntity> findByEstado_Id(Long estadoId);
}
