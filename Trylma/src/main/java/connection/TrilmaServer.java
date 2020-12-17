package connection;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import exceptions.CreatingPlayerException;

import java.awt.Color;

/*
 * A server class for Trilma, creates game, establishes connection through socket, 
 * adds players to game.
 * 
 * Communicates with client, using plain text commands.
 */
public class TrilmaServer {
	public static void main(String args[]) throws Exception{
		int n;
		String[] defaultPlayerName={ "Red", "Blue", "Green", "Yellow", "Cyan", "Magenta" };
		Color[] defaultPlayerColor={ Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN, Color.MAGENTA };
		try { n=Integer.parseInt(args[0]); }
		catch(NumberFormatException e) {
			System.out.println("Wrong argument!");
			return;
		}
		if(n!=2 || n!=4 || n!=5 || n!=6) {
			System.out.println("Wrong number of players!");
			return;
		}
		try (ServerSocket listener = new ServerSocket(58901)) {
            System.out.println("Tic Tac Toe Server is Running...");
            ExecutorService pool = Executors.newFixedThreadPool(200);
            while (true) {
                Game game = new Game(n);
                for(int i=0; i<n; i++) {
                	try {
                	Game.Player player=game.addPlayer(listener.accept(), defaultPlayerName[i], defaultPlayerColor[i]);
                	pool.execute(player);
                	} catch(CreatingPlayerException e) {
                		System.out.println(e.getMessage());
                		i--;
                	}
                }
                game.randomizePlayer();
            }
        }
	}
}
