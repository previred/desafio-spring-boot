package cl.previred.desafio.controller;

import cl.previred.desafio.api.TareasApi;
import cl.previred.desafio.model.Tarea;
import cl.previred.desafio.model.TareaCreate;
import cl.previred.desafio.service.TareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TareasController implements TareasApi {

    private final TareaService tareaService;

    @Override
    public ResponseEntity<List<Tarea>> tareasGet() {
        return ResponseEntity.ok(tareaService.listarTareas());
    }

    @Override
    public ResponseEntity<Void> tareasPost(TareaCreate tareaCreate) {
        tareaService.crearTarea(tareaCreate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> tareasIdPut(Long id, TareaCreate tareaCreate) {
        tareaService.actualizarTarea(id, tareaCreate);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> tareasIdDelete(Long id) {
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }
}