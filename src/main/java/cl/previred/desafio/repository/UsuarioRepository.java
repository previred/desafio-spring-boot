package cl.previred.desafio.repository;

import cl.previred.desafio.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    // Consulta derivada: Spring genera el SQL automáticamente basándose en el nombre del método
    Optional<UsuarioEntity> findByUsername(String username);
}
