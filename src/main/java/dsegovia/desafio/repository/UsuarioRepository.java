package dsegovia.desafio.repository;

import dsegovia.desafio.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para operaciones con usuarios
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de usuario
     *
     * @param username Nombre de usuario a buscar
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Verifica si existe un usuario con el nombre de usuario dado
     *
     * @param username Nombre de usuario a verificar
     * @return true si existe un usuario con ese nombre
     */
    boolean existsByUsername(String username);
}
