package com.nuevospa.tasksmanagement.repositories;

import com.nuevospa.tasksmanagement.entities.Task;
import com.nuevospa.tasksmanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Task entity.
 * Provides CRUD operations and custom queries for tasks.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
