package gestion.tareas.demo.application.post;

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

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostTareaService {
    private final GetAuthGateway getAuthGateway;
    private final TareaRepository tareaRepository;
    private final GetEstadoTareaGateway getEstadoTareaGateway;
    private final TaskMessageBuilder messageBuilder;

    public ResponseEntity<StateAppDto> tareasPost(TareaRequestDto tareaRequestDto) {
        Long userId = getAuthGateway.obtenerUsuarioAutenticado().getId();
        EstadoTarea estadoTarea = getEstadoTareaGateway.getEstadoTareaById(tareaRequestDto.getState().getValue());

        Tareas tareas = new Tareas();
        tareas.setDescripcion(tareaRequestDto.getDescripcion());
        tareas.setFechaCreacion(LocalDateTime.now());
        tareas.setFechaVencimiento(tareaRequestDto.getExpirationDate().toLocalDateTime());
        tareas.setUsuarioId(userId);
        tareas.setEstado(estadoTarea);

        tareaRepository.save(tareas);

        StateAppDto response = messageBuilder.buildMessage(tareas.getId(), true);
        return ResponseEntity.ok(response);

    }
}
