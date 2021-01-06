package exceptions;

/**
 * Exception thrown when player tries to perform illegal selection action.
 *
 */
public class IllegalSelectionException extends Exception {

	public IllegalSelectionException(String message) {
		super(message);
	}
	
}
