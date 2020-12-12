package gameobjects;

import connection.Game;
import java.awt.Color;

/**
 * Represents peg, has owner and color, possibly a visitor in Field (Visitor pattern)
 *
 */
public class Peg {
	private Color ownerColor;
	private boolean in_base;
	
	public Peg(Color ownerColor){
		this.ownerColor=ownerColor;
	}
	public Color getOwnerColor(){
		return this.ownerColor;
	}
	public boolean inBase(){
		return true; //TO BE DONE
	}
	public void visit(BaseField field){
		return;
	}
	public void visit(InnerField field){
		return;
	}
}


