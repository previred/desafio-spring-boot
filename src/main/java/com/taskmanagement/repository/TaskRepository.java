package com.taskmanagement.repository;

import com.taskmanagement.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long userId);

    Page<Task> findByUserId(Long userId, Pageable pageable);

    List<Task> findByUserIdAndStateId(Long userId, Long stateId);

    Page<Task> findByUserIdAndStateId(Long userId, Long stateId, Pageable pageable);

    Optional<Task> findByIdAndUserId(Long id, Long userId);
}
