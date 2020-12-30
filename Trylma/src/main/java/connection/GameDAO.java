package connection;

import connection.Game.Player;
import gameobjects.Peg;

public class GameDAO {
	private Peg[][] board;
	private Player[][] baseField;
	private Player currentPlayer;
	
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
