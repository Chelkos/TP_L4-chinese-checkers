package gameobjects;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D.Float;
/**
 * Hexagram-shaped board with inner hexagon 6 "bases" 
 *
 */



public class Board extends JFrame{
	public Field fields[][];
	public ArrayList<Field> Fields;
	public ArrayList<Shape> David_Star;
	public int height;
    public Point p1,p2,p3,p4,p5,p6;
    public int r;
    public int tabx,taby;
	public Board(){
		height = 800;
		this.fields = new Field[20][20];
		this.David_Star = new ArrayList<Shape>();
		this.Fields = new ArrayList<Field>();
		r = (int)(height *0.036);
        p1 = new Point((int)(height*0.1666666),(int)(height*0.294));
        p2 = new Point((int)(height/2),(int)(height*0.88444444));
        p3 = new Point((int)(height*0.83333333),(int)(height*0.294));
        p4 = new Point((int)(height*0.1666666),(int)(height*0.6622222));
        p5 = new Point((int)(height/2),(int)(height*0.085555555));
        p6 = new Point((int)(height*0.83333333),(int)(height*0.6622222));
        for(int k = 0;k<20;k++)
      	  for(int i = 0 ;i<20;i++)
      		fields[k][i]=null;
        drawBoardBorders(height);
        drawFieldsBase1();
        drawFieldsBase2();
        drawFieldsBase3();
        drawFieldsBase4();
        setSize(height,height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        setVisible(true);
        for(int k=0;k<20;k++)
        {
        	for(int i=0;i<20;i++)
        	{
        		if(fields[k][i]==null)
        			System.out.print("X ");
        		else
        			System.out.print("Pb");
        		
        	}
        	System.out.println("");
        }
	}
	
	public void drawBoardBorders(int height)
	{
        David_Star.add(new Polygon(new int[] {p1.x, p2.x,p3.x }, new int[] {p1.y, p2.y, p3.y}, 3));
        David_Star.add(new Polygon(new int[] {p4.x, p5.x, p6.x}, new int[] {p4.y, p5.y, p6.y}, 3));
	}
	public void drawFieldsBase1(){
		p5.y+=r*0.8;
		p5.x-=0.5*r;
		for(int k = 0;k<4;k++) {
			for(int i=4;i<k+5;i++) {
				fields[k][i] = new BaseField(p5.x,p5.y,r,Color.white);  			
				p5.x += (height*0.045);
			}
			p5.y+=r*(height*0.0014375);
			p5.x-=(k+1.5)*(height*0.045);
   	 	}

	}
	public void drawFieldsBase2() {
		p1.y+=(height*0.005);
		p1.x+=(height*0.022);
      	 for(int k = 4;k<9;k++)
      	 {
      		 for(int i=k-4;i<4;i++)
      		 {
      			fields[k][i] = new BaseField(p1.x,p1.y,r,Color.black);
      			 p1.x += (height*0.045);
      		 }
      		 p1.y+=r*(height*0.0014375);
      		 p1.x-= (height*0.045*(7-k+0.5));
      	 }
	}
	/*public void drawInnerField1()
	{
		
		 for(int k = 0;k<4;k++)
      	 {
      		 for(int i=5;i<9-k;i++)
      		 {
      			fields[k][i] = new BaseField(p1.x,p1.y,r,Color.black);
      			 p1.x += (height*0.045);
      		 }
      		 p1.y+=r*(height*0.0014375);
      		 p1.x-= (height*0.045*(3-k+0.5));
      	 }
		
		
		
	}*/
	public void drawFieldsBase3() {
		p3.y+=(height*0.005);
		p3.x-=(height*0.022)+r;
		for(int k = 4;k<8;k++){
			for(int i = 12; i>4+k;i--) {
				fields[k][i] = new BaseField(p3.x,p3.y,r,Color.black);
     			 p3.x -= (height*0.045);
			}
			 p3.y+=r*(height*0.0014375);
      		 p3.x+= (height*0.045*(7-k+0.5));
		}	
	}
	public void drawFieldsBase4() {
		p4.y-=r*1.1;
		p4.x+=0.5*r;
		for(int k = 12;k>8;k--) {
			for(int i=4;i<(k%8)+4;i++) {
				fields[k][i] = new BaseField(p4.x,p4.y,r,Color.blue);  			
				p4.x += (height*0.045);
			}
			p4.y-=r*(height*0.0014375);
			p4.x-=height*0.045*(k-8.5);
   	 	}
	}
	public void drawFieldsBase5() {
		
		
		
	}
	public void paint(Graphics g){
   	 Graphics2D g2 = (Graphics2D) g;
   	 g2.setStroke(new BasicStroke(5));
     g2.draw(this.David_Star.get(0));
     g2.draw(this.David_Star.get(1));
     for(int k=0; k<20;k++)
    	 for(int i=0;i<20;i++)
    	 {
    		 if(fields[k][i]!=null)
    		 g2.draw(fields[k][i]);
    	 }
    }

}
