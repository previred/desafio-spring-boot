package org.openapitools.repository.jpa;

import org.openapitools.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskEntityRepository extends JpaRepository<TaskEntity, UUID> {
}
