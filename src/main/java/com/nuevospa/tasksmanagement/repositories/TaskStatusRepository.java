package com.nuevospa.tasksmanagement.repositories;

import com.nuevospa.tasksmanagement.entities.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for TaskStatus entity.
 * Provides CRUD operations and methods to find by code.
 */
@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    /**
     * Finds a TaskStatus by its code.
     * @param code The code of the task status (e.g., "PENDING").
     * @return An Optional containing the TaskStatus if found, or empty otherwise.
     */
    Optional<TaskStatus> findByCode(String code);
}
