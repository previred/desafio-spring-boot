package gestion.tareas.demo.application.get.tarea;

import gestion.tareas.demo.db.TareaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetTareaGateway {
    private final GetTareaService getTareaService;

    public ResponseEntity<List<TareaDto>> tareasGet(Integer usuarioId) {
      return getTareaService.tareasGet(usuarioId);
    }

    public ResponseEntity<TareaDto> tareasIdGet(Long tareaId) {
        return getTareaService.tareasIdGet(tareaId);
    }
}
