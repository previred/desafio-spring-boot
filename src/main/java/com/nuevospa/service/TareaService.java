package com.nuevospa.service;

import com.nuevospa.domain.Tarea;
import com.nuevospa.dto.TareaRequestDTO;
import com.nuevospa.dto.TareaResponseDTO;

import java.util.List;

public interface TareaService {

    List<TareaResponseDTO> findAll();
    Tarea findById(Long id);
    Tarea create(TareaRequestDTO requestDTO);
    Tarea update(Long id, TareaRequestDTO requestDTO);
    void delete(Long id);
}
