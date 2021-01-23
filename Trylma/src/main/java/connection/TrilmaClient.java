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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gameobjects.*;
/**
 * 
 *Trilma Client initializes a Frame for the Client, provides visuals and establishes connection with a server
 *
 */
public class TrilmaClient {
	private JFrame frame;
	private JLabel messageLabel;
	private Board board;
	private Field selectedField=null; 
	private Field targetField=null;
	private Socket socket;
	private Scanner input;
	private PrintWriter output;
	private Button endTurnButton;
	private Button saveButton;
	private Button loadButton;
	public TrilmaClient() throws Exception{
		frame = new JFrame();
		saveButton = new Button("Save Game");
		loadButton = new Button ("Load Game");
		endTurnButton = new Button("End Turn");
		messageLabel = new JLabel("");
		socket=new Socket("127.0.0.1", 58901);
		input=new Scanner(socket.getInputStream());
		output=new PrintWriter(socket.getOutputStream(), true);
		messageLabel.setBackground(Color.LIGHT_GRAY);
		frame.add(endTurnButton,BorderLayout.EAST);
		frame.add(saveButton,BorderLayout.WEST);
		frame.add(loadButton,BorderLayout.WEST);
		frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
        frame.setResizable(false);
        saveButton.addMouseListener(new MouseAdapter(){
        	public void mousePressed(MouseEvent e) {
        		output.println("SAVE");
        	}
        });
        loadButton.addMouseListener(new MouseAdapter(){
        	public void mousePressed(MouseEvent e) {
        		output.println("LOAD");
        	}
        });
        endTurnButton.addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent e) {
        		output.println("END_TURN");
        	}
        });
		board=new Board();
        frame.add(board);
		board.setBackground(Color.WHITE);
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
							break;
						}
					}
				}
				if(clickedField==null)
					return;
				if(selectedField==null) {
					selectedField=clickedField; 
					output.println("SELECT "+ r + "|" + p);	
				}
				else {
					targetField=clickedField;
					output.println("MOVE "+ r + "|" + p);
				}
			}
		});
	}
	
	private void clearBoard() {
		board.fields=new Field[20][20];
	}
	
	/**
	 * Sends and receives messages from the server 
	 * @throws Exception on connection fault
	 */
    public void play() throws Exception {
        try {
        	int noPlayers;
            String response =  input.nextLine();
            frame.setTitle(response.substring(7));
            response = input.nextLine();
            noPlayers = Integer.parseInt(response.substring(9));
            board.fillBoard(noPlayers);
          
            String trimmed1,trimmed2;
            int begX,begY,endX,endY;
           
            while ( input.hasNextLine()) {
                response =  input.nextLine();
                if (response.startsWith("VALID_MOVE") ) {
                	 messageLabel.setText("Valid move, please wait");
                	targetField.accept(selectedField.getVisitor());
                	selectedField.accept(null);
                	targetField = null;
                	selectedField = null;

                	board.repaint();
                }else if(response.startsWith("MOVE")) {
                	 trimmed1 = response.substring(response.indexOf("|"),response.indexOf("TO"));
                	 trimmed2 = response.substring(response.indexOf("TO"));
                	 begX = Integer.parseInt(trimmed1.substring(trimmed1.indexOf("|")+1,trimmed1.indexOf(":")));
                	 begY = Integer.parseInt(trimmed1.substring(trimmed1.indexOf(":")+1));
                	 endX = Integer.parseInt(trimmed2.substring(trimmed2.indexOf("TO")+2,trimmed2.indexOf(":")));
                	 endY = Integer.parseInt(trimmed2.substring(trimmed2.indexOf(":")+1));
                	board.fields[endY][endX].accept(board.fields[begY][begX].getVisitor());
                	board.fields[begY][begX].accept(null);
                	 board.repaint();
                	 targetField = null;
                 	selectedField = null;
           	 	} else if (response.startsWith("INVALID_MOVE")) {
           	 		targetField = null;
           	 		selectedField = null;
           	 		messageLabel.setText(response.substring(13));
                } else if (response.startsWith("INVALID_SELECTION")) {
                	selectedField = null;
                	messageLabel.setText(response.substring(18));
                } else if (response.startsWith("VICTORY")) {
                		messageLabel.setText("Victory");
                } else if (response.startsWith("DEFEAT")) {
                		messageLabel.setText("Loser");
                } else if (response.startsWith("PLAYER_LEFT")) {
                	messageLabel.setText("Player: "+ response.substring(response.indexOf(":")+1) +" left");
                } else if (response.startsWith("MESSAGE")) {
                	messageLabel.setText(response.substring(8));
                } else if (response.startsWith("CLEAR")) {
                	clearBoard();
                } else if (response.startsWith("LOAD")) {
                	int i, j, r, g, b;
                	String parameter[]=new String[3]; String color[]=new String[3];
                	parameter=response.substring(5).split("|");
                	i=Integer.parseInt(parameter[1]); j=Integer.parseInt(parameter[2]);
                	trimmed1=parameter[0].substring(parameter[0].indexOf("=")+1);
                	r=Integer.parseInt(trimmed1.substring(0, trimmed1.indexOf(",")));
                	trimmed1=trimmed1.substring(trimmed1.indexOf("=")+1);
                	g=Integer.parseInt(trimmed1.substring(0, trimmed1.indexOf(",")));
                	trimmed1=trimmed1.substring(trimmed1.indexOf("=")+1);
                	b=Integer.parseInt(trimmed1.substring(0, trimmed1.indexOf("]")));
                	board.fields[j][i].accept(new Peg(new Color(r, g, b)));
                } else if (response.startsWith("REPAINT")) {
                	board.repaint();
                }
            }
            output.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
            frame.dispose();
        }
    }

	public static void main(String args[]) throws Exception{
		TrilmaClient client = new TrilmaClient();
        client.play();
	}
}
