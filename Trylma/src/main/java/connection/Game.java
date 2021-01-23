package connection;
import connection.DataTransfer;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import exceptions.IllegalMoveException;
import exceptions.IllegalSelectionException;
import gameobjects.Peg;
import rules.Rule;
/**
 * 
 * A game class for Trilma, executes commands, checks if the action is permitted by the rules, sends back commands to TrilmaClient
 * @see TrilmaClient
 */
public class Game {
	private Peg[][] board; //server version of board, only info about players needed, so Pegs instead of Fields
	private Player[][] baseField; //if field is a base of certain player, then returns this player, else null
	private Player[] players; //players in game
	private Player currentPlayer;
	private int currentPlayerIndex;
	private GameDAO gameDAO;
	private List<Rule> rules;
	private DataTransfer dataTransfer;
	 ApplicationContext context;
	/**
	 * Initiates game with given number of players.
	 * @param numberOfPlayers number of players
	 */
	public Game(int numberOfPlayers) {
		this.context = new ClassPathXmlApplicationContext("file:src/main/java/beans.xml");
		this.dataTransfer =  (DataTransfer)context.getBean("DataTransfer");
		this.players=new Player[numberOfPlayers];
		this.board=new Peg[17][17];
		this.baseField=new Player[17][17];
		this.gameDAO=new GameDAO();
		this.rules=new ArrayList<Rule>();
		dataTransfer.addNewMove(0, 1, 1, 2, 2, Color.black);
	}
	/**
	 * Adds given rule to the interface
	 * @param rule rule to add
	 * @return Game with rule added to the interface
	 */
	public Game with(Rule rule) {
		rules.add(rule);
		return this;
	}
	
