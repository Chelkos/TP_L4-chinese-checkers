package rules;

import connection.Game.Player;
import connection.GameDAO;
import exceptions.IllegalMoveException;

public class CantMoveOutOfBaseRule extends MovingRule {
	
	@Override
	public synchronized void move(int begI, int begJ, int endI, int endJ, Player player) throws IllegalMoveException {
		Player selectionOwner=gameDAO.getBaseFieldOwner(begI, begJ);
		Player targetOwner=gameDAO.getBaseFieldOwner(endI, endJ);
		Player currentPlayer=gameDAO.getCurrentPlayer();
		if(selectionOwner==currentPlayer && targetOwner!=currentPlayer)
			throw new IllegalMoveException("You can't move out of base!");
		game.move(begI, begJ, endI, endJ, player);
	}

}
