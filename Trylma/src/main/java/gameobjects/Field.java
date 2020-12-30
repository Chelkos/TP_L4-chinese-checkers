package gameobjects;

import java.awt.geom.Ellipse2D;

/**
 * Abstract field on board, subtypes: inner field, base field.
 * Possibly visited object with peg as visitor (Visitor pattern).
 * @see InnerField, BaseField
 */
public  class Field extends Ellipse2D.Float{
		private Peg visitor;
		
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
