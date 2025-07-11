package com.ejemplo.spa.services;

import com.ejemplo.spa.mapper.TareaMapper;
import com.ejemplo.spa.dto.TareaDTO;
import com.ejemplo.spa.model.EstadoTarea;
import com.ejemplo.spa.model.Tarea;
import com.ejemplo.spa.model.Usuario;
import com.ejemplo.spa.repository.EstadoTareaRepository;
import com.ejemplo.spa.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaService {
    private final TareaRepository tareaRepository;
    private final EstadoTareaRepository estadoTareaRepository;
    private final TareaMapper tareaMapper;

    public TareaService(TareaRepository tareaRepository, EstadoTareaRepository estadoTareaRepository
            , TareaMapper tareaMapper) {
        this.tareaRepository = tareaRepository;
        this.estadoTareaRepository = estadoTareaRepository;
        this.tareaMapper = tareaMapper;
    }

    public List<TareaDTO> getTareasByUsuario(Usuario usuario) {
        return tareaRepository.findByUsuario(usuario)
                .stream()
                .map(tareaMapper::toDTO)
                .toList();
    }

    public TareaDTO getTareaById(Long id, Usuario usuario) {
        return tareaRepository.findByIdAndUsuarioId(id, usuario.getId())
                .map(tareaMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada o no pertenece al usuario"));
    }

    public TareaDTO crearTarea(TareaDTO dto, Usuario usuario) {
        EstadoTarea estado = estadoTareaRepository.findById(dto.getEstadoId())
                .orElseThrow(() -> new RuntimeException("Estado no válido"));
        Tarea tarea = new Tarea(dto.getTitulo(), dto.getDescripcion(), usuario, estado);
        Tarea tareaGuardada = tareaRepository.save(tarea);
        return tareaMapper.toDTO(tareaGuardada);
    }

    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }

    public TareaDTO actualizarTarea(Long id, TareaDTO dto, Usuario usuario) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        if (!tarea.getUsuario().getId().equals(usuario.getId())) throw new RuntimeException("No autorizado");

        EstadoTarea estado = estadoTareaRepository.findById(dto.getEstadoId())
                .orElseThrow(() -> new RuntimeException("Estado no válido"));

        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setEstado(estado);
        Tarea tareaActualizada = tareaRepository.save(tarea);
        return tareaMapper.toDTO(tareaActualizada);
    }
}
