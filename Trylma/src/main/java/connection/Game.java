package connection;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import gameobjects.Field;

public class Game {
	private Field[][] board;
	private Player[] players;
	
	public Game(int playersNumber) {
		this.players=new Player[playersNumber];
	}
	
	public Player winner() {
		return null;
	}
	
	public synchronized void move(Point beg, Point end, Player player) {
		
	}
	
	class Player implements Runnable{
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
			
		}
		
		private void processMoveCommand(Point beg, Point end) {
			
		}
	}
}
