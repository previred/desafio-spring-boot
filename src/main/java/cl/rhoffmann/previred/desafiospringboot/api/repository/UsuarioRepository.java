package cl.rhoffmann.previred.desafiospringboot.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.rhoffmann.previred.desafiospringboot.api.entity.Usuario;

/**
 * Repositorio para gestionar la entidad Usuario, añadiendo funcionalidades específicas a las operaciones CRUD estándar.
 * <p>
 * Incluye métodos para realizar búsquedas personalizadas, como la búsqueda de un usuario por su correo electrónico.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByCorreo(String correo);
}
