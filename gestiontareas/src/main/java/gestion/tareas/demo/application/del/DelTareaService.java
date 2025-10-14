package gestion.tareas.demo.application.del;

import gestion.tareas.demo.application.shared.TaskMessageBuilder;
import gestion.tareas.demo.db.StateAppDto;
import gestion.tareas.demo.infrastructure.db.repository.TareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DelTareaService {
    private final TareaRepository tareaRepository;
    private final TaskMessageBuilder messageBuilder;

    public ResponseEntity<StateAppDto> tareasIdDelete(Long tareaId) {

        boolean exists = tareaRepository.existsById(tareaId);

        if (exists) {
            tareaRepository.deleteById(tareaId);
        }

        StateAppDto response = messageBuilder.buildMessage(tareaId, exists);
        return ResponseEntity.ok(response);
    }

}
