package gestion.tareas.demo.application.get.estadotarea;

import gestion.tareas.demo.infrastructure.db.entity.EstadoTarea;
import gestion.tareas.demo.infrastructure.db.repository.EstadoTareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEstadoTareaService {

    private final EstadoTareaRepository estadoTareaRepository;

    public EstadoTarea getEstadoTareaById(Long estadoId) {
        return estadoTareaRepository.findById(estadoId)
                .orElseThrow(() -> new RuntimeException("Estado de tarea no encontrado con id: " + estadoId));
    }
}
