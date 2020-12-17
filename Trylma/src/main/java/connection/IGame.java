package connection;

import connection.Game.Player;

public interface IGame {
	public boolean hasWinner();
	
	public void select(int i, int j, Player player);
	
	public void move(int begI, int begJ, int endI, int endJ, Player player);
}
