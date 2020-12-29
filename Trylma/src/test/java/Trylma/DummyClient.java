package Trylma;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import connection.ITrilmaClient;
import gameobjects.Board;

public class DummyClient implements ITrilmaClient{
	public Socket socket;
	public Scanner input;
	public PrintWriter output;
	public Board board;
	
	public DummyClient() throws Exception{
		socket=new Socket("127.0.0.1", 58901);
		input=new Scanner(socket.getInputStream());
		output=new PrintWriter(socket.getOutputStream(), true);
		board = new Board();
		board.fillBoard(2);
	}
	
	public void play() throws Exception{
		
	}
}
