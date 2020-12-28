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



public class Board extends JPanel{
	
	public Field fields[][];
	private ArrayList<Shape> David_Star;
	private int height;
    private Point p1,p2,p3,p4,p5,p6;
    private int r;
	public Board(){
		height = 800;
		this.fields = new Field[20][20];
		this.David_Star = new ArrayList<Shape>();
		r = (int)(height *0.036);
        p1 = new Point((int)(height*0.1666666),(int)(height*0.294));
        p2 = new Point((int)(height/2),(int)(height*0.88444444));
        p3 = new Point((int)(height*0.83333333),(int)(height*0.294));
        p4 = new Point((int)(height*0.1666666),(int)(height*0.6622222));
        p5 = new Point((int)(height/2),(int)(height*0.085555555));
        p6 = new Point((int)(height*0.83333333),(int)(height*0.6622222));
        System.out.println(p3.x-p1.x);
        for(int k = 0;k<20;k++)
      	  for(int i = 0 ;i<20;i++)
      		fields[k][i]=null;
        drawBoardBorders(height);
        
       drawFields();
       drawInnerField1();
       drawInnerField2();
        setSize(height,height);
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.WHITE);
     //   setLocationRelativeTo(null);
        setVisible(true);
        for(int k=0;k<20;k++)
        {
        	for(int i=0;i<20;i++)
        	{
        		if(fields[k][i]==null)
        			System.out.print("X ");
        		else if(fields[k][i].getClass()==BaseField.class)
        			System.out.print("Pb");
        		else
        			System.out.print("Pi");
        		
        	}
        	System.out.println("");
        }
	}
	public void fillBoard(int noPlayers)
	{
		int n = noPlayers;
		if(n==2) {
			for(int i=0; i<=3; i++) {  
				for(int j=0; j<=i; j++) { 
					fields[13+j][9+i].accept(new Peg(Color.blue)); 
					fields[3-j][7-i].accept(new Peg(Color.red));
				}
			}
		} else if(n==3) {
			for(int i=0; i<=3; i++) {
				for(int j=0; j<=i; j++) {
					fields[9+i][4+j].accept(new Peg(Color.red)); 
					fields[9+i][13+j].accept(new Peg(Color.blue)); 
					fields[i][4+j].accept(new Peg(Color.green)); 
				}
			}
		} else if(n==4) {
			for(int i=0; i<=3; i++) { 
				for(int j=0; j<=i; j++) {
					fields[9+i][4+j].accept(new Peg(Color.red)); 
					fields[7-i][12-j].accept(new Peg(Color.blue));
					fields[16-i][12-j].accept(new Peg(Color.green));
					fields[i][4+j].accept(new Peg(Color.yellow));
				}
			}
		} else if(n==6) {
			for(int i=0; i<=3; i++) { //setup all triangles 
				for(int j=0; j<=i; j++) {
					fields[7-i][3-j].accept(new Peg(Color.red)); 
					fields[9+i][4+j].accept(new Peg(Color.blue));
					fields[16-i][12-j].accept(new Peg(Color.green));
					fields[9+i][13+j].accept(new Peg(Color.yellow));
					fields[7-i][12-j].accept(new Peg(Color.cyan));
					fields[i][4+j].accept(new Peg(Color.MAGENTA));
				}
			}
		}
		repaint();
	}
	public void drawBoardBorders(int height)
	{
        David_Star.add(new Polygon(new int[] {p1.x, p2.x,p3.x }, new int[] {p1.y, p2.y, p3.y}, 3));
        David_Star.add(new Polygon(new int[] {p4.x, p5.x, p6.x}, new int[] {p4.y, p5.y, p6.y}, 3));
	}
	public void drawFields() {
		int tHeight=160;
		int tWidth=92;
		p6.y-=r*4.5;
		p6.x-=3.5*r;
		p5.y+=r*1.3;
		p5.x-=0.5*r;
		p2.y-=2.4*r;
		p2.x-=0.5*r;
		p1.y+=tHeight-(int)(2*r);
		p1.x+=tWidth-(int)(0.8*r);
		
		p3.y+=tHeight-(int)(2*r);
		p3.x-=tWidth+(int)(0.2*r);
		p4.x=p1.x;
		p4.y=p1.y+(int)(2.2*r);
		
		for(int k = 0;k<4;k++) {
			for(int i=4;i<k+5;i++) {
				fields[k][i] = new BaseField(p5.x,p5.y,r,Color.black);  			
				p5.x += (height*0.045);
				fields[16-k][16-i] = new BaseField(p2.x,p2.y,r,Color.black);  				
				p2.x-= (height*0.045);
				fields[7-k][i-k-1] = new BaseField(p1.x,p1.y,r,Color.black);
     			p1.x += (height*0.045);
     			fields[k+9][i] = new BaseField(p3.x,p3.y,r,Color.black);	
    			p3.x += (height*0.045);
				fields[k+9][i+9] = new BaseField(p4.x,p4.y,r,Color.black);  			
				p4.x += (height*0.045);
				fields[7-k][i+8-k] = new BaseField(p6.x,p6.y,r,Color.black);  		
				p6.x-= (height*0.045);
			}
			p6.y+=r*(height*0.0014375);
			p6.x+=height*0.045*(k+1.5);
			p5.y+=r*(height*0.0014375);
			p5.x-=(height*0.045)*(k+1.5);
			p4.y+=r*(height*0.0014375);
			p4.x-=height*0.045*(k+1.5);
			p3.y-=r*(height*0.0014375);
      		p3.x-= (height*0.045)*(k+1.5);
			p2.y-=r*(height*0.0014375);
			p2.x+=(height*0.045)*(k+1.5);
     		p1.y-=r*(height*0.0014375);
     		p1.x-= (height*0.045)*(k+1.5);

   	 	}
	}
	public void drawInnerField1() {
		p1.y+=r;
		p1.x+=6.3*r;
		for(int k = 4;k<9;k++)
		{
			for(int i=4;i<5+k;i++) {
				fields[k][i] = new InnerField(p1.x,p1.y,r);  		
				 p1.x += (height*0.05);
			}
			p1.y+=r*1.2;
			 p1.x -= (height*0.05*(k+1.5));
		}
	}
	public void drawInnerField2() {
		p1.x+=r*1.4;

		for(int k=9;k<13;k++)
		{
			for(int i=5+k-9;i<13;i++)
			{
				fields[k][i] = new InnerField(p1.x,p1.y,r);  
				p1.x+= (height*0.05);
				
			}
			p1.y+=r*1.2;
			 p1.x -= (height*0.05*(16.5-k));
		}
	}
	public void paint(Graphics g){
   	 Graphics2D g2 = (Graphics2D) g;
   	 g2.setPaint(Color.white);
   	 g2.drawRect(1200, 1200, 1200, 1200);
   	 g2.fillRect(1200, 1200, 1200, 1200);
   	 g2.setPaint(Color.black);
   	 g2.setStroke(new BasicStroke(5));
     g2.draw(this.David_Star.get(0));
     g2.draw(this.David_Star.get(1));
     for(int k=0; k<20;k++)
    	 for(int i=0;i<20;i++)
    	 {
    		 
    		 if(fields[k][i]!=null)
    		 {
    			 if(fields[k][i].visitor!=null)
    			 {
    			 g2.setColor(fields[k][i].visitor.getOwnerColor());		
    			 g2.fill(fields[k][i]);
    			 }
    			 else
    			 {
    			 g2.setColor(Color.white);
    			 g2.fill(fields[k][i]);
    			 g2.setColor(Color.black);
    			 g2.draw(fields[k][i]);
    			 }
    		
    			 g2.draw(fields[k][i]);
    			 g2.setColor(Color.black);
    		 
    		 }
    		 
    	 }
    }

}
