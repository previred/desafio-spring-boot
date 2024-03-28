package cl.rhoffmann.previred.desafiospringboot.api.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cl.rhoffmann.previred.desafiospringboot.api.filter.JwtTokenFilter;
import cl.rhoffmann.previred.desafiospringboot.api.repository.UsuarioRepository;

/**
 * Configuración de seguridad para la aplicación, integrando JWT para la autenticación.
 * <p>
 * Esta configuración define cómo se autentican los usuarios, cómo se verifica la autorización
 * para distintas rutas de la API y cómo se manejan las sesiones de usuario.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Configuration
public class SeguridadConfig {

	private final JwtTokenFilter jwtTokenFilter;
	private final UsuarioRepository usuarioRepository;

	public SeguridadConfig(JwtTokenFilter jwtTokenFilter, UsuarioRepository usuarioRepository) {
		this.jwtTokenFilter = jwtTokenFilter;
		this.usuarioRepository = usuarioRepository;
	}

	/**
     * Define el servicio para cargar los detalles de usuario por nombre de usuario (correo).
     *
     * @return Un UserDetailsService que carga los detalles del usuario.
     */
	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return usuarioRepository.findByCorreo(username)
						.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
			}
		};
	}

	/**
     * Configura el codificador de contraseña a utilizar en la aplicación.
     *
     * @return Un PasswordEncoder que utiliza BCrypt.
     */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
     * Configura y expone el AuthenticationManager para ser utilizado en la autenticación.
     *
     * @param authConfig Configuración de autenticación de Spring Security.
     * @return Un AuthenticationManager construido a partir de la configuración.
     * @throws Exception en caso de errores durante la configuración.
     */
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	/**
     * Configura la cadena de filtros de seguridad para las solicitudes HTTP.
     * <p>
     * Define políticas como la deshabilitación de CSRF para API stateless, la política de creación de sesiones,
     * restricciones de acceso a rutas específicas y el manejo de excepciones de autenticación.
     * </p>
     *
     * @param http Configuración de seguridad HTTP.
     * @return Una SecurityFilterChain configurada.
     * @throws Exception en caso de errores durante la configuración.
     */
	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests(
				requests -> requests.antMatchers("/auth/login").permitAll().anyRequest().authenticated());

		http.exceptionHandling(handling -> handling.authenticationEntryPoint(
				(request, response, ex) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage())));

		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}