package rules;

import connection.GameDAO;
import connection.Game.Player;
import exceptions.IllegalMoveException;
import gameobjects.Peg;
/**
 * 
 * Basic Moving Rule used to chceck if the move by the client was performed correctly
 *
 */
public class BasicMovingRule extends MovingRule {

	@Override
	public synchronized void move(int begI, int begJ, int endI, int endJ, Player player) throws IllegalMoveException {
		Player currentPlayer=gameDAO.getCurrentPlayer();
		Peg selection=gameDAO.getBoardField(begI, begJ);
		Peg target=gameDAO.getBoardField(endI, endJ);
		if(player!=currentPlayer)
			throw new IllegalMoveException("Not your turn!");
		else if(target!=null)
			throw new IllegalMoveException("This field is occupied!");
		else if(player.movedPeg!=null && selection!=player.movedPeg)
			throw new IllegalMoveException("You can move only one peg in a turn!");
		game.move(begI, begJ, endI, endJ, currentPlayer);
	}

}