	/**
	 * Function used to get up-to-date interface.
	 * @return interface decorated with rules
	 */
	public GameInterface getDecoratedInterface() {
		gameDAO.update(board, baseField, currentPlayer);
		GameInterface trilmaInterface=new DefaultTrilmaInterface();
		for(Rule rule : rules) {
			rule.setGameDAO(gameDAO);
			rule.setDecoratedInterface(trilmaInterface);
			trilmaInterface=rule;
		}
		return trilmaInterface;
	}
	/**
	 * Adds a new player to the current players array.
	 * @param player new player to add
	 * 
	 */
	public void addPlayer(Player player) {
		for(int i=0; i<players.length; i++)
			if(players[i]==null) {
				players[i]=player;
				return;
			}
	}
	/**
	 * Sets first player randomly.
	 */
	public void randomizePlayer() {
		currentPlayerIndex=new Random().nextInt(players.length);
	}
	/**
	 * Initializes a server-side representation of board and locations of base fields.
	 */
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
			for(int i=0; i<=3; i++) { //setup top-left, top-right and bottom triangles
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
					board[16-i][12-j]=new Peg(players[1].color);
					board[7-i][12-j]=new Peg(players[2].color);
					board[i][4+j]=new Peg(players[3].color);
					baseField[7-i][12-j]=players[0];
					baseField[i][4+j]=players[1];
					baseField[9+i][4+j]=players[2]; 
					baseField[16-i][12-j]=players[3];
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
					baseField[7-i][12-j]=players[1];
					baseField[i][4+j]=players[2];
					baseField[7-i][3-j]=players[3];  
					baseField[9+i][4+j]=players[4]; 
					baseField[16-i][12-j]=players[5];
				}
			}
		}
	}
	/**
	 * 
	 * @return Returns true when there is a winner.
	 */
	public boolean hasWinner() {
		for(int i=0; i<board.length; i++)
			for(int j=0; j<board[i].length; j++)
				if(board[i][j]!=null && board[i][j].getOwnerColor()==currentPlayer.color && baseField[i][j]!=currentPlayer)
					return false;
		return true;
	}
	/**
	 * Ends a turn for a current player and sets next player as a current player 
	 * @param player who tried to finish their turn
	 * @throws Exception if it's not the current player the one receives a message of an error 
	 */
	public synchronized void endTurn(Player player) throws Exception{
		if(player!=currentPlayer)
			throw new Exception("Not your turn!");
		currentPlayerIndex=(currentPlayerIndex+1)%(players.length);
		currentPlayer=players[currentPlayerIndex];
	}
	
	class DefaultTrilmaInterface implements GameInterface {

		@Override
		public synchronized void select(int begI, int begJ, Player player) throws IllegalSelectionException { }

		@Override
		public synchronized void move(int begI, int begJ, int endI, int endJ, Player player) throws IllegalMoveException {
			player.movedPeg=board[begI][begJ]; //indicates which peg was moved this turn
			board[endI][endJ]=board[begI][begJ]; board[begI][begJ]=null;
		}
		
	}
	/**
	 * 
	 * A server-side interface for client. 
	 *
	 */
	public class Player implements Runnable {
		public String name;
		public Color color;
		public boolean canMove=true;
		public boolean jumpedOverPeg=false;
		public Peg movedPeg=null;
		public Socket socket;
		public Scanner input;
		public PrintWriter output;
		
		/**
		 * Creates Player - server-side interface for client.
		 * @param socket connection socket
		 * @param name custom player's name
		 * @param color basic player's identifier - color of their pegs
		 */
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
				for(Player p : players) {
					if(p!=null && p.output!=null) {
						p.output.println("PLAYER_LEFT: " + this.name);
					}
				}
				try {
					socket.close();
				} catch(IOException e) { }
			}
			
		}
		/**
		 * Setups I/O features for player, sends them a welcome message and expected number of players, sends messages when it's the player's move, waits for players if necessary. 
		 * @throws IOException thrown in case of I/O initilaization failure
		 */
		private void setup() throws IOException{
			input=new Scanner(socket.getInputStream());
			output=new PrintWriter(socket.getOutputStream(), true);
			output.println("WELCOME " + name);
			output.println("PLAYERS: " + Integer.toString(players.length));
			if(this==players[players.length-1]) {
				currentPlayer=players[currentPlayerIndex];
				currentPlayer.output.println("MESSAGE Your move");
				for(Player p : players)
					if(p!=null && !p.equals(currentPlayer))
						p.output.println("MESSAGE " + currentPlayer.name + "' s move");
			} else {
				output.println("MESSAGE Waiting for opponents");
			}
				
		}
		/**
		 * Processes Commands from TrilmaClient to adequate methods.
		 */
		private void processCommands() {	
			int[] selection=new int[2];
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
		/**
		 * Checks if the Select is correct, delegating the command further .
		 * @param begI position X of a Peg on the Board
		 * @param begJ position Y of a Peg on the Board
		 * @see rules.SelectionRule
		 */
		private void processSelectCommand(int begI, int begJ) {
			try {
				getDecoratedInterface().select(begI, begJ, this);
				output.println("VALID_SELECTION");
			} catch(IllegalSelectionException e) {
				output.println("INVALID_SELECTION " + e.getMessage());
			}
		}
		/**
		 * Checks if the move is correct, delegating the command further, if is sends a message to client to move the peg on its Board.
		 * If the move was correct, checks if all of Pegs are in the base to provide victory, sends a message if true.
		 * @param begI position X of a Peg on the Board
		 * @param begJ position Y of a Peg on the Board
		 * @param endI target position X on the Board
		 * @param endJ target position Y on the Board
		 * @see rules.MovingRule
		 */
		private void processMoveCommand(int begI, int begJ, int endI, int endJ) {
			try {
				getDecoratedInterface().move(begI, begJ, endI, endJ, this);
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
		/**
		 * Ends the turn of current player, sets next player as a current player, sends a message.
		 */
		private void processEndTurnCommand() {
			try {
				endTurn(this);
				canMove=true;
				jumpedOverPeg=false;
				movedPeg=null;
				currentPlayer.output.println("MESSAGE Your move");
				for(Player p : players)
					if(p!=null && !p.equals(currentPlayer))
						p.output.println("MESSAGE " + currentPlayer.name + "'s move");
			} catch(Exception e) {
				output.println("MESSAGE " + e.getMessage());
			}
		}
	}
}