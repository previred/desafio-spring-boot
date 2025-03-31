package com.hfsolutions.challenge.task_management.task.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

    List<TaskEntity> findByUserId(UUID userId);

    Optional<TaskEntity> findByIdAndUserId(UUID id, UUID userId);
    
}
