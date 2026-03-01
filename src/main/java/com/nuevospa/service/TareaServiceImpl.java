package com.nuevospa.service;


import com.nuevospa.domain.EstadoTarea;
import com.nuevospa.domain.Tarea;
import com.nuevospa.domain.Usuario;
import com.nuevospa.dto.EstadoResponseDTO;
import com.nuevospa.dto.TareaRequestDTO;
import com.nuevospa.dto.TareaResponseDTO;
import com.nuevospa.dto.UsuarioResponseDTO;
import com.nuevospa.exception.BusinessException;
import com.nuevospa.repository.EstadoTareaRepository;
import com.nuevospa.repository.TareaRepository;
import com.nuevospa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TareaServiceImpl implements TareaService{

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstadoTareaRepository estadoRepository;

    @Override
    public List<TareaResponseDTO> findAll() {
        return tareaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Tarea findById(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tarea no encontrada", HttpStatus.NOT_FOUND));
    }

    @Override
    public Tarea create(TareaRequestDTO requestDTO) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        EstadoTarea estado = estadoRepository.findById(requestDTO.estadoId())
                .orElseThrow(() -> new BusinessException("Estado no encontrado", HttpStatus.NOT_FOUND));

        Tarea tarea = new Tarea();
        tarea.setTitulo(requestDTO.titulo());
        tarea.setDescripcion(requestDTO.descripcion());
        tarea.setEstado(estado);
        tarea.setUsuario(usuario);

        return tareaRepository.save(tarea);
    }

    @Override
    public Tarea update(Long id, TareaRequestDTO requestDTO) {

        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow();

        EstadoTarea estado = estadoRepository.findById(requestDTO.estadoId())
                .orElseThrow();

         tarea.setTitulo(requestDTO.titulo());
         tarea.setDescripcion(requestDTO.descripcion());
         tarea.setEstado(estado);
         tarea.setUsuario(tarea.getUsuario());


        return tareaRepository.save(tarea);
    }

    @Override
    public void delete(Long id) {
        Tarea existing = findById(id);
        tareaRepository.delete(existing);
    }

    private TareaResponseDTO mapToResponse(Tarea tarea) {
        return new TareaResponseDTO(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getEstado().getNombre(),
                tarea.getUsuario().getUsername()

        );
    }

}
