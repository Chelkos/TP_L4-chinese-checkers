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
		this.in_base=false;
	}
	public Color getOwnerColor(){
		return this.ownerColor;
	}
	public void setOwnerColor(Color newColor){
		this.ownerColor=newColor;
	}
	public boolean inBase(){
		return in_base; //TO BE DONE
	}
	public void visit(BaseField field){
		if(field.getOwnerColor()==this.ownerColor)
		this.in_base=true;
	}
	public void visit(InnerField field){
		return;
	}
}


