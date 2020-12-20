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
import gameobjects.Peg;

public class Game {
	private Peg[][] board; //server version of board, only info about players needed, so Pegs instead of Fields
	private Player[][] baseField; //if field is a base of certain player, then returns this player, else null
	private Player[] players; //players in game
	private Player currentPlayer;
	private int currentPlayerIndex;
	private int[] selection=new int[2]; //auxiliary variable for selection of field
	
	public Game(int numberOfPlayers) {
		this.players=new Player[numberOfPlayers];
		this.board=new Peg[17][17];
		this.baseField=new Player[17][17];
	}
	
	public void randomizePlayer() {
		currentPlayerIndex=new Random().nextInt(players.length);
	}
	
	public void setup() {
		int n=players.length;
		if(n==2) {
			for(int i=0; i<=3; i++) {
				for(int j=0; j<=i; j++) {
					board[7-i][3-j]=new Peg(players[0].color); //set top triangle pegs for one player
					baseField[7-i][3-j]=players[1]; //set top triangle as target for the other player
					board[9+i][13+j]=new Peg(players[1].color); //do reverse for bottom triangle
					baseField[9+i][13+j]=players[0];
				}
			}
		} else if(n==3) {
			for(int i=0; i<=3; i++) { //setup for first player
				for(int j=0; j<=i; j++) {
					board[9+i][13+j]=new Peg(players[0].color); 
					baseField[7-i][3-j]=players[0]; 
				}
			}
			for(int i=0; i<=3; i++) { //setup for second and third player
				for(int j=0; j<=i; j++) {
					board[i][4+j]=new Peg(players[1].color); 
					board[9+i][4+j]=new Peg(players[2].color); 
					baseField[7-i][12-j]=players[2];
					baseField[16-i][12-j]=players[1];
				}
			}
		} else if(n==4) {
			
			
		} else if(n==6) {
			
		}
	}
	
	public void addPlayer(Player player) {
		int i=0;
		while(players[i]!=null) { i++; }
		if(i<players.length)
			players[i]=player;
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
		selection[0]=i; selection[1]=j;
	}
	
	public synchronized void move(int begI, int begJ, int endI, int endJ, Player player) throws IllegalMoveException{
		if(player!=currentPlayer)
			throw new IllegalMoveException("Not your turn!");
		else if(board[endI][endJ]!=null)
			throw new IllegalMoveException("This field is occupied!");
		else if(board[begI][begJ].inBase() && baseField[endI][endJ]==null)
			throw new IllegalMoveException("You can't move out of base!");
		board[endI][endJ]=board[begI][begJ]; board[begI][begJ]=null;
		if(baseField[endI][endJ]==currentPlayer)
			//board[endI][endJ].enterBase()
			;
	}
	
	public synchronized void endTurn(Player player) throws Exception{
		if(player!=currentPlayer)
			throw new Exception("Not your turn!");
		currentPlayerIndex=(currentPlayerIndex+1)%(players.length);
		currentPlayer=players[currentPlayerIndex];
		selection[0]=-1; selection[1]=-1;
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
			addPlayer(this);
		}
		
		@Override
		public void run() {
			try {
				this.setup();
				processCommands();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				for(Player p : players)
					if(p!=null && p.output!=null) {
						output.println("PLAYER_LEFT: " + p.name);
						break;
					}
				try {
					socket.close();
				} catch(IOException e) { }
			}
			
		}
		
		private void setup() throws IOException{
			input=new Scanner(socket.getInputStream());
			output=new PrintWriter(socket.getOutputStream(), true);
			output.println("WELCOME " + name);
			if(this==players[players.length-1]) {
				currentPlayer=players[currentPlayerIndex];
				currentPlayer.output.println("MESSAGE Your move");
			} else {
				output.println("MESSAGE Waiting for opponents");
			}
				
		}
		
		private void processCommands() {			
			while (input.hasNextLine()) {
				String command = input.nextLine();
	            if (command.startsWith("QUIT")) {
	                return;
	            } else if (command.startsWith("SELECT")) {
	            	int i, j;
	             	i=Integer.parseInt(command.substring(7,command.indexOf('|')));
	               	j=Integer.parseInt(command.substring(command.indexOf('|')));
	                processSelectCommand(i, j);
	            } else if (command.startsWith("MOVE")) {
	                int endI, endJ;
	               	endI=Integer.parseInt(command.substring(5,command.indexOf('|')));
	               	endJ=Integer.parseInt(command.substring(command.indexOf('|')));
	                processMoveCommand(selection[0], selection[1], endI, endJ);
	            } else if (command.startsWith("END_TURN")) {
	            	processEndTurnCommand();
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
						p.output.println("MOVE " + this.name + ":" + begI + "|" + begJ + " TO " + endI + "|" + endJ);
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
		
		private void processEndTurnCommand() {
			try {
				endTurn(this);
				output.println("MESSAGE End of turn");
			} catch(Exception e) {
				output.println(e.getMessage());
			}
		}
	}
}
