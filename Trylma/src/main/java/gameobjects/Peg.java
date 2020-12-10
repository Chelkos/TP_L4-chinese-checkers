package gameobjects;

import java.awt.Color;

/**
 * Represents peg, has owner and color, possibly a visitor in Field (Visitor pattern)
 *
 */
public class Peg {
	private Player Owner;
	private Color color;
	private boolean in_base;
	public Peg(Player player){
		this.Owner=player;
	}
	public Player getOwner(){
		return this.Owner;
	}
	public Color getColor(Color color){
		return this.color;
	}
	public boolean InBase(){
		return true; //TO BE DONE
	}
	public void visit(Field field){
		return;
	}
}


