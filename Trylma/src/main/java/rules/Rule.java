package rules;

import connection.GameDAO;
import connection.GameInterface;

public abstract class Rule implements GameInterface {
	protected GameInterface game;
	protected GameDAO gameDAO;
	
	public void setDecoratedInterface(GameInterface game) {
		this.game=game;
	}
	
	public void setGameDAO(GameDAO gameDAO) {
		this.gameDAO=gameDAO;
	}
	
}
