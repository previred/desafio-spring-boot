package com.taskmanagement.repository;

import com.taskmanagement.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.sql.init.mode=never"
})
@DisplayName("UserRepository Tests")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        testUser = User.builder()
                .username("mgarcia")
                .email("maria.garcia@nuevospa.cl")
                .password("hashedPassword123")
                .fullName("María García")
                .build();
        testUser = userRepository.save(testUser);
    }

    @Test
    @DisplayName("Should find user by username")
    void shouldFindByUsername() {
        Optional<User> found = userRepository.findByUsername("mgarcia");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("maria.garcia@nuevospa.cl");
    }

    @Test
    @DisplayName("Should find user by email")
    void shouldFindByEmail() {
        Optional<User> found = userRepository.findByEmail("maria.garcia@nuevospa.cl");

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("mgarcia");
    }

    @Test
    @DisplayName("Should return empty when username not found")
    void shouldReturnEmptyWhenUsernameNotFound() {
        Optional<User> found = userRepository.findByUsername("nonexistent");

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should check if username exists")
    void shouldCheckUsernameExists() {
        assertThat(userRepository.existsByUsername("mgarcia")).isTrue();
        assertThat(userRepository.existsByUsername("unknown")).isFalse();
    }

    @Test
    @DisplayName("Should check if email exists")
    void shouldCheckEmailExists() {
        assertThat(userRepository.existsByEmail("maria.garcia@nuevospa.cl")).isTrue();
        assertThat(userRepository.existsByEmail("unknown@test.com")).isFalse();
    }
}
