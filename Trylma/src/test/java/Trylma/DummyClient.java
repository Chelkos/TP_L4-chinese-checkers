package Trylma;

import java.net.Socket;
import java.util.Scanner;

import connection.ITrilmaClient;

public class DummyClient implements ITrilmaClient{
	private Socket socket;
	private Scanner input;
	
	public DummyClient() throws Exception{
		socket=new Socket("127.0.0.1", 58901);
		input=new Scanner(socket.getInputStream());
	}
	
	public void play() throws Exception{
		
	}
}
