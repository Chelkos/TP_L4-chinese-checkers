package connection;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import exceptions.CreatingPlayerException;
import exceptions.IllegalMoveException;
import gameobjects.Field;

public class Game {
	private Field[][] board; //possibly will be changed to Player[][], Shapes not needed here
	private Player[] players;
	private Player currentPlayer;
	
	public Game(int numberOfPlayers) {
		this.players=new Player[numberOfPlayers];
		this.board=new Field[17][13];
	}
	
	public Player addPlayer(Socket socket, String name, Color color) throws CreatingPlayerException{
		for(Player p : players)
			if(p.color.equals(color))
				throw new CreatingPlayerException("Player already exists!");
		int i=0;
		while(players[i]!=null) { i++; }
		if(i<players.length) {
			Player player=new Player(socket, name, color);
			players[i]=player;
			return player;
		}
		else
			throw new CreatingPlayerException("Too many players!");
	}
	
	public void randomizePlayer() {
		currentPlayer=players[new Random().nextInt(players.length)];
	}
	
	public boolean hasWinner() {
		return false;
	}
	
	public synchronized void select(int i, int j, Player player) {
	}
	
	public synchronized void move(int begI, int begJ, int endI, int endJ, Player player) {
		
	}
	
	class Player implements Runnable {
		public String name;
		public Color color;
		public Socket socket;
		public Scanner input;
		public PrintWriter output;
		
		public Player(Socket socket, String name, Color color) {
			this.socket=socket;
			this.name=name;
			this.color=color;
		}
		
		@Override
		public void run() {
			
		}
		
		private void setup() throws IOException{
			input=new Scanner(socket.getInputStream());
			output=new PrintWriter(socket.getOutputStream(), true);
		}
		
		private void processCommands() {
			int[] selection=new int[2];			
			while (input.hasNextLine()) {
				String command = input.nextLine();
	            if (command.startsWith("QUIT")) {
	                return;
	            } else if (command.startsWith("SELECT")) {
	             	selection[0]=Integer.parseInt(command.substring(7,command.indexOf('|')));
	               	selection[1]=Integer.parseInt(command.substring(command.indexOf('|')));
	                processSelectCommand(selection[0],selection[1]);
	            } else if (command.startsWith("MOVE")) {
	                int i,j;
	               	i=Integer.parseInt(command.substring(5,command.indexOf('|')));
	               	j=Integer.parseInt(command.substring(command.indexOf('|')));
	                processMoveCommand(selection[0],selection[1],i,j);
	            }
	        }
		}
		
		private void processSelectCommand(int i, int j) {
			
		}
		
		private void processMoveCommand(int begI, int begJ, int endI, int endJ) {
			
		}
	}
}
