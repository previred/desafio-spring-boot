package cl.rhoffmann.previred.desafiospringboot.api.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.rhoffmann.previred.desafiospringboot.api.dto.PeticionAutenticacionDTO;
import cl.rhoffmann.previred.desafiospringboot.api.dto.RespuestaAutenticacionDTO;
import cl.rhoffmann.previred.desafiospringboot.api.serviceimpl.AutenticacionServiceImpl;

/**
 * Controlador REST para manejar la autenticación de usuarios.
 * <p>Este controlador proporciona endpoints para la autenticación de usuarios, permitiendo el inicio de sesión.</p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@RestController
@RequestMapping("/auth")
public class AutenticacionController {

	private final AutenticacionServiceImpl autenticacionServiceImpl;

	/**
     * Constructor para inyectar la implementación del servicio de autenticación.
     *
     * @param autenticacionServiceImpl Servicio que maneja la lógica de autenticación.
     */
	public AutenticacionController(AutenticacionServiceImpl autenticacionServiceImpl) {
		this.autenticacionServiceImpl = autenticacionServiceImpl;
	}
	
	/**
     * Endpoint para iniciar sesión y obtener un token de acceso.
     * <p>Este método maneja las peticiones de inicio de sesión, autenticando al usuario y generando un token de acceso en caso de éxito.</p>
     * <p>
     * {@code POST /auth/login} : Autentica al usuario y genera un token de acceso.
     * </p>
     *
     * @param peticion Objeto que contiene los datos de la petición de autenticación, como el correo y la contraseña.
     * @return Una {@link ResponseEntity} que contiene un {@link RespuestaAutenticacionDTO} con los detalles de la autenticación,
     * incluido el token de acceso, o un estado HTTP UNAUTHORIZED en caso de fallo en la autenticación.
     */
	@PostMapping("/login")
	public ResponseEntity<RespuestaAutenticacionDTO> login(@RequestBody @Valid PeticionAutenticacionDTO peticion) {
		try {

			String tokenAcceso = autenticacionServiceImpl.autenticar(peticion);
			RespuestaAutenticacionDTO response = new RespuestaAutenticacionDTO(peticion.getCorreo(), tokenAcceso);
			return ResponseEntity.ok().body(response);

		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

}
