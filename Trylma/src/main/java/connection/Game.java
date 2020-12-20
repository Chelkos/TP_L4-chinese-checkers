package connection;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import exceptions.CreatingPlayerException;
import exceptions.IllegalMoveException;
import exceptions.InvalidSelectException;
import gameobjects.Field;
import gameobjects.Peg;

public class Game {
	private Peg[][] board; //server version of board, only info about players needed, so Pegs instead of Fields
	private Player[] players; //players in game
	private Player currentPlayer;
	private int currentPlayerIndex;
	private int[] selection=new int[2];
	
	public Game(int numberOfPlayers) {
		this.players=new Player[numberOfPlayers];
		this.board=new Peg[17][13];
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
		currentPlayerIndex=new Random().nextInt(players.length);
		currentPlayer=players[currentPlayerIndex];
	}
	
	public boolean hasWinner() {
		for(int i=0; i<board.length; i++)
			for(int j=0; j<board[i].length; j++)
				if(!board[i][j].inBase())
					return false;
		return true;
	}
	
	public synchronized void select(int i, int j, Player player) throws InvalidSelectException{
		if(player!=currentPlayer)
			throw new InvalidSelectException("Not your turn!");
		else if(board[i][j]==null)
			throw new InvalidSelectException("This is empty field!");
		else if(board[i][j].getOwnerColor()!=currentPlayer.color)
			throw new InvalidSelectException("This is not your peg!");
		
	}
	
	public synchronized void move(int begI, int begJ, int endI, int endJ, Player player) throws IllegalMoveException{
		if(player!=currentPlayer)
			throw new IllegalMoveException("Not your turn!");
		else if(board[endI][endJ]!=null)
			throw new IllegalMoveException("This field is occupied!");
		board[endI][endJ]=board[begI][begJ]; board[begI][begJ]=null;
		currentPlayerIndex=(currentPlayerIndex+1)%(players.length);
		currentPlayer=players[currentPlayerIndex];
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
			try {
				select(i, j, this);
				output.println("VALID_SELECTION");
			} catch(InvalidSelectException e) {
				output.println("INVALID_SELECTION" + e.getMessage());
			}
		}
		
		private void processMoveCommand(int begI, int begJ, int endI, int endJ) {
			try {
				move(begI, begJ, endI, endJ, this);
				output.println("VALID_MOVE");
				for(Player p : players)
					if(!p.equals(this))
						p.output.println("MOVE " + this.name + " " + begI + "|" + begJ + "TO " + endI + "|" + endJ);
				if(hasWinner()) {
					output.println("VICTORY");
					for(Player p : players)
						if(!p.equals(this))
							p.output.println("DEFEAT: " + this.name + " won");
				}
			} catch(IllegalMoveException e) {
				output.println("INVALID_MOVE" + e.getMessage());
			}
		}
	}
}
