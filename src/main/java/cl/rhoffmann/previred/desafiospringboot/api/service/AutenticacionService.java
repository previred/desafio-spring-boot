package cl.rhoffmann.previred.desafiospringboot.api.service;

import cl.rhoffmann.previred.desafiospringboot.api.dto.PeticionAutenticacionDTO;

/**
 * Servicio de autenticación para usuarios.
 * <p>
 * Define las operaciones disponibles para autenticar usuarios en el sistema,
 * permitiendo verificar sus credenciales y generar tokens de acceso si la autenticación es exitosa.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
public interface AutenticacionService {

	/**
     * Autentica un usuario basado en los datos proporcionados en la petición de autenticación.
     * <p>
     * Este método verifica las credenciales del usuario (como correo electrónico y contraseña)
     * y, si son válidas, genera y retorna un token de acceso que el usuario puede utilizar para
     * acceder a recursos protegidos en el sistema. En caso de fallar la autenticación, se puede
     * lanzar una excepción o retornar null/valor vacío dependiendo de la implementación.
     * </p>
     *
     * @param peticion La petición de autenticación que contiene las credenciales del usuario.
     * @return Un {@link String} que representa el token de acceso del usuario autenticado,
     *         o null si la autenticación no es exitosa.
     */
	public String autenticar(PeticionAutenticacionDTO peticion);
}