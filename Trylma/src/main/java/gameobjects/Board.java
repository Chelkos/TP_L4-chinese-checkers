package gameobjects;
import javax.swing.*;
import java.awt.*;
public class Board extends JPanel{
	
	public Field fields[][];
	private Shape[] David_Star;
	private int height;
    
	public Board() {
		height = 800;
		this.fields = new Field[20][20];
		this.David_Star = new Shape[2];
		drawBorders(height);
		drawTopBottom();
		drawInterior();
        setSize(height,height);
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.WHITE);
     //   setLocationRelativeTo(null);
        setVisible(true);
	}
	
	public void fillBoard(int noPlayers) {
		int n = noPlayers;
		if(n==2) {
			for(int i=0; i<=3; i++) {  
				for(int j=0; j<=i; j++) {
					fields[3-j][7-i].accept(new Peg(Color.red));
					fields[13+j][9+i].accept(new Peg(Color.blue)); 
				}
			}
		} else if(n==3) {
			for(int i=0; i<=3; i++) {
				for(int j=0; j<=i; j++) {
					fields[4+j][9+i].accept(new Peg(Color.red)); 
					fields[13+j][9+i].accept(new Peg(Color.blue)); 
					fields[4+j][i].accept(new Peg(Color.green)); 
				}
			}
		} else if(n==4) {
			for(int i=0; i<=3; i++) { 
				for(int j=0; j<=i; j++) {
					fields[4+j][9+i].accept(new Peg(Color.red)); 
					fields[12-j][16-i].accept(new Peg(Color.blue));
					fields[12-j][7-i].accept(new Peg(Color.green));
					fields[4+j][i].accept(new Peg(Color.yellow));
				}
			}
		} else if(n==6) {
			for(int i=0; i<=3; i++) { //setup all triangles 
				for(int j=0; j<=i; j++) {
					fields[3-j][7-i].accept(new Peg(Color.red)); 
					fields[4+j][9+i].accept(new Peg(Color.blue));
					fields[12-j][16-i].accept(new Peg(Color.green));
					fields[13+j][9+i].accept(new Peg(Color.yellow));
					fields[12-j][7-i].accept(new Peg(Color.cyan));
					fields[4+j][i].accept(new Peg(Color.MAGENTA));
				}
			}
		}
		repaint();
	}
	
	public void drawBorders(int height) {
		Point[] p=new Point[6];
		p[0] = new Point((int)(height*0.1666666),(int)(height*0.294));
        p[1] = new Point((int)(height/2),(int)(height*0.88444444));
        p[2] = new Point((int)(height*0.83333333),(int)(height*0.294));
        p[3] = new Point((int)(height*0.1666666),(int)(height*0.6622222));
        p[4] = new Point((int)(height/2),(int)(height*0.085555555));
        p[5] = new Point((int)(height*0.83333333),(int)(height*0.6622222));
        David_Star[0]=(new Polygon(new int[] {p[0].x, p[1].x, p[2].x}, new int[] {p[0].y, p[1].y, p[2].y}, 3));
        David_Star[1]=(new Polygon(new int[] {p[3].x, p[4].x, p[5].x}, new int[] {p[3].y, p[4].y, p[5].y}, 3));
	}
	
	public void drawTopBottom() {
		int topX=386, topY=104, botX=386, botY=639, r=28;
		int horizontal=36;
		int vertical=32;
		int rowDisplacement=18;
		for(int i=0; i<=3; i++) {
			topX=386-i*rowDisplacement;
			botX=386+i*rowDisplacement;
			for(int j=0; j<=i; j++) {
				fields[i][4+j]=new Field(topX, topY, r);
				fields[16-i][12-j]=new Field(botX, botY, r);
				topX+=horizontal;
				botX-=horizontal;
			}
			topY+=vertical;
			botY-=vertical;
		}
	}
	
	public void drawInterior( ) {
		int x=150, y=240, r=28;
		int horizontal=36;
		int vertical=32;
		int rowDisplacement=18;
		int w=(int)(4.6*horizontal);
		for(int i=0; i<=4; i++) {
			x=150+i*rowDisplacement;
			if(i==4) {
				x+=8;
				w+=0.5*rowDisplacement;
			}
			for(int j=i; j<=12; j++) {
				fields[4+i][j]=new Field(x, y, r);
				if(j>3 && j-i<8)
					x+=(w/(i+4));
				else
					x+=horizontal;
				if(j==3 || j-i==8)
					x+=10;
			}
			y+=vertical;
			w+=2*rowDisplacement;
		}
		w=(int)(4.6*horizontal)+6*rowDisplacement;
		for(int i=0; i<=3; i++) {
			x=150+(3-i)*rowDisplacement;
			for(int j=4; j<=13+i; j++) {
				fields[9+i][j]=new Field(x, y, r);
				if(j-i>4 && j<12)
					x+=(w/(7-i));
				else
					x+=horizontal;
				if(j-i==4 || j==12)
					x+=10;
			}
			y+=vertical;
			w-=2*rowDisplacement;
		}
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
	   	g2.setPaint(Color.white);
	    g2.drawRect(1200, 1200, 1200, 1200);
	  	g2.fillRect(1200, 1200, 1200, 1200);
	   	g2.setPaint(Color.black);
	   	g2.setStroke(new BasicStroke(5));
	    g2.draw(this.David_Star[0]);
	    g2.draw(this.David_Star[1]);
	    for(int k=0; k<20;k++) {
	    	for(int i=0;i<20;i++) {
	    		if(fields[k][i]!=null) {
	    			if(fields[k][i].getVisitor()!=null) {
	    				g2.setColor(fields[k][i].getVisitor().getOwnerColor());		
	    				g2.fill(fields[k][i]);
	    			}
	    			else {
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
}