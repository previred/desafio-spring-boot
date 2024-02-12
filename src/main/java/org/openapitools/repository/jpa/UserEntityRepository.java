package org.openapitools.repository.jpa;

import org.openapitools.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    @Query("SELECT u FROM UserEntity u")
    Page<UserEntity> findAll(Pageable pageable);
}
