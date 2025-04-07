package cl.previred.desafio.infrastructure.db.jpa.repositories;

import cl.previred.desafio.infrastructure.db.jpa.entities.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioJpaRepository extends CrudRepository<UsuarioEntity,Long> {
    Optional<UsuarioEntity>findByUsername(String username);
}
