package com.nuevospa.controller;

import com.nuevospa.domain.Tarea;
import com.nuevospa.dto.TareaRequestDTO;
import com.nuevospa.dto.TareaResponseDTO;
import com.nuevospa.repository.TareaRepository;
import com.nuevospa.service.TareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;

    @GetMapping
    public List<TareaResponseDTO> listAll() {
        return tareaService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tarea> create(@RequestBody TareaRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tareaService.create(requestDTO));
    }

    @PutMapping("/{id}")
    public Tarea update(@PathVariable Long id, @RequestBody TareaRequestDTO requestDTO) {
        return tareaService.update(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tareaService.delete(id);
    }
}
