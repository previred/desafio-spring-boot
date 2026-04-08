package io.github.srdejo.task_manager.service.impl;

import io.github.srdejo.task_manager.entity.EstadoTarea;
import io.github.srdejo.task_manager.entity.Tarea;
import io.github.srdejo.task_manager.entity.Usuario;
import io.github.srdejo.task_manager.controller.exception.ResourceNotFoundException;
import io.github.srdejo.task_manager.mapper.TaskMapper;
import io.github.srdejo.task_manager.model.TaskRequest;
import io.github.srdejo.task_manager.model.TaskResponse;
import io.github.srdejo.task_manager.repository.EstadoTareaRepository;
import io.github.srdejo.task_manager.repository.TareaRepository;
import io.github.srdejo.task_manager.service.TaskService;
import io.github.srdejo.task_manager.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl  implements TaskService {

    private final TareaRepository tareaRepository;
    private final EstadoTareaRepository estadoRepository;
    private final TaskMapper taskMapper;
    private final UsuarioService usuarioService;

    @Override
    public TaskResponse create(TaskRequest request) {

        Usuario usuario = usuarioService.getCurrentUser();

        EstadoTarea estado = estadoRepository.findById(request.getEstadoId().getValue())
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado"));

        Tarea tarea = taskMapper.toEntity(request, usuario, estado);

        Tarea saved = tareaRepository.save(tarea);

        return taskMapper.toResponse(saved);
    }

    @Override
    public List<TaskResponse> findAll() {
        return tareaRepository.findAll()
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    @Override
    public TaskResponse findById(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));

        return taskMapper.toResponse(tarea);
    }

    @Override
    public TaskResponse update(Long id, TaskRequest request) {

        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));

        EstadoTarea estado = estadoRepository.findById(request.getEstadoId().getValue())
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado"));

        tarea.setTitulo(request.getTitulo());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setEstado(estado);

        Tarea updated = tareaRepository.save(tarea);

        return taskMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tarea no encontrada");
        }

        tareaRepository.deleteById(id);
    }
}