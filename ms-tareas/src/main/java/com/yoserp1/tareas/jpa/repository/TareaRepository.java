package com.yoserp1.tareas.jpa.repository;

import com.yoserp1.tareas.jpa.entity.TareaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaRepository extends JpaRepository<TareaEntity, Long> {}
