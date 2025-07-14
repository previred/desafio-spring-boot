package com.nuevospa.tasksmanagement.repositories;

import com.nuevospa.tasksmanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides CRUD operations and methods to find by username.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a User by their username.
     * @param username The username of the user.
     * @return An Optional containing the User if found, or empty otherwise.
     */
    Optional<User> findByUsername(String username);
}
