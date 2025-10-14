package gestion.tareas.demo.application.get.tarea.mapper;

import gestion.tareas.demo.application.get.usuario.GetUsuarioGateway;
import gestion.tareas.demo.db.TareaDto;
import gestion.tareas.demo.infrastructure.db.entity.Tareas;
import gestion.tareas.demo.infrastructure.security.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetTareaMapper {
    private final GetUsuarioGateway getUsuarioGateway;

    public TareaDto toDto(Tareas tarea) {
        TareaDto dto = new TareaDto();
        dto.setId(tarea.getId().longValue());
        dto.setDescripcion(tarea.getDescripcion());
        dto.setDateCreation(tarea.getFechaCreacion().atOffset(java.time.ZoneOffset.UTC));
        dto.setExpirationDate(tarea.getFechaVencimiento().atOffset(java.time.ZoneOffset.UTC));
        dto.setState(tarea.getEstado().getNombre());

        String username = AuthUtil.obtenerUsuarioAutenticado();
        dto.setUserName(username);
        getUsuarioGateway.findByUsername(username)
                .ifPresent(u -> dto.setUserRole(u.getRole()));

        return dto;
    }
}
