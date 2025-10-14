package gestion.tareas.demo.application.get.estadotarea;

import gestion.tareas.demo.infrastructure.db.entity.EstadoTarea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetEstadoTareaGateway {
    private final GetEstadoTareaService getEstadoTareaService;

    public EstadoTarea getEstadoTareaById(Long estadoId){
        return getEstadoTareaService.getEstadoTareaById(estadoId);
    }
}
