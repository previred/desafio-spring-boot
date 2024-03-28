package cl.rhoffmann.previred.desafiospringboot.api.exception; 

/**
 * Excepción personalizada para indicar que un usuario específico no ha sido encontrado.
 * <p>
 * Se lanza para señalar que una operación que busca a un usuario por algún criterio específico
 * (como su identificador único, correo electrónico, etc.) no ha podido encontrar al usuario en cuestión.
 * Esto puede ser usado para proporcionar feedback específico al cliente de la API o en el flujo de la aplicación,
 * permitiendo un manejo de error más detallado y adecuado.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
public class UsuarioNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 131484251878062138L;

	/**
     * Constructor que inicializa la excepción con un mensaje predeterminado.
     */
	public UsuarioNoEncontradoException() {
        super("Usuario no encontrado");
    }

}
