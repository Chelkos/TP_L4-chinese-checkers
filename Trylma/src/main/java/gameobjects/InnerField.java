package gameobjects;

/**
 * A field of the inner hexagon, moves are possible in every direction provided that target field is free
 *
 */
public class InnerField extends Field{
	
	public InnerField(int x, int y, int r){
		this.x=x;
		this.y=y;
		this.height=r;
		this.width=r;
	}

	@Override
	public boolean accept(Peg peg) {
		return true;
	}
}
