package Trylma;

import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;

import connection.Game;
import exceptions.CreatingPlayerException;

public class DummyServer {
	private Game game;
	private Game.Player spyPlayer;
	private ServerSocket listener;
	
	public DummyServer() {
		try {
			game=new Game(1);
			listener=new ServerSocket(58901);
			spyPlayer=game.addPlayer(listener.accept(), "dummyPlayer", Color.RED);
		} catch(IOException e) { }
		  catch(CreatingPlayerException e) { }
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public Game.Player getPlayer(){
		return this.spyPlayer;
	}
	
}
