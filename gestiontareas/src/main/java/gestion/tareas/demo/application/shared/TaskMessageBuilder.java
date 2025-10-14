package gestion.tareas.demo.application.shared;

import gestion.tareas.demo.application.get.auth.GetAuthGateway;
import gestion.tareas.demo.db.StateAppDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMessageBuilder {

    private final GetAuthGateway getAuthGateway;
    private final HttpServletRequest request;

    public StateAppDto buildMessage(long id, boolean success) {
        StateAppDto dto = new StateAppDto();
        String method = request.getMethod();

        if (success) {
            dto.setMessage(getSuccessMessage(method, id));
        } else {
            dto.setMessage("No existe Tarea " + id);
        }

        dto.setLoggedUser(getAuthGateway.obtenerUsuarioAutenticado().getUsername());
        return dto;
    }

    private String getSuccessMessage(String method, long id) {
        return switch (method.toUpperCase()) {
            case "POST" -> "Se registró exitosamente la tarea " + id;
            case "PUT"  -> "Se actualizó exitosamente la tarea " + id;
            case "DELETE" -> "Se eliminó exitosamente la tarea " + id;
            default -> "Operación exitosa sobre la tarea " + id;
        };
    }
}