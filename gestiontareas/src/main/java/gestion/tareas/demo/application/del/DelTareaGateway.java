package gestion.tareas.demo.application.del;

import gestion.tareas.demo.db.StateAppDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DelTareaGateway {
    private final DelTareaService delTareaService;

    public ResponseEntity<StateAppDto> tareasIdDelete(Long tareaId) {
        return delTareaService.tareasIdDelete(tareaId);
    }
}
