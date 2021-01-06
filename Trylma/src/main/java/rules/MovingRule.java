package rules;

import connection.Game.Player;
import exceptions.IllegalMoveException;
import exceptions.IllegalSelectionException;

/**
 * Abstract class for rule checking only validity of move action, does not modify select action.
 *
 */
public abstract class MovingRule extends Rule {
	
	public void select(int begI, int begJ, Player player) throws IllegalSelectionException { 
		game.select(begI, begJ, player);
	}
	
	public abstract void move(int begI, int begJ, int endI, int endJ, Player player) throws IllegalMoveException;
	
}
