package cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.repositories;

import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities.TaskJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, String> {

    Optional<TaskJpaEntity> findById(String id);
    List<TaskJpaEntity> findByCreatedBy(String createdBy);
    List<TaskJpaEntity> findByStatus(String status);

    List<TaskJpaEntity> findByCreatedBetween(LocalDateTime start, LocalDateTime end);
    List<TaskJpaEntity> findByLastUpdateBetween(LocalDateTime start, LocalDateTime end);


    // Actualización directa de estado
    @Modifying
    @Query("UPDATE TaskJpaEntity t SET t.status = :status, t.lastUpdate = CURRENT_TIMESTAMP WHERE t.id = :id")
    int updateStatus(@Param("id") String id, @Param("status") String status);

}
