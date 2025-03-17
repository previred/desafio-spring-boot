package com.nuevo.spa.taskmanagement.controller;

import com.nuevo.spa.taskmanagement.generated.api.TareaApi;
import com.nuevo.spa.taskmanagement.generated.model.GetAllTareas200Response;
import com.nuevo.spa.taskmanagement.generated.model.TareaDto;
import com.nuevo.spa.taskmanagement.mapper.TareaMapper;
import com.nuevo.spa.taskmanagement.mapper.TareaPageMapper;
import com.nuevo.spa.taskmanagement.model.Tarea;
import com.nuevo.spa.taskmanagement.service.TareaService;
import com.nuevo.spa.taskmanagement.service.UsuarioService;
import com.nuevo.spa.taskmanagement.validation.TareaValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;


@RestController
public class TareaController implements TareaApi {

    private final TareaService tareaService;
    private final UsuarioService usuarioService;
    private final TareaValidator tareaValidator;

    public TareaController(TareaService tareaService, UsuarioService usuarioService, TareaValidator tareaValidator) {
        this.tareaService = tareaService;
        this.usuarioService = usuarioService;
        this.tareaValidator = tareaValidator;
    }

    @Override
    public ResponseEntity<TareaDto> createTarea(TareaDto tareaDTO) {
        try {
            tareaValidator.validarTareaDto(tareaDTO);
            var nuevaTarea = tareaService.guardarTarea(TareaMapper.toEntity(tareaDTO));
            return ResponseEntity.status(201).body(TareaMapper.toDTO(nuevaTarea));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Override
    public ResponseEntity<Void> deleteTarea(Integer id) {
        try {
            tareaService.eliminarTarea(id.longValue());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<GetAllTareas200Response> getAllTareas(Integer page,Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Tarea> tareasPage = tareaService.obtenerTodasLasTareas(pageable);
        
        GetAllTareas200Response response = TareaPageMapper.toGetAllTareas200Response(tareasPage);

        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<TareaDto> getTareaById(Integer id) {
        try {
            Tarea tarea = tareaService.obtenerTareaPorId(id.longValue());
            return ResponseEntity.ok(TareaMapper.toDTO(tarea));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<TareaDto> updateTarea(Integer id, TareaDto tareaDTO) {
        try {
            tareaDTO.setId(id); // Aseguramos que el ID es el correcto
            Tarea tareaActualizada = tareaService.guardarTarea(TareaMapper.toEntity(tareaDTO));
            return ResponseEntity.ok(TareaMapper.toDTO(tareaActualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
