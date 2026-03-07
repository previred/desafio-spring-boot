package com.previred.desafiostareas.service;


import com.previred.desafiostareas.controller.dto.TareaRequest;
import com.previred.desafiostareas.controller.dto.TareaResponse;
import com.previred.desafiostareas.repository.entity.EstadoTarea;
import com.previred.desafiostareas.repository.entity.TareaEntity;
import com.previred.desafiostareas.repository.entity.UsuarioEntity;
import com.previred.desafiostareas.repository.exception.ResourceNotFoundException;
import com.previred.desafiostareas.repository.EstadoTareaRepository;
import com.previred.desafiostareas.repository.TareaRepository;
import com.previred.desafiostareas.repository.UsuarioRepository;
import com.previred.desafiostareas.mapper.MapeadorTarea;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de gestión de tareas
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;
    private final EstadoTareaRepository estadoTareaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MapeadorTarea mapeadorTarea;
    private final JwtService jwtService;

    @Override
    public List<TareaResponse> obtenerTodasLasTareas() {
        log.info("Obteniendo todas las tareas");
        return tareaRepository.findAll().stream()
                .map(mapeadorTarea::toRespuesta)
                .toList();
    }

    public TareaResponse obtenerTareaPorId(Long id) {
        log.info("Obteniendo tareaEntity con ID: {}", id);
        TareaEntity tareaEntity = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No encontrada : " + id));
        return mapeadorTarea.toRespuesta(tareaEntity);
    }

    @Transactional
    @Override
    public TareaResponse crearTarea(String bearerToken ,TareaRequest request) {
        log.info("Creando nueva tareaEntity: {}", request.getTitulo());
        
        EstadoTarea estado = estadoTareaRepository.findById(request.getEstadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado id: " + request.getEstadoId()));
        

        String token = bearerToken.replaceFirst("(?i)^Bearer ", "").trim();
        String email = jwtService.extractUsername(token);
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email no encontrado : " + email));

        TareaEntity tareaEntity = mapeadorTarea.toEntidad(request, estado, usuarioEntity);
        TareaEntity tareaEntityGuardada = tareaRepository.save(tareaEntity);
        
        log.info("TareaEntity creada exitosamente id : {}", tareaEntityGuardada.getId());
        return mapeadorTarea.toRespuesta(tareaEntityGuardada);
    }

    @Transactional
    @Override
    public TareaResponse actualizarTarea(Long id, String bearerToken, TareaRequest request) {
        log.info("Actualizando tareaEntity id : {}", id);
        
        TareaEntity tareaEntity = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada id: " + id));

        EstadoTarea estado = null;
        if (request.getEstadoId() != null) {
            estado = estadoTareaRepository.findById(request.getEstadoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado id: " + request.getEstadoId()));
        }

        String token = bearerToken.replaceFirst("(?i)^Bearer ", "").trim();
        String email = jwtService.extractUsername(token);
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email no encontrado : " + email));

        mapeadorTarea.actualizarEntidad(tareaEntity, request, estado, usuarioEntity);
        TareaEntity tareaEntityActualizada = tareaRepository.save(tareaEntity);
        
        log.info("TareaEntity actualizada : {}", id);
        return mapeadorTarea.toRespuesta(tareaEntityActualizada);
    }

    @Transactional
    @Override
    public void eliminarTarea(Long id) {
        log.info("Eliminando tarea con ID: {}", id);
        
        if (!tareaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id no encontrada : " + id);
        }
        
        tareaRepository.deleteById(id);
        log.info("eliminado : {}", id);
    }
}
