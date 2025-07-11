package com.ejemplo.spa.controller;

import com.ejemplo.spa.dto.TareaDTO;
import com.ejemplo.spa.model.Usuario;
import com.ejemplo.spa.services.TareaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tareas")
public class TareaController {
    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public ResponseEntity<List<TareaDTO>> listar(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(tareaService.getTareasByUsuario(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaDTO> obtener(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(tareaService.getTareaById(id, usuario));
    }

    @PostMapping
    public ResponseEntity<TareaDTO> crear(@RequestBody TareaDTO dto, @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(tareaService.crearTarea(dto, usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaDTO> actualizar(@PathVariable Long id, @RequestBody TareaDTO dto, @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(tareaService.actualizarTarea(id, dto, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }
}
