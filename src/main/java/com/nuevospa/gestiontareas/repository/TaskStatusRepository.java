package com.nuevospa.gestiontareas.repository;

import com.nuevospa.gestiontareas.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
}