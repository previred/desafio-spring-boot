package gestion.tareas.demo.application.get.usuario;

import gestion.tareas.demo.infrastructure.db.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetUsuarioGateway {
    private final GetUsuarioService getUsuarioService;

    public Optional<Usuario> findByUsername(String username) {
        return getUsuarioService.findByUsername(username);
    }
}
