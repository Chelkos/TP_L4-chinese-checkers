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
    private int tabx,taby;
    private int NoPlayers;
	public Board(int NoPlayers){
		this.NoPlayers=NoPlayers;
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
        for(int k = 0;k<20;k++)
      	  for(int i = 0 ;i<20;i++)
      		fields[k][i]=null;
        drawBoardBorders(height);
        
       drawFieldsBases();
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
	
	public void drawBoardBorders(int height)
	{
        David_Star.add(new Polygon(new int[] {p1.x, p2.x,p3.x }, new int[] {p1.y, p2.y, p3.y}, 3));
        David_Star.add(new Polygon(new int[] {p4.x, p5.x, p6.x}, new int[] {p4.y, p5.y, p6.y}, 3));
	}
	public void drawFieldsBases() {
        drawFieldsBase1();
        drawFieldsBase2();
        drawFieldsBase3();
        drawFieldsBase4();
        drawFieldsBase5();
        drawFieldsBase6();
	}
	public void drawFieldsBase1(){
		p5.y+=r*1.3;
		p5.x-=0.5*r;
		for(int k = 0;k<4;k++) {
			for(int i=4;i<k+5;i++) {
				fields[k][i] = new BaseField(p5.x,p5.y,r,Color.white);  			
				if(NoPlayers== 2 || NoPlayers == 4|| NoPlayers == 6)
					fields[k][i].visitor = new Peg(Color.blue);	
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
      			if(NoPlayers== 3 || NoPlayers == 4|| NoPlayers == 6)
					fields[k][i].visitor = new Peg(Color.yellow);	
      			 p1.x += (height*0.045);
      		 }
      		 p1.y+=r*(height*0.0014375);
      		 p1.x-= (height*0.045*(7-k+0.5));
      	 }
	}
	public void drawFieldsBase3() {
		p3.y+=(height*0.005);
		p3.x-=(height*0.022)+r;
		for(int k = 4;k<8;k++){
			for(int i = 12; i>4+k;i--) {
				fields[k][i] = new BaseField(p3.x,p3.y,r,Color.black);
				if(NoPlayers== 3 ||  NoPlayers == 6)
					fields[k][i].visitor = new Peg(Color.magenta);
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
				if( NoPlayers == 6)
					fields[k][i].visitor = new Peg(Color.green);
				p4.x += (height*0.045);
			}
			p4.y-=r*(height*0.0014375);
			p4.x-=height*0.045*(k-8.5);
   	 	}
	}
	public void drawFieldsBase5() {
		p6.y-=r*4.5;
		p6.x-=3.5*r;
		for(int k = 9;k<13;k++) {
			for(int i=13;i<14+(k-1)%4;i++) {
			
				fields[k][i] = new BaseField(p6.x,p6.y,r,Color.blue);  		
				if(NoPlayers== 4 ||  NoPlayers == 6)
					fields[k][i].visitor = new Peg(Color.cyan);
				p6.x-= (height*0.045);
			}
			p6.y+=r*(height*0.0014375);
			p6.x+=height*0.045*(k-7.5);
   	 	}
	}
	public void drawFieldsBase6() {
		p2.y-=2.4*r;
		p2.x-=0.5*r;
		for(int k = 16;k>=13;k--) {
			for(int i=12;i>8+k-13;i--) {
				fields[k][i] = new BaseField(p2.x,p2.y,r,Color.blue);  			
				fields[k][i].visitor = new Peg(Color.red);
				p2.x-= (height*0.045);
			}
			p2.y-=r*(height*0.0014375);
			p2.x-=height*0.045*(k-17.5);
			
   	 	}
	}
	public void drawInnerField1() {
		p1.y-=r*5.7;
		p1.x+=r*2.4;
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
    			 g2.setColor(Color.black);
    			 }
    		
    			 g2.draw(fields[k][i]);
    			 g2.setColor(Color.black);
    		 
    		 }
    	 }
    }

}
