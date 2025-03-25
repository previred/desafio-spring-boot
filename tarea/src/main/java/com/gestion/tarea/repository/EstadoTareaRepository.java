package com.gestion.tarea.repository;

import com.gestion.tarea.dto.EstadosTareaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EstadoTareaRepository extends JpaRepository<EstadosTareaDto, Long> {

}
