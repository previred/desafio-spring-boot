package gestion.tareas.demo.application.get.auth;

import gestion.tareas.demo.infrastructure.db.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAuthGateway {
    private final GetAuthService getAuthService;

    public Usuario obtenerUsuarioAutenticado() {
        return getAuthService.obtenerUsuarioAutenticado();
    }
}
