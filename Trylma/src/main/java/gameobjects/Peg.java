package gameobjects;

import connection.Game;
import java.awt.Color;

/**
 * Represents peg, has owner and color, possibly a visitor in Field (Visitor pattern)
 *
 */
public class Peg {
	private Color ownerColor;
	
	public Peg(Color ownerColor) {
		this.ownerColor=ownerColor;
	}
	
	public Color getOwnerColor() {
		return this.ownerColor;
	}

}


