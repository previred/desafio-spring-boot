package com.yoserp1.tareas.service;

import com.yoserp1.tareas.dto.TareaDTO;
import java.util.List;

public interface ITareaService {
  TareaDTO crearTarea(TareaDTO tareaDTO);

  List<TareaDTO> listarTareas();

  TareaDTO obtenerTarea(Long id);

  TareaDTO actualizarTarea(Long id, TareaDTO tareaDTO);

  void eliminarTarea(Long id);
}
