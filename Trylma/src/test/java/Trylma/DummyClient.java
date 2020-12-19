package Trylma;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import connection.ITrilmaClient;

public class DummyClient implements ITrilmaClient{
	public Socket socket;
	public Scanner input;
	public PrintWriter output;
	
	public DummyClient() throws Exception{
		socket=new Socket("127.0.0.1", 58901);
		input=new Scanner(socket.getInputStream());
		output=new PrintWriter(socket.getOutputStream(), true);
	}
	
	public void play() throws Exception{
		
	}
}
