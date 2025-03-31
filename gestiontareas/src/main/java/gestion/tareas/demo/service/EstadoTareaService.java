package gestion.tareas.demo.service;

import gestion.tareas.demo.model.EstadoTarea;
import gestion.tareas.demo.repository.EstadoTareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoTareaService {

    private final EstadoTareaRepository estadoTareaRepository;

    @Autowired
    public EstadoTareaService(EstadoTareaRepository estadoTareaRepository) {
        this.estadoTareaRepository = estadoTareaRepository;
    }

    public EstadoTarea getEstadoTareaById(Long estadoId) {
        return estadoTareaRepository.findById(estadoId)
                .orElseThrow(() -> new RuntimeException("Estado de tarea no encontrado con id: " + estadoId));
    }
}
