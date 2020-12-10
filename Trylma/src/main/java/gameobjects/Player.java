package gameobjects;
import java.awt.Color;
public class Player {
	String name;
	Color color;
	Player(String name,Color color){
		this.name=name;
		this.color=color;
	}
	public String getName(){
		return this.name;
	}
	public Color getcolor(){
		return this.color;
	}
}
