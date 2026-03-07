package cl.cediaz.desafiospringboot.service;

import cl.cediaz.desafiospringboot.dto.TaskRequest;
import cl.cediaz.desafiospringboot.dto.TaskResponse;
import cl.cediaz.desafiospringboot.entity.Task;
import cl.cediaz.desafiospringboot.entity.TaskStatus;
import cl.cediaz.desafiospringboot.entity.User;
import cl.cediaz.desafiospringboot.exception.ResourceNotFoundException;
import cl.cediaz.desafiospringboot.mapper.TaskMapper;
import cl.cediaz.desafiospringboot.repository.TaskRepository;
import cl.cediaz.desafiospringboot.repository.TaskStatusRepository;
import cl.cediaz.desafiospringboot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskStatusRepository taskStatusRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testFindByIdSuccess() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(Task.builder().id(1L).title("Task").build()));
        when(taskMapper.toResponse(any(Task.class))).thenReturn(new TaskResponse(
                1L,
                "Task",
                null,
                null,
                null,
                null,
                List.of()
        ));

        TaskResponse result = taskService.findById(1L);

        assertThat(result.title()).isEqualTo("Task");
    }

    @Test
    void testFindByIdNotFound() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testSaveSuccess() {

        TaskRequest request = new TaskRequest(
                "New Task",
                "Desc",
                1L,
                List.of(1L),
                LocalDateTime.now()
        );

        when(taskStatusRepository.findById(anyLong())).thenReturn(Optional.of(TaskStatus.builder().id(1L).name("OPEN").build()));
        when(userRepository.findAllById(any())).thenReturn(List.of(User.builder().id(1L).username("user").build()));
        when(taskMapper.toEntity(any(TaskRequest.class), any(TaskStatus.class), any())).thenReturn(Task.builder().id(1L).title("New Task").build());
        when(taskRepository.save(any(Task.class))).thenReturn(Task.builder().id(1L).title("New Task").build());
        when(taskMapper.toResponse(any(Task.class))).thenReturn(new TaskResponse(
                1L,
                "Task",
                null,
                null,
                null,
                null,
                List.of()
        ));

        TaskResponse result = taskService.save(request);

        assertThat(result.title()).isEqualTo("New Task");
    }

    @Test
    void testDeleteSuccess() {
        when(taskRepository.existsById(anyLong())).thenReturn(true);

        taskService.delete(1L);

        verify(taskRepository).deleteById(anyLong());
    }

    @Test
    void testDeleteNotFound() {
        when(taskRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> taskService.delete(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testAssignTaskToUserSuccess() {
        Task task = Task.builder().id(1L).title("Task").users(new java.util.ArrayList<>()).build();
        User user = User.builder().id(1L).username("testuser").tasks(new java.util.ArrayList<>()).build();
        TaskResponse response = new TaskResponse(1L, "Task", null, null, null, null, List.of());


        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toResponse(any(Task.class))).thenReturn(response);

        TaskResponse result = taskService.assignTaskToUser(1L, 1L);

        assertThat(result.title()).isEqualTo("Task");
    }

    @Test
    void testFindAll() {
        when(taskRepository.findAll()).thenReturn(List.of(Task.builder().id(1L).title("Task 1").build()));
        when(taskMapper.toResponseList(any())).thenReturn(List.of(new TaskResponse(1L, "Task 1", null, null, null, null, List.of())));

        List<TaskResponse> result = taskService.findAll();

        assertThat(result).hasSize(1);
    }

    @Test
    void testUpdateSuccess() {

        TaskRequest request = new TaskRequest(
                "Updated Task",
                "Updated Desc",
                1L,
                List.of(1L),
                LocalDateTime.now()
        );

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(Task.builder().id(1L).title("Old Task").build()));
        when(taskStatusRepository.findById(anyLong())).thenReturn(Optional.of(TaskStatus.builder().id(1L).name("OPEN").build()));
        when(userRepository.findAllById(any())).thenReturn(List.of(User.builder().id(1L).username("user").build()));
        when(taskRepository.save(any(Task.class))).thenReturn(Task.builder().id(1L).title("Updated Task").build());
        when(taskMapper.toResponse(any(Task.class))).thenReturn(new TaskResponse(1L, "Updated Task", null, null, null, null, List.of()));

        TaskResponse result = taskService.update(1L, request);

        assertThat(result.title()).isEqualTo("Updated Task");
    }
}
