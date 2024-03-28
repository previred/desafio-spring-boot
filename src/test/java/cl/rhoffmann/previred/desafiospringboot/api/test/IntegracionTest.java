package cl.rhoffmann.previred.desafiospringboot.api.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import cl.rhoffmann.previred.desafiospringboot.api.dto.PeticionAutenticacionDTO;
import cl.rhoffmann.previred.desafiospringboot.api.entity.EstadoTarea;
import cl.rhoffmann.previred.desafiospringboot.api.entity.Usuario;
import cl.rhoffmann.previred.desafiospringboot.api.repository.EstadoTareaRepository;
import cl.rhoffmann.previred.desafiospringboot.api.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Assertions;
import java.util.HashMap;
import java.util.Map;

/**
 * Prueba de integración para la API de gestión de tareas de NUEVO SPA.
 * <p>
 * Esta prueba verifica la funcionalidad completa de la aplicación, desde la autenticación de usuarios
 * hasta la gestión de tareas, incluyendo la creación, actualización, obtención y eliminación de tareas. Asegura
 * que la seguridad esté correctamente implementada, probando que solo los usuarios autenticados y autorizados
 * pueden acceder y modificar las tareas.
 * </p>
 * <p>
 * La prueba simula escenarios de uso real de la aplicación, incluyendo interacciones con la base de datos
 * y el sistema de autenticación JWT, utilizando {@link MockMvc} para realizar peticiones HTTP a la API.
 * Se prepara un entorno de prueba con usuarios y estados de tareas predefinidos para validar las operaciones.
 * </p>
 * <p>
 * La pruebas cubre:
 * - Autenticación correcta e incorrecta de usuarios.
 * - Creación de tareas verificando la autenticación y autorización.
 * - Actualización y obtención de tareas, asegurando que los usuarios solo puedan modificar sus propias tareas.
 * - Listado de tareas de un usuario y validación de que no se listan tareas de otros usuarios.
 * - Eliminación de tareas con verificaciones de seguridad para prevenir borrados no autorizados.
 * </p>
 * 
 * 
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class IntegracionTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EstadoTareaRepository estadoTareaRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	private final String USUARIO1_CORREO = "u1@test.com";
	private final String USUARIO1_CONTRASENA = "u1Contrasena";
	private final String USUARIO1_NOMBRE = "u1";

	private final String USUARIO2_CORREO = "u2@test.com";
	private final String USUARIO2_CONTRASENA = "u2Contrasena";
	private final String USUARIO2_NOMBRE = "u2";

	private final String ESTADO_TAREA1 = "CREADA";
	private final String ESTADO_TAREA2 = "EN_PROCESO";
	private final String ESTADO_TAREA3 = "COMPLETADA";

	private final Long TAREA1_ID = 1L;
	private final String TAREA1_NOMBRE = "Nombre tarea1";
	private final String TAREA1_DESCRIPCION = "descripcion tarea1";
	private final String TAREA1_NOMBRE_MODIFICADO = "Nombre tarea1 modificado";

	private final Long TAREA2_ID = 2L;
	private final String TAREA2_NOMBRE = "Nombre tarea2";
	private final String TAREA2_DESCRIPCION = "descripcion tarea2";
	private final String TAREA2_NOMBRE_MODIFICADO = "Nombre tarea2 modificado";

	@BeforeEach
	public void setup() {
		usuarioRepository
				.save(new Usuario(USUARIO1_CORREO, passwordEncoder.encode(USUARIO1_CONTRASENA), USUARIO1_NOMBRE));
		usuarioRepository
				.save(new Usuario(USUARIO2_CORREO, passwordEncoder.encode(USUARIO2_CONTRASENA), USUARIO2_NOMBRE));

		estadoTareaRepository.save(new EstadoTarea(ESTADO_TAREA1));
		estadoTareaRepository.save(new EstadoTarea(ESTADO_TAREA2));
		estadoTareaRepository.save(new EstadoTarea(ESTADO_TAREA3));
	}

	/**
     * Ejecuta una prueba completa de integración, validando todas las funcionalidades principales de la API.
     * <p>
     * Incluye pruebas de autenticación, operaciones CRUD sobre tareas, y validaciones de seguridad para
     * asegurar que las operaciones están restringidas a usuarios autenticados y autorizados. Utiliza
     * aserciones para verificar las respuestas esperadas de la API en cada paso del flujo de trabajo de las tareas.
     * </p>
     *
     * @throws Exception si ocurre un error durante la ejecución de las pruebas.
     */
	@Test
	void pruebaCompleta() throws Exception {

		// Probamos realizar login con el usuario 1, pero con credenciales incorrectas.
		// Se espera status 401
		loginUsuario(USUARIO1_CORREO, USUARIO2_CONTRASENA, status().isUnauthorized());
		// Probamos realizar login con el usuario 2, pero con credenciales incorrectas.
		// Se espera status 401
		loginUsuario(USUARIO2_CORREO, USUARIO1_CONTRASENA, status().isUnauthorized());

		// Probamos hacer login con el usuario 1 y sus credenciales correctas. Se espera
		// status 200
		String tokenAcceso1 = loginUsuario(USUARIO1_CORREO, USUARIO1_CONTRASENA, status().isOk());
		// Probamos hacer login con el usuario 2 y sus credenciales correctas. Se espera
		// status 200
		String tokenAcceso2 = loginUsuario(USUARIO2_CORREO, USUARIO2_CONTRASENA, status().isOk());

		// Creamos la tarea 1, con el usuario 1. Se espera status 200
		crearTareaUsuario(tokenAcceso1, TAREA1_NOMBRE, TAREA1_DESCRIPCION, status().isOk());
		// Creamos la tarea 2, con el usuario 2. Se espera status 200
		crearTareaUsuario(tokenAcceso2, TAREA2_NOMBRE, TAREA2_DESCRIPCION, status().isOk());

		// Probamos que el usuario 1 pueda actualizar su tarea (tarea 1). Se espera
		// status 200
		actualizarTarea(tokenAcceso1, TAREA1_ID, TAREA1_NOMBRE_MODIFICADO, status().isOk());
		// Probamos que el usuario 2 pueda actualizar su tarea (tarea 2). Se espera
		// status 200
		actualizarTarea(tokenAcceso2, TAREA2_ID, TAREA2_NOMBRE_MODIFICADO, status().isOk());
		// Probamos que el usuario 1 actualice la tarea 2. Se espera status 400
		actualizarTarea(tokenAcceso1, TAREA2_ID, TAREA1_NOMBRE_MODIFICADO, status().isBadRequest());
		// Probamos que el usuario 2 actualice la tarea 1. Se espera status 400
		actualizarTarea(tokenAcceso2, TAREA1_ID, TAREA2_NOMBRE_MODIFICADO, status().isBadRequest());

		// Comprobamos que el usuario 1 pueda obtener la tarea 1. Se espera status 200
		obtenerTarea(tokenAcceso1, TAREA1_ID, status().isOk());
		// Comprobamos que el usuario 2 pueda obtener la tarea 2. Se espera status 200
		obtenerTarea(tokenAcceso2, TAREA2_ID, status().isOk());
		// Probamos que el usuario 1 obtenga la tarea 2. Se espera status 400
		obtenerTarea(tokenAcceso1, TAREA2_ID, status().isBadRequest());
		// Probamos que el usuario 2 obtenga la tarea 1. Se espera status 400
		obtenerTarea(tokenAcceso2, TAREA1_ID, status().isBadRequest());

		// Comprobamos que el usuario 1 pueda obtener su lista de tareas.
		obtenerTareas(tokenAcceso1, TAREA1_NOMBRE_MODIFICADO, true);
		// Comprobamos que el usuario 2 pueda obtener su lista de tareas.
		obtenerTareas(tokenAcceso2, TAREA2_NOMBRE_MODIFICADO, true);

		// Comprobamos que el usuario 1 no tenga a la tarea 2 en su listado.
		obtenerTareas(tokenAcceso1, TAREA2_NOMBRE_MODIFICADO, false);
		// Comprobamos que el usuario 2 no tenga a la tarea 1 en su listado.
		obtenerTareas(tokenAcceso2, TAREA1_NOMBRE_MODIFICADO, false);

		// Intentamos eliminar la tarea 2 con el usuario 1. Se espera status 400
		eliminarTarea(tokenAcceso1, TAREA2_ID, status().isBadRequest());
		// Intentamos eliminar la tarea 1 con el usuario 2. Se espera status 400
		eliminarTarea(tokenAcceso2, TAREA1_ID, status().isBadRequest());

		// Intentamos eliminar la tarea 1 con el usuario 1. Se espera status 200
		eliminarTarea(tokenAcceso1, TAREA1_ID, status().isOk());
		// Intentamos eliminar la tarea 2 con el usuario 2. Se espera status 200
		eliminarTarea(tokenAcceso2, TAREA2_ID, status().isOk());
	}

	private String loginUsuario(String correo, String contrasena, ResultMatcher estadoEsperado)
			throws JsonProcessingException, Exception {
		PeticionAutenticacionDTO peticion = new PeticionAutenticacionDTO(correo, contrasena);
		MvcResult mvcResult = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(peticion))).andExpect(estadoEsperado).andReturn();

		String response = mvcResult.getResponse().getContentAsString();
		return response.isEmpty() || response == null ? "" : JsonPath.parse(response).read("$.tokenAcceso");
	}

	private void crearTareaUsuario(String tokenAcceso, String nombreTarea, String descripcionTarea,
			ResultMatcher estadoEsperado) throws JsonProcessingException, Exception {

		Map<String, Object> tareaDatos = new HashMap<>();
		tareaDatos.put("nombre", nombreTarea);
		tareaDatos.put("descripcion", descripcionTarea);

		String tareaDatosJson = objectMapper.writeValueAsString(tareaDatos);

		mockMvc.perform(post("/api/tareas").contentType(MediaType.APPLICATION_JSON).content(tareaDatosJson)
				.header("Authorization", "Bearer " + tokenAcceso)).andExpect(estadoEsperado).andReturn();
	}

	private void actualizarTarea(String tokenAcceso, Long tareaId, String nombreTarea, ResultMatcher estadoEsperado)
			throws JsonProcessingException, Exception {
		Map<String, Object> tareaDatos = new HashMap<>();
		tareaDatos.put("nombre", nombreTarea);
		String tareaDatosJson = objectMapper.writeValueAsString(tareaDatos);
		mockMvc.perform(put("/api/tareas/" + String.valueOf(tareaId)).contentType(MediaType.APPLICATION_JSON)
				.content(tareaDatosJson).header("Authorization", "Bearer " + tokenAcceso)).andExpect(estadoEsperado)
				.andReturn();
	}

	private void obtenerTarea(String tokenAcceso, Long tareaId, ResultMatcher estadoEsperado)
			throws JsonProcessingException, Exception {
		mockMvc.perform(get("/api/tareas/" + String.valueOf(tareaId)).header("Authorization", "Bearer " + tokenAcceso))
				.andExpect(estadoEsperado).andReturn();
	}

	private void obtenerTareas(String tokenAcceso, String nombreEsperado, boolean coincidir)
			throws JsonProcessingException, Exception {
		mockMvc.perform(get("/api/tareas").header("Authorization", "Bearer " + tokenAcceso)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1)).andExpect(result -> {
					String nombreActual = JsonPath.parse(result.getResponse().getContentAsString()).read("$[0].nombre");

					if (!nombreActual.equals(nombreEsperado) && coincidir) {
						Assertions.fail("No tiene el nombre esperado y debieran coincidir");
					}
				}).andReturn();
	}

	private void eliminarTarea(String tokenAcceso, Long tareaId, ResultMatcher estadoEsperado)
			throws JsonProcessingException, Exception {
		mockMvc.perform(
				delete("/api/tareas/" + String.valueOf(tareaId)).header("Authorization", "Bearer " + tokenAcceso))
				.andExpect(estadoEsperado).andReturn();
	}

}
