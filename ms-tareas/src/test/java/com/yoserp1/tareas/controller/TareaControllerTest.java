package com.yoserp1.tareas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.yoserp1.tareas.dto.TareaRequestDTO;
import com.yoserp1.tareas.dto.TareaResponseDTO;
import com.yoserp1.tareas.service.ITareaService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class TareaControllerTest {

  @Mock private ITareaService tareaService;

  @InjectMocks private TareaController tareaController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Crea una tarea exitosamente")
  void creaTareaExitosamente() {
    TareaRequestDTO tareaRequestDTO =
        new TareaRequestDTO(1L, "Titulo", "Descripcion", "Estado", null, null);

    TareaResponseDTO tareaResponseDTO =
        new TareaResponseDTO(1L, "Titulo", "Descripcion", "Estado", null, null);
    when(tareaService.crearTarea(tareaRequestDTO)).thenReturn(tareaResponseDTO);

    ResponseEntity<TareaResponseDTO> response = tareaController.crearTarea(tareaRequestDTO);

    assertEquals(201, response.getStatusCodeValue());
    assertEquals(tareaResponseDTO, response.getBody());
    verify(tareaService, times(1)).crearTarea(tareaRequestDTO);
  }

  @Test
  @DisplayName("Lista todas las tareas exitosamente")
  void listaTodasLasTareasExitosamente() {
    List<TareaResponseDTO> tareas =
        Arrays.asList(
            new TareaResponseDTO(1L, "Titulo1", "Descripcion1", "Estado1", null, null),
            new TareaResponseDTO(2L, "Titulo2", "Descripcion2", "Estado2", null, null));
    when(tareaService.listarTareas()).thenReturn(tareas);

    ResponseEntity<List<TareaResponseDTO>> response = tareaController.listarTareas();

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(tareas, response.getBody());
    verify(tareaService, times(1)).listarTareas();
  }

  @Test
  @DisplayName("Obtiene una tarea por ID exitosamente")
  void obtieneTareaPorIdExitosamente() {
    TareaResponseDTO tareaRequestDTO =
        new TareaResponseDTO(1L, "Titulo", "Descripcion", "Estado", null, null);
    when(tareaService.obtenerTarea(1L)).thenReturn(tareaRequestDTO);

    ResponseEntity<TareaResponseDTO> response = tareaController.obtenerTarea(1L);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(tareaRequestDTO, response.getBody());
    verify(tareaService, times(1)).obtenerTarea(1L);
  }

  @Test
  @DisplayName("Actualiza una tarea exitosamente")
  void actualizaTareaExitosamente() {
    TareaRequestDTO tareaRequestDTO =
        new TareaRequestDTO(
            1L, "Titulo Actualizado", "Descripcion Actualizada", "Estado", null, null);
    TareaResponseDTO tareaResponseDTO =
        new TareaResponseDTO(
            1L, "Titulo Actualizado", "Descripcion Actualizada", "Estado", null, null);
    when(tareaService.actualizarTarea(1L, tareaRequestDTO)).thenReturn(tareaResponseDTO);

    ResponseEntity<TareaResponseDTO> response =
        tareaController.actualizarTarea(1L, tareaRequestDTO);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(tareaResponseDTO, response.getBody());
    verify(tareaService, times(1)).actualizarTarea(1L, tareaRequestDTO);
  }

  @Test
  @DisplayName("Elimina una tarea exitosamente")
  void eliminaTareaExitosamente() {
    doNothing().when(tareaService).eliminarTarea(1L);

    ResponseEntity<Void> response = tareaController.eliminarTarea(1L);

    assertEquals(204, response.getStatusCodeValue());
    verify(tareaService, times(1)).eliminarTarea(1L);
  }
}
