package connection;

import connection.Game.Player;
import exceptions.IllegalMoveException;
import exceptions.IllegalSelectionException;
/**
 * Interface responsible for checking game rules on move or select by player.
 *
 */
public interface GameInterface {
	/**
	 * Checks validity of select action.
	 * @param begI selection 'x' coordinate
	 * @param begJ selection 'y' coordinate
	 * @param player player currently performing action
	 * @throws IllegalSelectionException thrown in case of rule violation
	 */
	public void select(int begI, int begJ, Player player) throws IllegalSelectionException;
	/**
	 * Checks validity of move action.
	 * @param begI starting point 'x' coordinate
	 * @param begJ starting point 'y' coordinate
	 * @param endI target point 'x' coordinate
	 * @param endJ target point 'y' coordinate
	 * @param player player currently performing action
	 * @throws IllegalMoveException thrown in case of rule violation
	 */
	public void move(int begI, int begJ, int endI, int endJ, Player player) throws IllegalMoveException;
	
}
