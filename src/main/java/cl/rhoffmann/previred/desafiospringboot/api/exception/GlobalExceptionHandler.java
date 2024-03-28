package cl.rhoffmann.previred.desafiospringboot.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Maneja excepciones de tipo {@link RuntimeException}.
 * <p>
 * Este método captura cualquier {@link RuntimeException} no manejada específicamente por otros
 * manejadores de excepciones y construye una respuesta con un mensaje de error y un código de estado HTTP BAD_REQUEST (400).
 * </p>
 *
 * @param ex La excepción capturada.
 * @return Una {@link ResponseEntity} que encapsula el error ocurrido y el estado HTTP adecuado.
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
		return new ResponseEntity<>(apiError, apiError.getEstado());
	}

	/**
     * Clase interna que representa un error de la API.
     * <p>
     * Esta clase se utiliza para estructurar la respuesta de error enviada a los clientes de la API,
     * incluyendo un código de estado HTTP y un mensaje descriptivo del error.
     * </p>
     */
	protected class ApiError {

		private HttpStatus estado;
		private String mensaje;

		public ApiError(HttpStatus estado, String mensaje) {
			super();
			this.estado = estado;
			this.mensaje = mensaje;
		}

		public HttpStatus getEstado() {
			return estado;
		}

		public void setEstado(HttpStatus estado) {
			this.estado = estado;
		}

		public String getMensaje() {
			return mensaje;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}

	}
}
