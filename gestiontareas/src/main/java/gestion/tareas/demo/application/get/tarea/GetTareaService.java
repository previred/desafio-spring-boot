package gestion.tareas.demo.application.get.tarea;

import gestion.tareas.demo.application.get.tarea.mapper.GetTareaMapper;
import gestion.tareas.demo.db.TareaDto;
import gestion.tareas.demo.infrastructure.db.entity.Tareas;
import gestion.tareas.demo.infrastructure.db.repository.TareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetTareaService {

    private final TareaRepository tareaRepository;
    private final GetTareaMapper getTareaMapper;

    public ResponseEntity<List<TareaDto>> tareasGet(Integer usuarioId) {
        List<TareaDto> tareaDtoList = tareaRepository.findByUsuarioId(usuarioId.longValue())
                .stream()
                .map(getTareaMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tareaDtoList);
    }

    public ResponseEntity<TareaDto> tareasIdGet(Long tareaId) {
        Optional<Tareas> tareaOpt = tareaRepository.findById(tareaId);
        return tareaOpt.map(getTareaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
