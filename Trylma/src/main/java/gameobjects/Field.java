package gameobjects;

import java.awt.geom.Ellipse2D;

/**
 * Abstract field on board, subtypes: inner field, base field.
 * Possibly visited object with peg as visitor (Visitor pattern).
 */
public class Field extends Ellipse2D.Float{
	private Peg visitor;
	/**
	 * Creates field on board with given coordinates and radius.
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param r radius
	 */
	public Field(int x, int y, int r) {
		this.visitor=null;
		this.x=x;
		this.y=y;
		this.height=r;
		this.width=r;
	}
		
	public void accept(Peg peg) {
		this.visitor=peg;
	}
		
	public Peg getVisitor(){
		return this.visitor;
	}
		
}
