package dsegovia.desafio.controller;

import dsegovia.desafio.dto.EstadoTareaDTO;
import dsegovia.desafio.entity.EstadoTarea;
import dsegovia.desafio.mapper.EstadoTareaMapper;
import dsegovia.desafio.repository.EstadoTareaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador para operaciones con estados de tarea
 */
@RestController
@RequestMapping("/api/estados")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class EstadoTareaController {

    private final EstadoTareaRepository estadoTareaRepository;
    private final EstadoTareaMapper estadoTareaMapper;

    public EstadoTareaController(EstadoTareaRepository estadoTareaRepository,
                                 EstadoTareaMapper estadoTareaMapper) {
        this.estadoTareaRepository = estadoTareaRepository;
        this.estadoTareaMapper = estadoTareaMapper;
    }

    @GetMapping
    public ResponseEntity<List<EstadoTareaDTO>> listarEstados() {
        List<EstadoTarea> estados = estadoTareaRepository.findAll();
        return ResponseEntity.ok(estadoTareaMapper.toDtoList(estados));
    }
}