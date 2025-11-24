package com.api.nuevospa.repository;

import com.api.nuevospa.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByAssignedToId(Integer userId);
}
