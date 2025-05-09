package com.example.usuarioytareas.service;


import com.example.usuarioytareas.entity.Tarea;
import com.example.usuarioytareas.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {
    
    @Autowired
    private TareaRepository tareaRepository;

    public List<Tarea> listarTareas() {
        return tareaRepository.findAll();
    }

    public List<Tarea> listarTareasPorUsuario(Long usuarioId) {
        return tareaRepository.findByUsuarioId(usuarioId);
    }

    public Optional<Tarea> obtenerPorId(Long id) {
        return tareaRepository.findById(id);
    }

    public Tarea crearTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public Tarea actualizarTarea(Long id, Tarea tareaActualizada) {
        return tareaRepository.findById(id).map(tarea -> {
            tarea.setTitulo(tareaActualizada.getTitulo());
            tarea.setDescripcion(tareaActualizada.getDescripcion());
            tarea.setEstado(tareaActualizada.getEstado());
            return tareaRepository.save(tarea);
        }).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }
}
