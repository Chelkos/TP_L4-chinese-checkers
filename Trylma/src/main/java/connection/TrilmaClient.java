package connection;

import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Button;
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

public class TrilmaClient implements ITrilmaClient{
	private JFrame frame;
	private JLabel messageLabel;
	private Board board;
	private Button end;
	private Field selectedField=null; //Set these two fields to null after (in)valid move!
	private Field targetField=null;
	
	private Socket socket;
	private Scanner input;
	private PrintWriter output;
	
	public TrilmaClient(String NoPlayers) throws Exception{
		frame = new JFrame();
		messageLabel = new JLabel();
		socket=new Socket("127.0.0.1", 58901);
		input=new Scanner(socket.getInputStream());
		output=new PrintWriter(socket.getOutputStream(), true);
		messageLabel.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(messageLabel/*, BorderLayer.SOUTH*/);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Czerwony");
        try
        {
		board=new Board(Integer.parseInt(NoPlayers));
        }
        catch(NumberFormatException e)
        {
        		System.out.println("Invalid Number of players");
        		return;
        		
        }
        frame.add(board);
		//board.setBackground(Color.WHITE);
		board.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int r=-1, p=-1;
				Field clickedField=null;
				for(int i=0; i<board.fields.length; i++) {
					for(int j=0; j<board.fields[i].length; j++) {
						if(board.fields[j][i]!=null && board.fields[j][i].contains(e.getPoint())) {
							clickedField=board.fields[j][i];
						
							r=i; 
							p=j;
							System.out.print(i);
							System.out.println(" "+j);
							break;
						}
					}
				}
				if(clickedField==null)
					return;
				if(selectedField==null) {
					selectedField=clickedField; 			//validate selection by request here and if (INVALID SELECTION ?)
					output.println("SELECT "+ r + "|" + p);	//answer in play(), reset to null
				}
				else {
					targetField=clickedField;
					output.println("MOVE "+ r + "|" + p);
				}
			}
		});
	}
	
	public void play() throws Exception{
		
	}
	
	public static void main(String args[]) throws Exception{
		TrilmaClient client = new TrilmaClient(args[0]);
        client.play();
	}
}
