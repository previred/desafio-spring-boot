package cl.rhoffmann.previred.desafiospringboot.api.exception;

/**
 * Excepción personalizada para indicar que una tarea específica no ha sido
 * encontrada.
 * <p>
 * Esta excepción se utiliza en situaciones donde una operación intenta acceder
 * a una tarea específica por su identificador o cualquier otro criterio de
 * búsqueda, y dicha tarea no existe o no está disponible en el contexto actual
 * de la aplicación (por ejemplo, en la base de datos). Su propósito es
 * proporcionar un manejo más claro y específico de errores relacionados con la
 * búsqueda de de tareas.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 * 
 */
public class TareaNoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 131484251878062138L;

	/**
	 * Constructor que inicializa la excepción con un mensaje predeterminado.
	 */
	public TareaNoEncontradaException() {
		super("Tarea no encontrada");
	}

}
