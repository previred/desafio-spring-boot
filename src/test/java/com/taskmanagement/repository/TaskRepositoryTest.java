package com.taskmanagement.repository;

import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.TaskState;
import com.taskmanagement.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.sql.init.mode=never"
})
@DisplayName("TaskRepository Tests")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStateRepository taskStateRepository;

    private User testUser;
    private TaskState todoState;
    private TaskState doneState;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
        taskStateRepository.deleteAll();

        todoState = taskStateRepository.save(new TaskState("TODO", "Pendiente"));
        doneState = taskStateRepository.save(new TaskState("DONE", "Completada"));

        testUser = userRepository.save(User.builder()
                .username("mgarcia")
                .email("maria@test.com")
                .password("password")
                .fullName("María García")
                .build());

        taskRepository.save(Task.builder()
                .title("Tarea 1")
                .description("Descripción 1")
                .user(testUser)
                .state(todoState)
                .build());

        taskRepository.save(Task.builder()
                .title("Tarea 2")
                .description("Descripción 2")
                .user(testUser)
                .state(doneState)
                .build());
    }

    @Test
    @DisplayName("Should find all tasks by user id")
    void shouldFindByUserId() {
        List<Task> tasks = taskRepository.findByUserId(testUser.getId());

        assertThat(tasks).hasSize(2);
    }

    @Test
    @DisplayName("Should find tasks by user id with pagination")
    void shouldFindByUserIdWithPagination() {
        Page<Task> page = taskRepository.findByUserId(testUser.getId(), PageRequest.of(0, 10));

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should find tasks by user id and state id")
    void shouldFindByUserIdAndStateId() {
        List<Task> todoTasks = taskRepository.findByUserIdAndStateId(testUser.getId(), todoState.getId());

        assertThat(todoTasks).hasSize(1);
        assertThat(todoTasks.get(0).getTitle()).isEqualTo("Tarea 1");
    }

    @Test
    @DisplayName("Should find task by id and user id")
    void shouldFindByIdAndUserId() {
        Task task = taskRepository.findByUserId(testUser.getId()).get(0);

        var found = taskRepository.findByIdAndUserId(task.getId(), testUser.getId());

        assertThat(found).isPresent();
    }

    @Test
    @DisplayName("Should return empty when task belongs to another user")
    void shouldReturnEmptyWhenTaskBelongsToAnotherUser() {
        Task task = taskRepository.findByUserId(testUser.getId()).get(0);

        var found = taskRepository.findByIdAndUserId(task.getId(), 999L);

        assertThat(found).isEmpty();
    }
}
