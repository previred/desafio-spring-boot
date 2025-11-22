package com.yoserp1.tareas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.yoserp1.tareas.dto.TareaDTO;
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
    TareaDTO tareaDTO = new TareaDTO(1L, "Titulo", "Descripcion", "Estado", null, null);
    when(tareaService.crearTarea(tareaDTO)).thenReturn(tareaDTO);

    ResponseEntity<TareaDTO> response = tareaController.crearTarea(tareaDTO);

    assertEquals(201, response.getStatusCodeValue());
    assertEquals(tareaDTO, response.getBody());
    verify(tareaService, times(1)).crearTarea(tareaDTO);
  }

  @Test
  @DisplayName("Lista todas las tareas exitosamente")
  void listaTodasLasTareasExitosamente() {
    List<TareaDTO> tareas =
        Arrays.asList(
            new TareaDTO(1L, "Titulo1", "Descripcion1", "Estado1", null, null),
            new TareaDTO(2L, "Titulo2", "Descripcion2", "Estado2", null, null));
    when(tareaService.listarTareas()).thenReturn(tareas);

    ResponseEntity<List<TareaDTO>> response = tareaController.listarTareas();

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(tareas, response.getBody());
    verify(tareaService, times(1)).listarTareas();
  }

  @Test
  @DisplayName("Obtiene una tarea por ID exitosamente")
  void obtieneTareaPorIdExitosamente() {
    TareaDTO tareaDTO = new TareaDTO(1L, "Titulo", "Descripcion", "Estado", null, null);
    when(tareaService.obtenerTarea(1L)).thenReturn(tareaDTO);

    ResponseEntity<TareaDTO> response = tareaController.obtenerTarea(1L);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(tareaDTO, response.getBody());
    verify(tareaService, times(1)).obtenerTarea(1L);
  }

  @Test
  @DisplayName("Actualiza una tarea exitosamente")
  void actualizaTareaExitosamente() {
    TareaDTO tareaDTO =
        new TareaDTO(1L, "Titulo Actualizado", "Descripcion Actualizada", "Estado", null, null);
    when(tareaService.actualizarTarea(1L, tareaDTO)).thenReturn(tareaDTO);

    ResponseEntity<TareaDTO> response = tareaController.actualizarTarea(1L, tareaDTO);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(tareaDTO, response.getBody());
    verify(tareaService, times(1)).actualizarTarea(1L, tareaDTO);
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
