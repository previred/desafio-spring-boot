package cl.previred.desafio.infrastructure.adapters.out.repositories;

import cl.previred.desafio.domain.models.Usuario;
import cl.previred.desafio.domain.ports.out.repositories.IUsuarioRepository;
import cl.previred.desafio.infrastructure.db.jpa.entities.UsuarioEntity;
import cl.previred.desafio.infrastructure.db.jpa.repositories.UsuarioJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UsuarioRepository implements IUsuarioRepository {
    private final UsuarioJpaRepository jpaRepository;

    @Override
    public Usuario findById(Long id) {
        log.info("Buscando usuario por id {} desde JPA", id);
        return jpaRepository.findById(id)
                .map(UsuarioEntity::toDomain)
                .orElse(null);
    }

    @Override
    public Usuario findByUsername(String username) {
        log.info("Buscando usuario por username {} desde JPA", username);
        return jpaRepository.findByUsername(username)
                .map(UsuarioEntity::toDomain)
                .orElse(null);
    }
}
