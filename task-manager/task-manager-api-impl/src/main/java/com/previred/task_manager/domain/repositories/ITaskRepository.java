package com.previred.task_manager.domain.repositories;

import com.previred.task_manager.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITaskRepository extends JpaRepository<Task, UUID> {
}
