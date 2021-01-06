package exceptions;

/**
 * Exception thrown when player tries to perform illegal move action.
 *
 */
public class IllegalMoveException extends Exception {
	
	public IllegalMoveException(String message) {
		super(message);
	}
	
}
