package com.taskmanagement.repository;

import com.taskmanagement.entity.TaskState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.sql.init.mode=never"
})
@DisplayName("TaskStateRepository Tests")
class TaskStateRepositoryTest {

    @Autowired
    private TaskStateRepository taskStateRepository;

    @BeforeEach
    void setUp() {
        taskStateRepository.deleteAll();
        taskStateRepository.save(new TaskState("TODO", "Tarea pendiente"));
        taskStateRepository.save(new TaskState("IN_PROGRESS", "En desarrollo"));
        taskStateRepository.save(new TaskState("DONE", "Completada"));
    }

    @Test
    @DisplayName("Should find all task states")
    void shouldFindAllTaskStates() {
        List<TaskState> states = taskStateRepository.findAll();

        assertThat(states).hasSize(3);
    }

    @Test
    @DisplayName("Should find task state by name")
    void shouldFindByName() {
        Optional<TaskState> todoState = taskStateRepository.findByName("TODO");

        assertThat(todoState).isPresent();
        assertThat(todoState.get().getName()).isEqualTo("TODO");
    }

    @Test
    @DisplayName("Should return empty when state name not found")
    void shouldReturnEmptyWhenNameNotFound() {
        Optional<TaskState> notFound = taskStateRepository.findByName("NONEXISTENT");

        assertThat(notFound).isEmpty();
    }

    @Test
    @DisplayName("Should find task state by id")
    void shouldFindById() {
        TaskState saved = taskStateRepository.save(new TaskState("TEST", "Test state"));

        Optional<TaskState> found = taskStateRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("TEST");
    }
}
