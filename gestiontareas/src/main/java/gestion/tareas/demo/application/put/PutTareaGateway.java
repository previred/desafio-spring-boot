package gestion.tareas.demo.application.put;

import gestion.tareas.demo.db.StateAppDto;
import gestion.tareas.demo.db.TareaRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PutTareaGateway {
    private final PutTareaService putTareaService;

    public ResponseEntity<StateAppDto> tareasIdPut(Long tareaId, TareaRequestDto tareaRequestDto) {
        return putTareaService.tareasIdPut(tareaId, tareaRequestDto);
    }
}
