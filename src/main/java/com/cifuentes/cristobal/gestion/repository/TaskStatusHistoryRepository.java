package com.cifuentes.cristobal.gestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cifuentes.cristobal.gestion.model.entity.Task;
import com.cifuentes.cristobal.gestion.model.entity.TaskStatusHistory;

import java.util.List;

public interface TaskStatusHistoryRepository extends JpaRepository<TaskStatusHistory, Long> {
	List<TaskStatusHistory> findByTask(Task task);
}