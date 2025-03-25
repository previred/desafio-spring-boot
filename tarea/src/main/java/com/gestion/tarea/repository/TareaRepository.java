package com.gestion.tarea.repository;

import com.gestion.tarea.dto.TareaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TareaRepository extends JpaRepository<TareaDto, Long> {

}
