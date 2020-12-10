package gameobjects;

/**
 * Field of certain player's winning area, if entered by their peg, move is possible only inside the area.
 *
 */
public class BaseField extends Field {
	public BaseField(){
	
	}

	@Override
	public boolean accept(Peg peg) {
		return false;
	}
}
