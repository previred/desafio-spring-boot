package com.nuevo.taskmanager.repositories;


import com.nuevo.taskmanager.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {}
