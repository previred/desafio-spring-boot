package com.nuevospa.service;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nuevospa.model.EstadoTarea;
import com.nuevospa.model.Tarea;
import com.nuevospa.api.TareasApiDelegate;
import com.nuevospa.exception.CamposRequeridosVaciosException;
import com.nuevospa.exception.RecursoNoEncontradoException;
import com.nuevospa.mapper.TareaMapper;
import com.nuevospa.model.TareaDto;
import com.nuevospa.model.TareaNuevaDto;
import com.nuevospa.model.Usuario;
import com.nuevospa.repository.EstadoTareaRepository;
import com.nuevospa.repository.TareaRepository;
import com.nuevospa.repository.UsuarioRepository;

import java.util.List;

@Service
public class TareasApiDelegateImpl implements TareasApiDelegate {
    private final TareaRepository tareaRepository;
    private final TareaMapper tareaMapper;
    private final UsuarioRepository usuarioRepository;
    private final EstadoTareaRepository estadoTareaRepository;

    public TareasApiDelegateImpl(TareaRepository tareaRepository, TareaMapper tareaMapper,
                                UsuarioRepository usuarioRepository, EstadoTareaRepository estadoTareaRepository) {
        this.tareaRepository = tareaRepository;
        this.tareaMapper = tareaMapper;
        this.usuarioRepository = usuarioRepository;
        this.estadoTareaRepository = estadoTareaRepository;
    }

    @Override
    public ResponseEntity<List<TareaDto>> tareasGet() {
        List<TareaDto> tareas = tareaRepository.findByActivoTrue().stream()
                .map(tareaMapper::toDto)
                .collect(Collectors.toList());
            return ResponseEntity.ok(tareas);
    }
    
    @Override
    public ResponseEntity<TareaDto> tareasIdGet(Long id) {
        if(esNuloCeroONegativo(id)) throw new CamposRequeridosVaciosException();

        Tarea tarea = tareaRepository.findByIdAndActivoTrue(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No se ha encontrado la tarea."));
        
        return ResponseEntity.ok(tareaMapper.toDto(tarea));
    }

    @Override
    public ResponseEntity<TareaDto> tareasPost(TareaNuevaDto tareaNuevaDto) {
        if(tieneCamposRequeridosInvalidos(tareaNuevaDto)) {
            throw new CamposRequeridosVaciosException();
        }

        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(tareaNuevaDto.getUsuarioId())
                            .orElseThrow(() -> new RecursoNoEncontradoException("No se ha encontrado el usuario a asignar."));
        EstadoTarea estadoTarea = estadoTareaRepository.findByIdAndActivoTrue(tareaNuevaDto.getEstadoTareaId())
                            .orElseThrow(() -> new RecursoNoEncontradoException("No se ha encontrado el estado tarea a asignar."));

        Tarea tareaExistente = tareaMapper.toEntity(new Tarea(), tareaNuevaDto, usuario, estadoTarea);
        Tarea tareaGuardada = tareaRepository.save(tareaExistente);
        return ResponseEntity.status(201).body(tareaMapper.toDto(tareaGuardada));
    }

    @Override
    public ResponseEntity<TareaDto> tareasIdPut(Long id, TareaNuevaDto tareaNuevaDto) {
        if(esNuloCeroONegativo(id) || tieneCamposRequeridosInvalidos(tareaNuevaDto)) {
            throw new CamposRequeridosVaciosException();
        }

        Tarea tarea = tareaRepository.findByIdAndActivoTrue(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No se ha encontrado la tarea a modificar."));

        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(tareaNuevaDto.getUsuarioId())
                            .orElseThrow(() -> new RecursoNoEncontradoException("No se ha encontrado el usuario a asignar."));
        EstadoTarea estadoTarea = estadoTareaRepository.findByIdAndActivoTrue(tareaNuevaDto.getEstadoTareaId())
                            .orElseThrow(() -> new RecursoNoEncontradoException("No se ha encontrado el estado tarea a asignar."));

        Tarea tareaActualizada = tareaMapper.toEntity(tarea, tareaNuevaDto, usuario, estadoTarea);

        Tarea tareaGuardada = tareaRepository.save(tareaActualizada);
        return ResponseEntity.ok(tareaMapper.toDto(tareaGuardada));
    }

    @Override
    public ResponseEntity<Void> tareasIdDelete(Long id) {
        if(esNuloCeroONegativo(id)) throw new CamposRequeridosVaciosException();

        Tarea tarea = tareaRepository.findByIdAndActivoTrue(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No se ha encontrado la tarea a eliminar."));

        tarea.setActivo(false);
        tareaRepository.save(tarea);

        return ResponseEntity.noContent().build();
    }

    private boolean tieneCamposRequeridosInvalidos(TareaNuevaDto tareaNuevaDto) {
        return esNuloOVacio(tareaNuevaDto.getNombre()) || esNuloOVacio(tareaNuevaDto.getDescripcion())
                || esNuloCeroONegativo(tareaNuevaDto.getUsuarioId()) || esNuloCeroONegativo(tareaNuevaDto.getEstadoTareaId());
    }

    private boolean esNuloOVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private boolean esNuloCeroONegativo(Long valor) {
        return valor == null || valor <= 0;
    }

}
