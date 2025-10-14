package gestion.tareas.demo.application.get.auth;

import gestion.tareas.demo.application.get.usuario.GetUsuarioGateway;
import gestion.tareas.demo.infrastructure.db.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAuthService {
    private final GetUsuarioGateway getUsuarioGateway;

    public Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Usuario no autenticado");
        }

        String username = authentication.getName();
        return getUsuarioGateway.findByUsername(username)
                .orElseThrow(() -> new AccessDeniedException("Usuario no encontrado"));
    }
}
