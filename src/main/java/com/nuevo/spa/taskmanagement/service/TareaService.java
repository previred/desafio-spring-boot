package com.nuevo.spa.taskmanagement.service;

import com.nuevo.spa.taskmanagement.model.EstadoTarea;
import com.nuevo.spa.taskmanagement.model.Tarea;
import com.nuevo.spa.taskmanagement.repository.EstadoTareaRepository;
import com.nuevo.spa.taskmanagement.repository.TareaRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {
    private final TareaRepository tareaRepository;
    private final EstadoTareaRepository estadoTareaRepository;

    public TareaService(TareaRepository tareaRepository,
    EstadoTareaRepository estadoTareaRepository) {
        this.tareaRepository = tareaRepository;
        this.estadoTareaRepository = estadoTareaRepository
        ;
    }

    public Page<Tarea> obtenerTodasLasTareas(Pageable pageable) {
        return tareaRepository.findAll(pageable);
    }

    public Tarea obtenerTareaPorId(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    public Tarea guardarTarea(Tarea tarea) {
        if (tarea.getId() != null && tareaRepository.existsById(tarea.getId())) {
            Tarea tareaExistente = tareaRepository.findById(tarea.getId())
                    .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

            // Solo actualizar los valores modificados
            if (tarea.getTitulo() != null) {
                tareaExistente.setTitulo(tarea.getTitulo());
            }
            if (tarea.getDescripcion() != null) {
                tareaExistente.setDescripcion(tarea.getDescripcion());
            }
            if (tarea.getUsuario() != null) {
                tareaExistente.setUsuario(tarea.getUsuario());
            }
            if (tarea.getEstado() != null) {
                tareaExistente.setEstado(tarea.getEstado());
            }

            return tareaRepository.save(tareaExistente);
        }

        return tareaRepository.save(tarea);
    }


    public void eliminarTarea(Long id) {
        tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        tareaRepository.deleteById(id);
    }

    public Optional<EstadoTarea> obtenerEstadoPorId(Long id) {
        return estadoTareaRepository.findById(id);
    }

    public boolean existeTarea(Long id) {
        return tareaRepository.findById(id).isPresent();
    }
}
