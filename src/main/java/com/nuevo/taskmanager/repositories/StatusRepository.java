package com.nuevo.taskmanager.repositories;


import com.nuevo.taskmanager.entities.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {

    Optional<StatusEntity> findByName(String name);
}
