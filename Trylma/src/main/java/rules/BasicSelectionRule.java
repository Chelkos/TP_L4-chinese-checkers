package rules;

import connection.Game.Player;
import connection.GameDAO;
import exceptions.IllegalSelectionException;
import gameobjects.Peg;

public class BasicSelectionRule extends SelectionRule {

	@Override
	public synchronized void select(int begI, int begJ, Player player) throws IllegalSelectionException {
		Player currentPlayer=gameDAO.getCurrentPlayer();
		Peg selection=gameDAO.getBoardField(begI, begJ);
		if(player!=currentPlayer)
			throw new IllegalSelectionException("Not your turn!");
		else if(selection==null)
			throw new IllegalSelectionException("This field is empty!");
		else if(selection.getOwnerColor()!=currentPlayer.color)
			throw new IllegalSelectionException("This is not your peg!");
		game.select(begI, begJ, currentPlayer);
	}

}
