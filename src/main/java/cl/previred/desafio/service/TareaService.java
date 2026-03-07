package cl.previred.desafio.service;

import cl.previred.desafio.entity.EstadoTareaEntity;
import cl.previred.desafio.entity.TareaEntity;
import cl.previred.desafio.entity.UsuarioEntity;
import cl.previred.desafio.model.EstadoTarea;
import cl.previred.desafio.model.Tarea;
import cl.previred.desafio.model.TareaCreate;
import cl.previred.desafio.repository.EstadoTareaRepository;
import cl.previred.desafio.repository.TareaRepository;
import cl.previred.desafio.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TareaService {

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstadoTareaRepository estadoRepository;

    public List<Tarea> listarTareas() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return tareaRepository.findByUsuarioId(usuario.getId()).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void crearTarea(TareaCreate tareaCreate) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        EstadoTareaEntity estadoPendiente = estadoRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Estado inicial no encontrado"));

        TareaEntity nuevaTarea = TareaEntity.builder()
                .titulo(tareaCreate.getTitulo())
                .descripcion(tareaCreate.getDescripcion())
                .estado(estadoPendiente)
                .usuario(usuario)
                .build();

        tareaRepository.save(nuevaTarea);
    }

    public void actualizarTarea(Long id, TareaCreate tareaUpdate) {
        TareaEntity tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        validarPropiedad(tarea);

        tarea.setTitulo(tareaUpdate.getTitulo());
        tarea.setDescripcion(tareaUpdate.getDescripcion());
        tareaRepository.save(tarea);
    }

    public void eliminarTarea(Long id) {
        TareaEntity tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        validarPropiedad(tarea);
        tareaRepository.delete(tarea);
    }

    private void validarPropiedad(TareaEntity tarea) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!tarea.getUsuario().getUsername().equals(username)) {
            throw new RuntimeException("No tienes permisos para modificar esta tarea");
        }
    }

    private Tarea mapToDto(TareaEntity entity) {
        return new Tarea(
                entity.getId(),
                entity.getTitulo(),
                entity.getDescripcion(),
                EstadoTarea.fromValue(entity.getEstado().getNombre())
        );
    }

}