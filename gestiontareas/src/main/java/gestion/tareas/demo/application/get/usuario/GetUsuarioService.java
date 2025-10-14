package gestion.tareas.demo.application.get.usuario;

import gestion.tareas.demo.infrastructure.db.entity.Usuario;
import gestion.tareas.demo.infrastructure.db.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
