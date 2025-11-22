package com.yoserp1.tareas.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

import com.yoserp1.tareas.dto.TareaDTO;
import com.yoserp1.tareas.exception.AplicacionException;
import com.yoserp1.tareas.jpa.entity.EstadoTareaEntity;
import com.yoserp1.tareas.jpa.entity.TareaEntity;
import com.yoserp1.tareas.jpa.entity.UsuarioEntity;
import com.yoserp1.tareas.jpa.repository.EstadoTareaRepository;
import com.yoserp1.tareas.jpa.repository.TareaRepository;
import com.yoserp1.tareas.jpa.repository.UsuarioRepository;
import com.yoserp1.tareas.mapper.TareaMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TareaServiceTest {

  @Mock private TareaRepository tareaRepository;

  @Mock private EstadoTareaRepository estadoTareaRepository;

  @Mock private UsuarioRepository usuarioRepository;

  @Mock private TareaMapper tareaMapper;

  @InjectMocks private TareaService tareaService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Crea una tarea exitosamente")
  void creaTareaExitosamente() {
    TareaDTO tareaDTO = new TareaDTO(null, "Titulo", "Descripcion", "Estado", null, 1L);
    TareaEntity tareaEntity = new TareaEntity();
    UsuarioEntity usuarioEntity = new UsuarioEntity();
    EstadoTareaEntity estadoEntity = new EstadoTareaEntity();

    when(tareaMapper.toEntity(tareaDTO)).thenReturn(tareaEntity);
    when(estadoTareaRepository.findByNombre("Estado")).thenReturn(Optional.of(estadoEntity));
    when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
    when(tareaRepository.save(tareaEntity)).thenReturn(tareaEntity);
    when(tareaMapper.toDTO(tareaEntity)).thenReturn(tareaDTO);

    TareaDTO result = tareaService.crearTarea(tareaDTO);

    assertEquals(tareaDTO, result);
    verify(tareaRepository, times(1)).save(tareaEntity);
  }

  @Test
  @DisplayName("Lanza excepción al crear tarea con estado inexistente")
  void lanzaExcepcionAlCrearTareaConEstadoInexistente() {
    TareaDTO tareaDTO = new TareaDTO(null, "Titulo", "Descripcion", "EstadoInvalido", null, null);
    TareaEntity tareaEntity = new TareaEntity();

    when(tareaMapper.toEntity(tareaDTO)).thenReturn(tareaEntity);
    when(estadoTareaRepository.findByNombre("EstadoInvalido")).thenReturn(Optional.empty());

    AplicacionException exception =
        assertThrows(AplicacionException.class, () -> tareaService.crearTarea(tareaDTO));

    assertEquals("Estado no encontrado: EstadoInvalido", exception.getMessage());
    verify(tareaRepository, never()).save(any());
  }

  @Test
  @DisplayName("Lista todas las tareas exitosamente")
  void listaTodasLasTareasExitosamente() {
    TareaEntity tareaEntity = new TareaEntity();
    TareaDTO tareaDTO = new TareaDTO(1L, "Titulo", "Descripcion", "Estado", null, null);

    when(tareaRepository.findAll()).thenReturn(List.of(tareaEntity));
    when(tareaMapper.toDTO(tareaEntity)).thenReturn(tareaDTO);

    List<TareaDTO> result = tareaService.listarTareas();

    assertEquals(1, result.size());
    assertEquals(tareaDTO, result.get(0));
    verify(tareaRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("Obtiene una tarea por ID exitosamente")
  void obtieneTareaPorIdExitosamente() {
    TareaEntity tareaEntity = new TareaEntity();
    TareaDTO tareaDTO = new TareaDTO(1L, "Titulo", "Descripcion", "Estado", null, null);

    when(tareaRepository.findById(1L)).thenReturn(Optional.of(tareaEntity));
    when(tareaMapper.toDTO(tareaEntity)).thenReturn(tareaDTO);

    TareaDTO result = tareaService.obtenerTarea(1L);

    assertEquals(tareaDTO, result);
    verify(tareaRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Lanza excepción al obtener tarea inexistente")
  void lanzaExcepcionAlObtenerTareaInexistente() {
    when(tareaRepository.findById(1L)).thenReturn(Optional.empty());

    AplicacionException exception =
        assertThrows(AplicacionException.class, () -> tareaService.obtenerTarea(1L));

    assertEquals("Tarea no encontrado: 1", exception.getMessage());
    verify(tareaRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Elimina una tarea exitosamente")
  void eliminaTareaExitosamente() {
    when(tareaRepository.existsById(1L)).thenReturn(true);
    doNothing().when(tareaRepository).deleteById(1L);

    tareaService.eliminarTarea(1L);

    verify(tareaRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Lanza excepción al eliminar tarea inexistente")
  void lanzaExcepcionAlEliminarTareaInexistente() {
    when(tareaRepository.existsById(1L)).thenReturn(false);

    AplicacionException exception =
        assertThrows(AplicacionException.class, () -> tareaService.eliminarTarea(1L));

    assertEquals("Tarea no encontrado: 1", exception.getMessage());
    verify(tareaRepository, never()).deleteById(any());
  }
}
