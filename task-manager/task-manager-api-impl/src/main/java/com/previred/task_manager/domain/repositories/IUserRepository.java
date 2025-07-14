package com.previred.task_manager.domain.repositories;

import com.previred.task_manager.domain.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailAndIsActiveIsTrue(String email);
    boolean existsByTokenAndIsActiveIsTrue(String token);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.token = :token, u.lastLogin = :lastLogin WHERE u.id = :userId")
    void updateTokenAndLastLogin(@Param("token") String token,
                                 @Param("lastLogin") LocalDateTime lastLogin,
                                 @Param("userId") UUID userId);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.token = :token WHERE u.id = :userId")
    void updateToken(@Param("token") String token,
                     @Param("userId") UUID userId);
}
