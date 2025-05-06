package dsegovia.desafio.service.impl;

import dsegovia.desafio.entity.EstadoTarea;
import dsegovia.desafio.entity.Rol;
import dsegovia.desafio.entity.Tarea;
import dsegovia.desafio.entity.Usuario;
import dsegovia.desafio.exception.AccesoDenegadoException;
import dsegovia.desafio.exception.NegocioException;
import dsegovia.desafio.exception.RecursoNoEncontradoException;
import dsegovia.desafio.repository.EstadoTareaRepository;
import dsegovia.desafio.repository.TareaRepository;
import dsegovia.desafio.repository.UsuarioRepository;
import dsegovia.desafio.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstadoTareaRepository estadoRepository;

    @Autowired
    public TareaServiceImpl(TareaRepository tareaRepository,
                            UsuarioRepository usuarioRepository,
                            EstadoTareaRepository estadoRepository) {
        this.tareaRepository = tareaRepository;
        this.usuarioRepository = usuarioRepository;
        this.estadoRepository = estadoRepository;
    }

    @Override
    @Transactional
    public Tarea crearTarea(Tarea tarea) {
        Usuario usuario = obtenerUsuarioActual();
        tarea.setOwner(usuario);

        Long estadoId = tarea.getEstado() != null ? tarea.getEstado().getId() : null;
        if (estadoId == null) {
            throw new NegocioException("El estado de la tarea es obligatorio");
        }

        EstadoTarea estado = estadoRepository.findById(estadoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estado de tarea", estadoId));

        tarea.setEstado(estado);

        return tareaRepository.save(tarea);
    }

    @Override
    @Transactional
    public Tarea actualizarTarea(Long id, Tarea tareaActualizada) {
        Tarea tareaExistente = tareaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tarea", id));

        Usuario usuarioActual = obtenerUsuarioActual();

        if (!tienePermiso(tareaExistente, usuarioActual)) {
            throw new AccesoDenegadoException("No tienes permiso para modificar esta tarea");
        }

        tareaExistente.setTitulo(tareaActualizada.getTitulo());
        tareaExistente.setDescripcion(tareaActualizada.getDescripcion());
        tareaExistente.setFechaVencimiento(tareaActualizada.getFechaVencimiento());
        tareaExistente.setPrioridad(tareaActualizada.getPrioridad());

        if (tareaActualizada.getEstado() != null && tareaActualizada.getEstado().getId() != null) {
            EstadoTarea nuevoEstado = estadoRepository.findById(tareaActualizada.getEstado().getId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Estado de tarea",
                            tareaActualizada.getEstado().getId()));

            EstadoTarea estadoAnterior = tareaExistente.getEstado();
            tareaExistente.setEstado(nuevoEstado);

        }

        if (tareaActualizada.getEtiquetas() != null) {
            tareaExistente.setEtiquetas(tareaActualizada.getEtiquetas());
        }

        return tareaRepository.save(tareaExistente);
    }

    @Override
    @Transactional
    public void eliminarTarea(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tarea", id));

        Usuario usuarioActual = obtenerUsuarioActual();

        if (!tienePermiso(tarea, usuarioActual)) {
            throw new AccesoDenegadoException("No tienes permiso para eliminar esta tarea");
        }


        tareaRepository.delete(tarea);
    }

    @Override
    public Optional<Tarea> obtenerTareaById(Long id) {
        return tareaRepository.findById(id);
    }

    @Override
    public boolean tienePermiso(Tarea tarea, Usuario usuario) {
        if (tarea.getOwner().getId().equals(usuario.getId())) {
            return true;
        }

        return usuario.getRoles().stream()
                .map(Rol::getNombre)
                .anyMatch(rol -> rol.equals("ROLE_ADMIN"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tarea> obtenerTareasFiltradas(Long estadoId, Tarea.Prioridad prioridad, String etiqueta) {
        Usuario usuarioActual = obtenerUsuarioActual();

        List<Tarea> tareas;

        boolean isAdmin = usuarioActual.getRoles().stream()
                .map(Rol::getNombre)
                .anyMatch(rol -> rol.equals("ROLE_ADMIN"));

        if (isAdmin) {
            tareas = tareaRepository.findAll();
        } else {
            tareas = tareaRepository.findByOwnerId(usuarioActual.getId());
        }

        return tareas.stream()
                .filter(tarea -> estadoId == null || tarea.getEstado().getId().equals(estadoId))
                .filter(tarea -> prioridad == null || tarea.getPrioridad() == prioridad)
                .filter(tarea -> etiqueta == null ||
                        (tarea.getEtiquetas() != null &&
                                tarea.getEtiquetasSet().contains(etiqueta)))
                .collect(Collectors.toList());
    }

    @Override
    public Usuario obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return usuarioRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado"));
    }
}