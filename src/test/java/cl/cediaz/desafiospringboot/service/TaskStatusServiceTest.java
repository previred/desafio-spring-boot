package cl.cediaz.desafiospringboot.service;

import cl.cediaz.desafiospringboot.entity.TaskStatus;
import cl.cediaz.desafiospringboot.exception.ResourceNotFoundException;
import cl.cediaz.desafiospringboot.repository.TaskStatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskStatusServiceTest {

    @Mock
    private TaskStatusRepository repository;

    @InjectMocks
    private TaskStatusService service;

    @Test
    void testFindByNameSuccess() {
        TaskStatus status = TaskStatus.builder().id(1L).name("OPEN").build();

        when(repository.findByNameIgnoreCase(anyString())).thenReturn(Optional.of(status));

        TaskStatus result = service.findByName("OPEN");

        assertThat(result.getName()).isEqualTo("OPEN");
    }

    @Test
    void testFindByNameNotFound() {
        when(repository.findByNameIgnoreCase(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findByName("INVALID"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Estado no encontrado");
    }
}