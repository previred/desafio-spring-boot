package com.cifuentes.cristobal.gestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cifuentes.cristobal.gestion.model.entity.Task;
import com.cifuentes.cristobal.gestion.model.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByUser(User user);

}
