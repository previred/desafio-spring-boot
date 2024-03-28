package cl.rhoffmann.previred.desafiospringboot.api.exception; 

/**
 * Excepción personalizada para indicar que un estado de tarea no ha sido encontrado.
 * <p>
 * Esta excepción se lanza cuando una operación en la aplicación intenta acceder a un estado de tarea
 * en la base de datos o en cualquier otro contexto de persistencia, pero el estado especificado no existe o no puede ser encontrado.
 * Su propósito es proporcionar un manejo más claro y específico de errores relacionados con la búsqueda de estados de tarea.
 * </p>
 * 
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
public class EstadoTareaNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 131484251878062138L;

	/**
     * Constructor que inicializa la excepción con un mensaje predeterminado.
     */
	public EstadoTareaNoEncontradoException() {
        super("EstadoTarea no encontrado");
    }

}
