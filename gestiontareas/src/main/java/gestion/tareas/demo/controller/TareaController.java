package gestion.tareas.demo.controller;

import gestion.tareas.demo.model.StateAppDto;
import gestion.tareas.demo.model.TareaDto;
import gestion.tareas.demo.model.TareaRequestDto;
import gestion.tareas.demo.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TareaController implements TareasApi {
    @Autowired
    private TareaService tareaService;

    @Override
    public ResponseEntity<List<TareaDto>> tareasGet(@RequestParam Integer usuarioId) {
        return this.tareaService.tareasGet(usuarioId);
    }

    @Override
    public ResponseEntity<StateAppDto> tareasPost(@RequestBody TareaRequestDto tareaRequestDto) {
        return this.tareaService.tareasPost(tareaRequestDto);
    }

    @Override
    public ResponseEntity<StateAppDto> tareasIdPut(@PathVariable Long id, @RequestBody TareaRequestDto tareaRequestDto) {
        return this.tareaService.tareasIdPut(id , tareaRequestDto);
    }

    @Override
    public ResponseEntity<TareaDto> tareasIdGet(@PathVariable Long id) {
        return this.tareaService.tareasIdGet(id);
    }

    @Override
    public ResponseEntity<StateAppDto> tareasIdDelete(@PathVariable Long id){
        return this.tareaService.tareasIdDelete(id);
    }
}
