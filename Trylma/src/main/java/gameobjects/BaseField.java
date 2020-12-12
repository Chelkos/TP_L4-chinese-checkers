package gameobjects;

import java.awt.Color;

/**
 * Field of certain player's winning area, if entered by their peg, move is possible only inside the area.
 *
 */
public class BaseField extends Field {
	private Color ownerColor;
	
	public BaseField(int x, int y, int r, Color ownerColor){
		this.ownerColor=ownerColor;
	}

	@Override
	public boolean accept(Peg peg) {
		return false;
	}
	
	public Color getOwnerColor() {
		return this.ownerColor;
	}
}
