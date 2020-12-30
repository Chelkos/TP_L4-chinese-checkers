package rules;

import connection.Game.Player;
import connection.GameDAO;
import exceptions.IllegalMoveException;
import gameobjects.Peg;

public class JumpDistanceRule extends MovingRule {

	@Override
	public synchronized void move(int begI, int begJ, int endI, int endJ, Player player) throws IllegalMoveException {
		int distance=Math.max(Math.abs(endI-begI), Math.abs(endJ-begJ)); //distance in maximum metric
		if(distance==1 && !player.jumpedOverPeg) { //jump to adjacent field, can be done only once and can't be combined with jump over peg
			player.canMove=false; //processCommands() will call for end of turn automatically
		} else if(distance==2 && Math.abs(endI-begI)!=1 && Math.abs(endJ-begJ)!=1) { //jump in a straight line over peg
			player.jumpedOverPeg=true;
			int medI=(begI+endI)/2, medJ=(begJ+endJ)/2; //coordinates of peg between beg and end
			Peg obstacle=gameDAO.getBoardField(medI, medJ);
			if(obstacle==null) //checks if there is a peg to jump over
				throw new IllegalMoveException("This move is not allowed!");
		} else {
			throw new IllegalMoveException("This move is not allowed!");
		}
		game.move(begI, begJ, endI, endJ, player);
	}

}
