package cl.rhoffmann.previred.desafiospringboot.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.rhoffmann.previred.desafiospringboot.api.entity.EstadoTarea;
import cl.rhoffmann.previred.desafiospringboot.api.entity.Usuario;
import cl.rhoffmann.previred.desafiospringboot.api.repository.EstadoTareaRepository;
import cl.rhoffmann.previred.desafiospringboot.api.repository.UsuarioRepository;

/**
 * Configuración para inicializar la base de datos con datos de prueba en el perfil 'dev'.
 * <p>
 * Esta clase se activa solo cuando la aplicación se ejecuta en el perfil de desarrollo ('dev').
 * Se encarga de poblar la base de datos con usuarios iniciales y estados de tareas, facilitando el proceso de
 * desarrollo y pruebas al proporcionar un conjunto de datos predeterminado.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Configuration
@Profile("dev")
public class InicializadorDatosConfig {

	private final UsuarioRepository usuarioRepository;
	private final EstadoTareaRepository estadoTareaRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${usuario1.correo}")
	private String usuario1Correo;
	@Value("${usuario1.contrasena}")
	private String usuario1Contrasena;
	@Value("${usuario1.nombre}")
	private String usuario1Nombre;

	@Value("${usuario2.correo}")
	private String usuario2Correo;
	@Value("${usuario2.contrasena}")
	private String usuario2Contrasena;
	@Value("${usuario2.nombre}")
	private String usuario2Nombre;

	@Value("${estadotarea1.nombre}")
	private String estadoTarea1Nombre;
	@Value("${estadotarea2.nombre}")
	private String estadoTarea2Nombre;
	@Value("${estadotarea3.nombre}")
	private String estadoTarea3Nombre;

	/**
     * Constructor para inyectar las dependencias necesarias para la inicialización de datos.
     * <p>
     * A través de este constructor se inyectan las dependencias hacia los repositorios y el componente de codificación
     * de contraseñas, necesarios para crear los usuarios y estados de tarea en la base de datos. 
     * </p>
     *
     * @param usuarioRepository Repositorio utilizado para operaciones de persistencia de usuarios.
     * @param estadoTareaRepository Repositorio utilizado para operaciones de persistencia de los estados de las tareas.
     * @param passwordEncoder Componente para la codificación de contraseñas, asegurando que las contraseñas de los usuarios
     *                        se almacenen de manera segura.
     */
	public InicializadorDatosConfig(UsuarioRepository usuarioRepository, EstadoTareaRepository estadoTareaRepository,
			PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.estadoTareaRepository = estadoTareaRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
     * Inicializa la base de datos con datos de prueba al arranque de la aplicación.
     * <p>
     * Utiliza CommandLineRunner para ejecutar la lógica de inicialización después de que el contexto de la aplicación
     * haya sido cargado. Crea usuarios y estados de tareas basándose en la configuración proporcionada en el
     * archivo de propiedades de la aplicación.
     * </p>
     *
     * @return Un CommandLineRunner que ejecuta la lógica de inicialización.
     */
	@Bean
	CommandLineRunner iniciarBaseDatos() {
		return args -> {
			usuarioRepository
					.save(new Usuario(usuario1Correo, passwordEncoder.encode(usuario1Contrasena), usuario1Nombre));
			usuarioRepository
					.save(new Usuario(usuario2Correo, passwordEncoder.encode(usuario2Contrasena), usuario2Nombre));

			estadoTareaRepository.save(new EstadoTarea(estadoTarea1Nombre));
			estadoTareaRepository.save(new EstadoTarea(estadoTarea2Nombre));
			estadoTareaRepository.save(new EstadoTarea(estadoTarea3Nombre));
		};
	}
}
