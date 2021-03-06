package connection;

import connection.Game.Player;
import gameobjects.Peg;
/**
 * 
 * Used for checking the values of private fields in Game
 *
 */
public class GameDAO {
	private Peg[][] board;
	private Player[][] baseField;
	private Player currentPlayer;
	
	/**
	 * Updates information about game's state.
	 * @param board current board state
	 * @param baseField location of players' bases
	 * @param currentPlayer player currently performing action
	 */
	public void update(Peg[][] board, Player[][] baseField, Player currentPlayer) {
		this.board=board;
		this.baseField=baseField;
		this.currentPlayer=currentPlayer;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Peg getBoardField(int i, int j) {
		return board[i][j];
	}
	
	public Player getBaseFieldOwner(int i, int j) {
		return baseField[i][j];
	}
	
}
