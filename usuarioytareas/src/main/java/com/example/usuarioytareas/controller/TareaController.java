package com.example.usuarioytareas.controller;

import com.example.usuarioytareas.dto.TareaRequest;
import com.example.usuarioytareas.dto.TareaResponse;
import com.example.usuarioytareas.entity.EstadoTarea;
import com.example.usuarioytareas.entity.Tarea;
import com.example.usuarioytareas.entity.Usuario;
import com.example.usuarioytareas.repository.EstadoTareaRepository;
import com.example.usuarioytareas.repository.TareaRepository;
import com.example.usuarioytareas.repository.UsuarioRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/tareas")
@Tag(name = "Tareas", description = "CRUD de tareas")
public class TareaController {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstadoTareaRepository estadoTareaRepository;

    @Operation(summary = "Listar todas las tareas")
    // GET: Listar tareas
    @GetMapping
    public List<TareaResponse> listar() {
        return tareaRepository.findAll().stream()
                .map(t -> new TareaResponse(
                        t.getId(),
                        t.getTitulo(),
                        t.getDescripcion(),
                        t.getUsuario().getUsername(),
                        t.getEstado().getNombre()
                ))
                .collect(Collectors.toList());
    }
    @Operation(summary = "Crear una nueva tarea")
    //  POST: Crear tarea
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody TareaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        EstadoTarea estado = estadoTareaRepository.findById(request.getEstadoId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        Tarea tarea = new Tarea();
        tarea.setTitulo(request.getTitulo());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setUsuario(usuario);
        tarea.setEstado(estado);

        tareaRepository.save(tarea);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Operation(summary = "Actualizar una tarea existente")
    // PUT: Actualizar tarea
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody TareaRequest request) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        EstadoTarea estado = estadoTareaRepository.findById(request.getEstadoId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        tarea.setTitulo(request.getTitulo());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setUsuario(usuario);
        tarea.setEstado(estado);

        tareaRepository.save(tarea);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @Operation(summary = "Eliminar una tarea por ID")
    // DELETE: Eliminar tarea
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!tareaRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        tareaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
