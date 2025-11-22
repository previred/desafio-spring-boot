package com.yoserp1.tareas.service;

import com.yoserp1.tareas.dto.TareaRequestDTO;
import com.yoserp1.tareas.dto.TareaResponseDTO;
import java.util.List;

public interface ITareaService {
  TareaResponseDTO crearTarea(TareaRequestDTO tareaRequestDTO);

  List<TareaResponseDTO> listarTareas();

  TareaResponseDTO obtenerTarea(Long id);

  TareaResponseDTO actualizarTarea(Long id, TareaRequestDTO tareaRequestDTO);

  void eliminarTarea(Long id);
}
