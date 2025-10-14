package gestion.tareas.demo.infrastructure.controller;

import gestion.tareas.demo.application.del.DelTareaGateway;
import gestion.tareas.demo.application.get.tarea.GetTareaGateway;
import gestion.tareas.demo.application.post.PostTareaGateway;
import gestion.tareas.demo.application.put.PutTareaGateway;
import gestion.tareas.demo.controller.TareasApi;
import gestion.tareas.demo.db.StateAppDto;
import gestion.tareas.demo.db.TareaDto;
import gestion.tareas.demo.db.TareaRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TareaController implements TareasApi {
    private final GetTareaGateway getTareaGateway;
    private final PostTareaGateway postTareaGateway;
    private final PutTareaGateway putTareaGateway;
    private final DelTareaGateway delTareaGateway;

    @Override
    public ResponseEntity<List<TareaDto>> tareasGet(@RequestParam Integer usuarioId) {
        return getTareaGateway.tareasGet(usuarioId);
    }

    @Override
    public ResponseEntity<StateAppDto> tareasPost(@RequestBody TareaRequestDto tareaRequestDto) {
        return postTareaGateway.tareasPost(tareaRequestDto);
    }

    @Override
    public ResponseEntity<StateAppDto> tareasIdPut(@PathVariable Long id, @RequestBody TareaRequestDto tareaRequestDto) {
        return putTareaGateway.tareasIdPut(id , tareaRequestDto);
    }

    @Override
    public ResponseEntity<TareaDto> tareasIdGet(@PathVariable Long id) {
        return getTareaGateway.tareasIdGet(id);
    }

    @Override
    public ResponseEntity<StateAppDto> tareasIdDelete(@PathVariable Long id){
        return delTareaGateway.tareasIdDelete(id);
    }
}
