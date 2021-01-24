package connection;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gameobjects.*;
/**
 * 
 *Trilma Client initializes a Frame for the Client, provides visuals and establishes connection with a server
 *
 */

public class TrilmaClient {
	private JFrame frame;
	private JFrame options;
	private JFrame saveWindow;
	private JLabel messageLabel;
	private Board board;
	private Field selectedField=null; 
	private Field targetField=null;
	private Socket socket;
	private Scanner input;
	private PrintWriter output;
	private JButton endTurnButton;
	private JButton saveButton;
	private JButton loadButton;
	public TrilmaClient() throws Exception{
		frame = new JFrame();
		options = new JFrame();
		saveButton = new JButton("Save Game");
		loadButton = new JButton ("Load Game");
		endTurnButton = new JButton("End Turn");
		messageLabel = new JLabel("");
		socket=new Socket("127.0.0.1", 58901);
		input=new Scanner(socket.getInputStream());
		output=new PrintWriter(socket.getOutputStream(), true);
		messageLabel.setBackground(Color.LIGHT_GRAY);
		frame.setLayout(new BorderLayout());
		
		options.getContentPane().setLayout(new BoxLayout(options.getContentPane(), BoxLayout.Y_AXIS));
		options.setSize(200,200);
		endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		options.getContentPane().add(endTurnButton);
        options.getContentPane().add(loadButton);
		options.getContentPane().add(saveButton);
		endTurnButton.setSize(170, 60);
		saveButton.setSize(170, 60);
		loadButton.setSize(170, 60);
		//options.add(saveButton,BorderLayout.SOUTH);
		
        frame.setSize(1000, 1000);
        //options.add(endTurnButton,BorderLayout.NORTH);
        //options.add(loadButton,BorderLayout.EAST);
		frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        options.setVisible(true);
        options.setResizable(false);
        saveButton.addMouseListener(new MouseAdapter(){
        	public void mousePressed(MouseEvent e) {
        		output.println("SAVE_GAME");
        	}
        });
        loadButton.addMouseListener(new MouseAdapter(){
        	public void mousePressed(MouseEvent e) {
        		saveWindow=new JFrame("Load game");
        		saveWindow.setSize(250, 100);
        		saveWindow.setLayout(new FlowLayout(FlowLayout.LEFT));
        		JTextField textField=new JTextField(14);
        		textField.setSize(200, 50);
        		JButton acceptButton=new JButton("Accept");
        		acceptButton.addMouseListener(new MouseAdapter() {
        			public void mousePressed(MouseEvent e) {
        				int n=Integer.parseInt(textField.getText());
        				output.println("LOAD_GAME " + n);
        				saveWindow.dispose();
        			}
        		});
        		saveWindow.add(new JLabel("Enter ID of game to load: "));
        		saveWindow.add(textField);
        		saveWindow.add(acceptButton);
        		saveWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        		saveWindow.setVisible(true);
        		saveWindow.setResizable(false);
        	}
        });
        endTurnButton.addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent e) {
        		output.println("END_TURN");
        	}
        });
		
        board=new Board();
        frame.add(board,BorderLayout.CENTER);

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
    	for(int i=0; i<20;i++)
    		for(int j=0; j<20;j++)
    			if(board.fields[i][j]!=null)
    				board.fields[i][j].accept(null);
	}
	
	/**
	 * Sends and receives messages from the server 
	 * @throws Exception on connection fault
	 */
    public void play() throws Exception {
        try {
        	int noPlayers;
            String response = input.nextLine();
            String name=response.substring(7);
            response = input.nextLine();
            String gameID=response.substring(5);
            frame.setTitle("Game ID: " + gameID + ", player: " + name);
            options.setTitle(name);
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
                	String parameter; String color[]=new String[3];
                	parameter=response.substring(21);
                	r=Integer.parseInt(parameter.substring(parameter.indexOf("=")+1,parameter.indexOf(",")));
                	parameter=parameter.substring(parameter.indexOf(",")+1);
                	g=Integer.parseInt(parameter.substring(parameter.indexOf("=")+1,parameter.indexOf(",")));
                	parameter=parameter.substring(parameter.indexOf(",")+1);
                	b=Integer.parseInt(parameter.substring(parameter.indexOf("=")+1,parameter.indexOf("]")));
                	parameter=parameter.substring(parameter.indexOf("|")+1);
                	i=Integer.parseInt(parameter.substring(0,parameter.indexOf("|")));
                	parameter=parameter.substring(parameter.indexOf("|")+1);
                	j=Integer.parseInt(parameter.substring(0));
                
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
