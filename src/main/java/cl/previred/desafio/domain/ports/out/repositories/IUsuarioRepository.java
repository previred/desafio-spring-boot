package cl.previred.desafio.domain.ports.out.repositories;

import cl.previred.desafio.domain.models.Usuario;

public interface IUsuarioRepository {
    Usuario findById(Long id);
    Usuario findByUsername(String username);
}
