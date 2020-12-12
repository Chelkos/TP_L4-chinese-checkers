package connection;

import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import gameobjects.*;

public class TrilmaClient {
	private JFrame frame;
	private JLabel messageLabel;
	private Board board;
	
	private Field selectedField;
	private Field targetField;
	
	private Socket socket;
	private Scanner input;
	private PrintWriter output;
	
	public TrilmaClient(String serverAddress) throws Exception{
		
	}
	
	public void play() throws Exception{
		
	}
	
	public static void main(String args[]) throws Exception{
		
	}
}
