package connection;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import exceptions.IllegalMoveException;
import exceptions.InvalidSelectException;
import gameobjects.Peg;

public class Game {
	private Peg[][] board; //server version of board, only info about players needed, so Pegs instead of Fields
	private Player[][] baseField; //if field is a base of certain player, then returns this player, else null
	private Player[] players; //players in game
	private Player currentPlayer;
	private int currentPlayerIndex;
	
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
					board[7-i][3-j]=new Peg(players[0].color);//put first player's pegs in top triangle
					board[9+i][13+j]=new Peg(players[1].color);//put second player's pegs in bottom triangle
					baseField[9+i][13+j]=players[0]; //set bottom triangle as first player's target "base"  
					baseField[7-i][3-j]=players[1]; //set top triangle as second player's target "base"
				}
			}
		} else if(n==3) {
			for(int i=0; i<=3; i++) { //setup for second and third player, middle triangles
				for(int j=0; j<=i; j++) {
					board[9+i][4+j]=new Peg(players[0].color); //put pegs
					board[9+i][13+j]=new Peg(players[1].color); 
					board[i][4+j]=new Peg(players[2].color); 
					baseField[7-i][12-j]=players[0]; //set bases
					baseField[7-i][3-j]=players[1]; 
					baseField[16-i][12-j]=players[2];
				}
			}
		} else if(n==4) {
			for(int i=0; i<=3; i++) { //setup triangles on sides for all players
				for(int j=0; j<=i; j++) {
					board[9+i][4+j]=new Peg(players[0].color); 
					board[7-i][12-j]=new Peg(players[1].color);
					board[16-i][12-j]=new Peg(players[2].color);
					board[i][4+j]=new Peg(players[3].color);
					baseField[9+i][4+j]=players[2]; 
					baseField[7-i][12-j]=players[3];
					baseField[16-i][12-j]=players[0];
					baseField[i][4+j]=players[1];
				}
			}
		} else if(n==6) {
			for(int i=0; i<=3; i++) { //setup all triangles 
				for(int j=0; j<=i; j++) {
					board[7-i][3-j]=new Peg(players[0].color);
					board[9+i][4+j]=new Peg(players[1].color); 
					board[16-i][12-j]=new Peg(players[2].color);
					board[9+i][13+j]=new Peg(players[3].color);
					board[7-i][12-j]=new Peg(players[4].color);
					board[i][4+j]=new Peg(players[5].color);
					baseField[9+i][13+j]=players[0];
					baseField[16-i][12-j]=players[1];
					baseField[i][4+j]=players[2];
					baseField[7-i][3-j]=players[3];  
					baseField[9+i][4+j]=players[4]; 
					baseField[7-i][12-j]=players[5];
				}
			}
		}
	}
	
	public void addPlayer(Player player) {
		for(int i=0; i<players.length; i++)
			if(players[i]==null) {
				players[i]=player;
				return;
			}
	}
	
	public boolean hasWinner() {
		for(int i=0; i<board.length; i++)
			for(int j=0; j<board[i].length; j++)
				if(board[i][j]!=null && board[i][j].getOwnerColor()==currentPlayer.color && baseField[i][j]!=currentPlayer)
					return false;
		return true;
	}
	
	public synchronized void select(int begI, int begJ, Player player) throws InvalidSelectException{
		if(player!=currentPlayer)
			throw new InvalidSelectException("Not your turn!");
		else if(board[begI][begJ]==null)
			throw new InvalidSelectException("This field is empty!");
		else if(board[begI][begJ].getOwnerColor()!=currentPlayer.color)
			throw new InvalidSelectException("This is not your peg!");
	}
	
	public synchronized void move(int begI, int begJ, int endI, int endJ, Player player) throws IllegalMoveException{
		verifyMove(begI, begJ, endI, endJ, player);
		board[endI][endJ]=board[begI][begJ]; board[begI][begJ]=null;
	}
	
	public synchronized void endTurn(Player player) throws Exception{
		if(player!=currentPlayer)
			throw new Exception("Not your turn!");
		currentPlayerIndex=(currentPlayerIndex+1)%(players.length);
		currentPlayer=players[currentPlayerIndex];
	}
	
	private void verifyMove(int begI, int begJ, int endI, int endJ, Player player) throws IllegalMoveException {
		if(player!=currentPlayer)
			throw new IllegalMoveException("Not your turn!");
		else if(board[endI][endJ]!=null)
			throw new IllegalMoveException("This field is occupied!");
		else if(baseField[begI][begJ]==currentPlayer && baseField[endI][endJ]!=currentPlayer)
			throw new IllegalMoveException("You can't move out of base!");
		else if(player.movedPeg!=null && board[begI][begJ]!=player.movedPeg)
			throw new IllegalMoveException("You can move only one peg in turn!");
		int distance=Math.max(Math.abs(endI-begI), Math.abs(endJ-begJ)); //distance in maximum metric
		if(distance==1 && !player.jumpedOverPeg) { //jump to adjacent field, can be done only once and can't be combined with jump over peg
			player.canMove=false; //processCommands() will call for end of turn automatically
		} else if(distance==2 && Math.abs(endI-begI)!=1 && Math.abs(endJ-begJ)!=1) { //jump in a straight line over peg
			player.jumpedOverPeg=true;
			int medI=(begI+endI)/2, medJ=(begJ+endJ)/2; //coordinates of field between beg and end
			if(board[medI][medJ]==null) //checks if there is a peg to jump over
				throw new IllegalMoveException("This move is not allowed!");
		} else {
			throw new IllegalMoveException("This move is not allowed!");
		}
		player.movedPeg=board[begI][begJ]; //finally indicates which peg was successfully moved
	}
	
	class Player implements Runnable {
		private int[] selection=new int[2]; //auxiliary variable for selection of field
		public String name;
		public Color color;
		public boolean canMove=true;
		public boolean jumpedOverPeg=false;
		public Peg movedPeg=null;
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
			output.println("PLAYERS: " + Integer.toString(players.length));
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
	            	selection[0]=Integer.parseInt(command.substring(7,command.indexOf('|')));
	            	selection[1]=Integer.parseInt(command.substring(command.indexOf('|')+1));
	                processSelectCommand(selection[0], selection[1]);
	            } else if (command.startsWith("MOVE")) {
	                int endI, endJ;
	               	endI=Integer.parseInt(command.substring(5,command.indexOf('|')));
	               	endJ=Integer.parseInt(command.substring(command.indexOf('|')+1));
	                processMoveCommand(selection[0], selection[1], endI, endJ);
	                if(!canMove)
	                	processEndTurnCommand();
	            } else if (command.startsWith("END_TURN")) {
	            	processEndTurnCommand();
	            }
	        }
		}
		
		private void processSelectCommand(int begI, int begJ) {
			try {
				select(begI, begJ, this);
				output.println("VALID_SELECTION");
			} catch(InvalidSelectException e) {
				output.println("INVALID_SELECTION " + e.getMessage());
			}
		}
		
		private void processMoveCommand(int begI, int begJ, int endI, int endJ) {
			try {
				move(begI, begJ, endI, endJ, this);
				output.println("VALID_MOVE");
				for(Player p : players)
					if(!p.equals(this))
						p.output.println("MOVE " + this.name + "|" + begI + ":" + begJ + "TO" + endI + ":" + endJ);
				if(hasWinner()) {
					output.println("VICTORY");
					for(Player p : players)
						if(!p.equals(this))
							p.output.println("DEFEAT: " + this.name + " won");
				}
			} catch(IllegalMoveException e) {
				output.println("INVALID_MOVE " + e.getMessage());
			}
		}
		
		private void processEndTurnCommand() {
			try {
				endTurn(this);
				canMove=true;
				jumpedOverPeg=false;
				movedPeg=null;
				output.println("MESSAGE End of turn");
				currentPlayer.output.println("MESSAGE Your move");
			} catch(Exception e) {
				output.println("MESSAGE " + e.getMessage());
			}
		}
	}
}