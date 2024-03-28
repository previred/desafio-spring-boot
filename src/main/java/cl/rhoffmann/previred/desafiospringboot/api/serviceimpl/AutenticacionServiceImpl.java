package cl.rhoffmann.previred.desafiospringboot.api.serviceimpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import cl.rhoffmann.previred.desafiospringboot.api.dto.PeticionAutenticacionDTO;
import cl.rhoffmann.previred.desafiospringboot.api.entity.Usuario;
import cl.rhoffmann.previred.desafiospringboot.api.service.AutenticacionService;
import cl.rhoffmann.previred.desafiospringboot.api.util.JwtTokenUtil;

/**
 * Implementación del servicio de autenticación que utiliza Spring Security y JWT para autenticar usuarios.
 * <p>
 * Proporciona la lógica necesaria para autenticar usuarios basándose en sus credenciales y generar un token JWT
 * en caso de autenticación exitosa. Este servicio se integra con el {@link AuthenticationManager} de Spring Security
 * para realizar la autenticación.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Service
public class AutenticacionServiceImpl implements AutenticacionService {

	private final AuthenticationManager authManager;
	private final JwtTokenUtil jwtUtil;

	/**
     * Constructor que inyecta las dependencias necesarias para el proceso de autenticación.
     *
     * @param authManager Gestor de autenticación de Spring Security.
     * @param jwtUtil     Utilidad para la generación de tokens JWT.
     */
	public AutenticacionServiceImpl(AuthenticationManager authManager, JwtTokenUtil jwtUtil) {
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
	}

	/**
     * Autentica a un usuario utilizando las credenciales proporcionadas y genera un token de acceso JWT.
     * <p>
     * Utiliza el {@link AuthenticationManager} para validar las credenciales del usuario. Si la autenticación es exitosa,
     * genera y retorna un token JWT utilizando la información del usuario autenticado. Este token puede ser utilizado
     * por el usuario para acceder a recursos protegidos en la aplicación.
     * </p>
     *
     * @param peticion DTO que contiene el correo y la contraseña para la autenticación.
     * @return Un token JWT como {@link String} que representa el acceso del usuario autenticado.
     * @throws AuthenticationException si la autenticación falla debido a credenciales inválidas.
     */
	public String autenticar(PeticionAutenticacionDTO peticion) {
		Authentication autenticacion = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(peticion.getCorreo(), peticion.getContrasena()));

		Usuario usuario = (Usuario) autenticacion.getPrincipal();
		return jwtUtil.generarTokenAcceso(usuario);

	}
}