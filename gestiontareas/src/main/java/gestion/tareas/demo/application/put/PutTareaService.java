package gestion.tareas.demo.application.put;

import gestion.tareas.demo.application.get.auth.GetAuthGateway;
import gestion.tareas.demo.application.get.estadotarea.GetEstadoTareaGateway;
import gestion.tareas.demo.application.shared.TaskMessageBuilder;
import gestion.tareas.demo.db.StateAppDto;
import gestion.tareas.demo.db.TareaRequestDto;
import gestion.tareas.demo.infrastructure.db.entity.EstadoTarea;
import gestion.tareas.demo.infrastructure.db.entity.Tareas;
import gestion.tareas.demo.infrastructure.db.repository.TareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PutTareaService {

    private final TareaRepository tareaRepository;
    private final GetAuthGateway getAuthGateway;
    private final GetEstadoTareaGateway getEstadoTareaGateway;
    private final TaskMessageBuilder messageBuilder;

    public ResponseEntity<StateAppDto> tareasIdPut(Long tareaId, TareaRequestDto tareaRequestDto) {
        Optional<Tareas> tareaOptional = tareaRepository.findById(tareaId);

        if (tareaOptional.isEmpty()) {
            return ResponseEntity.ok(messageBuilder.buildMessage(tareaId, false));
        }

        Tareas tarea = tareaOptional.get();
        Long userId = getAuthGateway.obtenerUsuarioAutenticado().getId();
        EstadoTarea estadoTarea = getEstadoTareaGateway.getEstadoTareaById(tareaRequestDto.getState().getValue());

        tarea.setDescripcion(tareaRequestDto.getDescripcion());
        tarea.setEstado(estadoTarea);
        tarea.setFechaVencimiento(tareaRequestDto.getExpirationDate().toLocalDateTime());
        tarea.setUsuarioId(userId);

        tareaRepository.save(tarea);

        StateAppDto response = messageBuilder.buildMessage(tareaId, true);
        return ResponseEntity.ok(response);
    }
}