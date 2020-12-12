package gameobjects;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.*;
/**
 * Abstract field on board, subtypes: inner field, base field.
 * Possibly visited object with peg as visitor (Visitor pattern).
 * @see InnerField, BaseField
 */
public abstract class Field extends Ellipse2D.Float{
		protected Peg visitor;
		protected float radius;
		public abstract boolean accept(Peg peg);
		public Peg getVisitor(){
			return this.visitor;
		}
}
