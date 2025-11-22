package com.yoserp1.tareas.service.implementation;

import com.yoserp1.tareas.dto.TareaDTO;
import com.yoserp1.tareas.exception.AplicacionException;
import com.yoserp1.tareas.jpa.entity.EstadoTareaEntity;
import com.yoserp1.tareas.jpa.entity.TareaEntity;
import com.yoserp1.tareas.jpa.entity.UsuarioEntity;
import com.yoserp1.tareas.jpa.repository.EstadoTareaRepository;
import com.yoserp1.tareas.jpa.repository.TareaRepository;
import com.yoserp1.tareas.jpa.repository.UsuarioRepository;
import com.yoserp1.tareas.mapper.TareaMapper;
import com.yoserp1.tareas.service.ITareaService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TareaService implements ITareaService {
  @Autowired private TareaRepository tareaRepository;

  @Autowired private EstadoTareaRepository estadoTareaRepository;

  @Autowired private UsuarioRepository usuarioRepository;

  @Autowired private TareaMapper tareaMapper;

  @Override
  public TareaDTO crearTarea(TareaDTO tareaDTO) {
    log.info("Creando tarea: {}", tareaDTO);

    TareaEntity entity = tareaMapper.toEntity(tareaDTO);

    if (tareaDTO.estado() != null) {
      EstadoTareaEntity estado =
          estadoTareaRepository
              .findByNombre(tareaDTO.estado())
              .orElseThrow(() -> AplicacionException.notFound("Estado", tareaDTO.estado()));
      entity.setEstado(estado);
    }

    if (tareaDTO.usuarioId() != null) {
      UsuarioEntity usuario =
          usuarioRepository
              .findById(tareaDTO.usuarioId())
              .orElseThrow(
                  () -> AplicacionException.notFound("Usuario", tareaDTO.usuarioId().toString()));
      entity.setUsuario(usuario);
    }

    entity = tareaRepository.save(entity);
    log.info("Tarea guardada con ID {}", entity.getId());

    return tareaMapper.toDTO(entity);
  }

  @Override
  public List<TareaDTO> listarTareas() {
    try {
      log.info("Listando todas las tareas");

      return tareaRepository.findAll().stream().map(tareaMapper::toDTO).toList();

    } catch (Exception ex) {
      log.error("Error al listar tareas", ex);
      throw AplicacionException.operation("Error al listar tareas", ex);
    }
  }

  @Override
  public TareaDTO obtenerTarea(Long id) {
    try {
      log.info("Buscando tarea con ID {}", id);

      TareaEntity tarea =
          tareaRepository.findById(id).orElseThrow(() -> AplicacionException.notFound("Tarea", id));

      return tareaMapper.toDTO(tarea);

    } catch (AplicacionException ex) {
      throw ex;
    } catch (Exception ex) {
      log.error("Error al obtener tarea con ID {}", id, ex);
      throw AplicacionException.operation("Error al obtener tarea", ex);
    }
  }

  @Override
  public TareaDTO actualizarTarea(Long id, TareaDTO tareaDTO) {
    TareaEntity entity =
        tareaRepository
            .findById(id)
            .orElseThrow(() -> AplicacionException.notFound("Tarea", id.toString()));

    entity.setTitulo(tareaDTO.titulo());
    entity.setDescripcion(tareaDTO.descripcion());

    if (tareaDTO.estado() != null) {
      EstadoTareaEntity estado =
          estadoTareaRepository
              .findByNombre(tareaDTO.estado())
              .orElseThrow(() -> AplicacionException.notFound("Estado", tareaDTO.estado()));
      entity.setEstado(estado);
    }

    entity = tareaRepository.save(entity);
    return tareaMapper.toDTO(entity);
  }

  @Override
  public void eliminarTarea(Long id) {
    try {
      log.info("Eliminando tarea con ID {}", id);

      if (!tareaRepository.existsById(id)) {
        throw AplicacionException.notFound("Tarea", id);
      }

      tareaRepository.deleteById(id);
      log.info("Tarea ID {} eliminada correctamente", id);

    } catch (AplicacionException ex) {
      throw ex;
    } catch (Exception ex) {
      log.error("Error al eliminar tarea", ex);
      throw AplicacionException.operation("Error al eliminar tarea", ex);
    }
  }
}
