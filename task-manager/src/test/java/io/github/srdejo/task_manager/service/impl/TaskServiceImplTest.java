package io.github.srdejo.task_manager.service.impl;

import io.github.srdejo.task_manager.controller.exception.ResourceNotFoundException;
import io.github.srdejo.task_manager.entity.EstadoTarea;
import io.github.srdejo.task_manager.entity.Tarea;
import io.github.srdejo.task_manager.entity.Usuario;
import io.github.srdejo.task_manager.mapper.TaskMapper;
import io.github.srdejo.task_manager.model.TaskEstado;
import io.github.srdejo.task_manager.model.TaskRequest;
import io.github.srdejo.task_manager.model.TaskResponse;
import io.github.srdejo.task_manager.repository.EstadoTareaRepository;
import io.github.srdejo.task_manager.repository.TareaRepository;
import io.github.srdejo.task_manager.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private EstadoTareaRepository estadoRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskRequest taskRequest;
    private Tarea tarea;
    private EstadoTarea estado;
    private Usuario usuario;
    private TaskResponse taskResponse;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .build();

        estado = EstadoTarea.builder()
                .id(1L)
                .nombre("PENDIENTE")
                .build();

        tarea = Tarea.builder()
                .id(1L)
                .titulo("Test Task")
                .descripcion("Test Description")
                .estado(estado)
                .usuario(usuario)
                .fechaCreacion(LocalDateTime.now())
                .build();

        taskRequest = new TaskRequest("Test Task", TaskEstado.NUMBER_1);
        taskRequest.setDescripcion("Test Description");

        taskResponse = new TaskResponse();
        taskResponse.setId(1L);
        taskResponse.setTitulo("Test Task");
        taskResponse.setDescripcion("Test Description");
        taskResponse.setEstado("PENDIENTE");
        taskResponse.setUsuario("testuser");
    }

    @Test
    void create_Successful() {
        // Arrange
        when(usuarioService.getCurrentUser()).thenReturn(usuario);
        when(estadoRepository.findById(1L)).thenReturn(Optional.of(estado));
        when(taskMapper.toEntity(taskRequest, usuario, estado)).thenReturn(tarea);
        when(tareaRepository.save(tarea)).thenReturn(tarea);
        when(taskMapper.toResponse(tarea)).thenReturn(taskResponse);

        // Act
        TaskResponse result = taskService.create(taskRequest);

        // Assert
        assertNotNull(result);
        assertEquals(taskResponse.getId(), result.getId());
        assertEquals(taskResponse.getTitulo(), result.getTitulo());
        verify(usuarioService).getCurrentUser();
        verify(estadoRepository).findById(1L);
        verify(taskMapper).toEntity(taskRequest, usuario, estado);
        verify(tareaRepository).save(tarea);
        verify(taskMapper).toResponse(tarea);
    }

    @Test
    void create_UserNotAuthenticated() {
        // Arrange
        when(usuarioService.getCurrentUser()).thenThrow(new ResourceNotFoundException("Usuario no autenticado"));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> taskService.create(taskRequest));
        assertEquals("Usuario no autenticado", exception.getMessage());
        verify(usuarioService).getCurrentUser();
        verify(estadoRepository, never()).findById(any());
        verify(taskMapper, never()).toEntity(any(), any(), any());
        verify(tareaRepository, never()).save(any());
    }

    @Test
    void create_UserNotFound() {
        // Arrange
        when(usuarioService.getCurrentUser()).thenThrow(new ResourceNotFoundException("Usuario no encontrado"));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> taskService.create(taskRequest));
        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioService).getCurrentUser();
        verify(estadoRepository, never()).findById(any());
        verify(taskMapper, never()).toEntity(any(), any(), any());
        verify(tareaRepository, never()).save(any());
    }

    @Test
    void create_EstadoNotFound() {
        // Arrange
        when(usuarioService.getCurrentUser()).thenReturn(usuario);
        when(estadoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> taskService.create(taskRequest));
        assertEquals("Estado no encontrado", exception.getMessage());
        verify(usuarioService).getCurrentUser();
        verify(estadoRepository).findById(1L);
        verify(taskMapper, never()).toEntity(any(), any(), any());
        verify(tareaRepository, never()).save(any());
    }

    @Test
    void findAll_Successful() {
        // Arrange
        List<Tarea> tareas = List.of(tarea);
        when(tareaRepository.findAll()).thenReturn(tareas);
        when(taskMapper.toResponse(tarea)).thenReturn(taskResponse);

        // Act
        List<TaskResponse> result = taskService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(taskResponse, result.getFirst());
        verify(tareaRepository).findAll();
        verify(taskMapper).toResponse(tarea);
    }

    @Test
    void findById_Successful() {
        // Arrange
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(taskMapper.toResponse(tarea)).thenReturn(taskResponse);

        // Act
        TaskResponse result = taskService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(taskResponse, result);
        verify(tareaRepository).findById(1L);
        verify(taskMapper).toResponse(tarea);
    }

    @Test
    void findById_TaskNotFound() {
        // Arrange
        when(tareaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> taskService.findById(1L));
        assertEquals("Tarea no encontrada", exception.getMessage());
        verify(tareaRepository).findById(1L);
        verify(taskMapper, never()).toResponse(any());
    }

    @Test
    void update_Successful() {
        // Arrange
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(estadoRepository.findById(1L)).thenReturn(Optional.of(estado));
        when(tareaRepository.save(tarea)).thenReturn(tarea);
        when(taskMapper.toResponse(tarea)).thenReturn(taskResponse);

        // Act
        TaskResponse result = taskService.update(1L, taskRequest);

        // Assert
        assertNotNull(result);
        assertEquals(taskResponse, result);
        verify(tareaRepository).findById(1L);
        verify(estadoRepository).findById(1L);
        verify(tareaRepository).save(tarea);
        verify(taskMapper).toResponse(tarea);
    }

    @Test
    void update_TaskNotFound() {
        // Arrange
        when(tareaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> taskService.update(1L, taskRequest));
        assertEquals("Tarea no encontrada", exception.getMessage());
        verify(tareaRepository).findById(1L);
        verify(estadoRepository, never()).findById(any());
        verify(tareaRepository, never()).save(any());
    }

    @Test
    void update_EstadoNotFound() {
        // Arrange
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(estadoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> taskService.update(1L, taskRequest));
        assertEquals("Estado no encontrado", exception.getMessage());
        verify(tareaRepository).findById(1L);
        verify(estadoRepository).findById(1L);
        verify(tareaRepository, never()).save(any());
    }

    @Test
    void delete_Successful() {
        // Arrange
        when(tareaRepository.existsById(1L)).thenReturn(true);

        // Act
        taskService.delete(1L);

        // Assert
        verify(tareaRepository).existsById(1L);
        verify(tareaRepository).deleteById(1L);
    }

    @Test
    void delete_TaskNotFound() {
        // Arrange
        when(tareaRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> taskService.delete(1L));
        assertEquals("Tarea no encontrada", exception.getMessage());
        verify(tareaRepository).existsById(1L);
        verify(tareaRepository, never()).deleteById(any());
    }
}