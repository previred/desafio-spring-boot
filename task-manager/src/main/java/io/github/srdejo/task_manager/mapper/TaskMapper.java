package io.github.srdejo.task_manager.mapper;

import io.github.srdejo.task_manager.entity.EstadoTarea;
import io.github.srdejo.task_manager.entity.Tarea;
import io.github.srdejo.task_manager.entity.Usuario;
import io.github.srdejo.task_manager.model.TaskRequest;
import io.github.srdejo.task_manager.model.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Tarea toEntity(TaskRequest request, Usuario usuario, EstadoTarea estado) {
        return Tarea.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .usuario(usuario)
                .estado(estado)
                .build();
    }

    public TaskResponse toResponse(Tarea tarea) {
        TaskResponse response = new TaskResponse();
        response.setId(tarea.getId());
        response.setTitulo(tarea.getTitulo());
        response.setDescripcion(tarea.getDescripcion());
        response.setEstado(tarea.getEstado().getNombre());
        response.setUsuario(tarea.getUsuario().getUsername());
        return response;
    }
}
