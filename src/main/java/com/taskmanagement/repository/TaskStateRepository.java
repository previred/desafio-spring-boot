package com.taskmanagement.repository;

import com.taskmanagement.entity.TaskState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskStateRepository extends JpaRepository<TaskState, Long> {

    Optional<TaskState> findByName(String name);
}
