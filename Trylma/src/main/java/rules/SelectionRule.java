package rules;

import connection.Game.Player;
import exceptions.IllegalMoveException;
import exceptions.IllegalSelectionException;

public abstract class SelectionRule extends Rule {
	
	public abstract void select(int begI, int begJ, Player player) throws IllegalSelectionException;
	
	public void move(int begI, int begJ, int endI, int endJ, Player player) throws IllegalMoveException {
		game.move(begI, begJ, endI, endJ, player);
	}
	
}
