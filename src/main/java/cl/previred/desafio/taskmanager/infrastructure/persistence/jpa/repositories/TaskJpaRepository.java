package cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.repositories;

import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities.TaskJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, String> {

    Optional<TaskJpaEntity> findById(String id);

    // Actualización directa de estado
    @Modifying
    @Query("UPDATE TaskJpaEntity t SET t.status = :status, t.lastUpdate = CURRENT_TIMESTAMP WHERE t.id = :id")
    int updateStatus(@Param("id") String id, @Param("status") String status);

}
