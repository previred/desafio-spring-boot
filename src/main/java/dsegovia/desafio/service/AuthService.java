package dsegovia.desafio.service;

import dsegovia.desafio.entity.Usuario;

/**
 * Interfaz para servicios de autenticación
 */
public interface AuthService {

    /**
     * Busca un usuario por su nombre de usuario
     *
     * @param username Nombre de usuario a buscar
     * @return Usuario encontrado o null
     */
    Usuario findByUsername(String username);
}