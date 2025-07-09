package com.ml.GestionDeTareas.services;

import com.ml.GestionDeTareas.dao.Tarea;
import com.ml.GestionDeTareas.repository.TareaRepository;
import com.ml.GestionDeTareas.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaServiceImpl implements TareaService{

    @Autowired
    TareaRepository tareaRepository;

    public List<Tarea> listaTodasLasTareas() {
        return tareaRepository.findAll();
    }

    public Tarea crearTarea(Tarea tarea) {
        if (tarea.getUsuario() == null || tarea.getUsuario().getId() <= 0) {
            throw new ResourceNotFoundException("Debe proporcionar un usuario válido.");
        }

        if (tarea.getEstado() == null || tarea.getEstado().getId() <= 0) {
            throw new ResourceNotFoundException("Debe proporcionar un estado válido.");
        }

        return tareaRepository.save(tarea);

    }

    public Tarea actualizarTarea(int id, Tarea tarea) {
        return tareaRepository.findById(id)
                .map(t -> {
                    t.setTitulo(tarea.getTitulo());
                    t.setDescripcion(tarea.getDescripcion());
                    t.setUsuario(tarea.getUsuario());
                    t.setEstado(tarea.getEstado());
                    return tareaRepository.save(t);
                })
                .orElseThrow(() -> new ResourceNotFoundException("No existe la tarea con id: " + id));
    }

    public void eliminarTarea(int id) {
        if (tareaRepository.existsById(id)) {
            tareaRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Tarea no encontrada.");
        }
    }
}
