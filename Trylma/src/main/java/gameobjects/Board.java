package gameobjects;
import javax.swing.*;

/**
 * Hexagram-shaped board with inner hexagon 6 "bases" 
 *
 */
public class Board extends JPanel{
	public Field fields[][];
	public Board(){
		this.fields = new Field[17][13];
	}


}
