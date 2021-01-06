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
	 * @param begI
	 * @param begJ
	 * @param player
	 * @throws IllegalSelectionException
	 */
	public void select(int begI, int begJ, Player player) throws IllegalSelectionException;
	/**
	 * Checks validity of move action.
	 * @param begI
	 * @param begJ
	 * @param endI
	 * @param endJ
	 * @param player
	 * @throws IllegalMoveException
	 */
	public void move(int begI, int begJ, int endI, int endJ, Player player) throws IllegalMoveException;
	
}
