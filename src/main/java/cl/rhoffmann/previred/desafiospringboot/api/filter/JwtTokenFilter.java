package cl.rhoffmann.previred.desafiospringboot.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cl.rhoffmann.previred.desafiospringboot.api.entity.Usuario;
import cl.rhoffmann.previred.desafiospringboot.api.util.JwtTokenUtil;

/**
 * Filtro JWT para interceptar las solicitudes y validar los tokens de acceso.
 * <p>
 * Este filtro se ejecuta una vez por cada solicitud, verificando la presencia y validez de un token JWT
 * en la cabecera Authorization. Si el token es válido, establece el contexto de autenticación para la solicitud,
 * permitiendo el acceso a los recursos protegidos.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtUtil;

	/**
     * Constructor para inyectar la utilidad de manejo de tokens JWT.
     *
     * @param jwtUtil la utilidad para trabajar con tokens JWT.
     */
	public JwtTokenFilter(JwtTokenUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	/**
     * Método interno para filtrar cada solicitud.
     * <p>
     * Verifica si la solicitud contiene un token JWT válido. Si no es así, permite que la solicitud continúe sin modificar el contexto de autenticación.
     * Si un token válido está presente, establece el contexto de autenticación basado en la información del token.
     * </p>
     *
     * @param request     la solicitud HTTP entrante.
     * @param response    la respuesta HTTP a enviar.
     * @param filterChain la cadena de filtros a ejecutar.
     * @throws ServletException si ocurre un error de servlet.
     * @throws IOException      si ocurre un error de entrada/salida.
     */
    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (!tieneAutorizacionBearer(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = obtenerTokenAcceso(request);

		if (!jwtUtil.validarTokenAcceso(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		establecerContextoAutenticacion(token, request);
		filterChain.doFilter(request, response);
	}

    /**
     * Comprueba si la solicitud contiene una cabecera de autorización con un token Bearer.
     *
     * @param request la solicitud HTTP entrante.
     * @return true si la solicitud contiene un token Bearer válido; false en caso contrario.
     */
	private boolean tieneAutorizacionBearer(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		return !(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer"));
	}

	/**
     * Extrae el token de acceso JWT de la cabecera de autorización.
     *
     * @param request la solicitud HTTP entrante.
     * @return el token de acceso JWT como un String.
     */
	private String obtenerTokenAcceso(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		return header.split(" ")[1].trim();
	}

	/**
     * Establece el contexto de autenticación para la solicitud actual basándose en el token JWT.
     *
     * @param token   el token JWT desde el cual extraer los detalles del usuario.
     * @param request la solicitud HTTP entrante.
     */
	private void establecerContextoAutenticacion(String token, HttpServletRequest request) {
		UserDetails userDetails = obtenerUserDetails(token);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				null);

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
     * Obtiene los detalles del usuario a partir del token JWT.
     *
     * @param token el token JWT desde el cual extraer los detalles del usuario.
     * @return un objeto {@link UserDetails} con los detalles del usuario.
     */
	private UserDetails obtenerUserDetails(String token) {
		Usuario userDetails = new Usuario();
		String[] jwtSubject = jwtUtil.obtenerSubject(token).split(",");

		userDetails.setId(Long.parseLong(jwtSubject[0]));
		userDetails.setCorreo(jwtSubject[1]);

		return userDetails;
	}
}