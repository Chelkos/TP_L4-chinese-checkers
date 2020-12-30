package connection;

import connection.Game.Player;
import exceptions.IllegalMoveException;
import exceptions.IllegalSelectionException;

public interface GameInterface {
	
	public void select(int begI, int begJ, Player player) throws IllegalSelectionException;
	public void move(int begI, int begJ, int endI, int endJ, Player player) throws IllegalMoveException;
	
}
