package connection;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import connection.Game.Player;
import gameobjects.Board;
import gameobjects.Peg;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;

//@Aspect
public class DataTransfer {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    private List<String> movementList;
    private String colorname;
    public void setDataSource(DataSource dataSource){
          this.dataSource = dataSource;
          this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
    public int getGameID()
    {	int game_ID;
    	String SQL = "SELECT MAX(game_ID) FROM movementlog;";
    	try {
    	game_ID = jdbcTemplateObject.queryForObject(SQL,new Object[] {},Integer.class);
    	game_ID++;
    	}
    	catch(NullPointerException e)
    	{
    		game_ID=0;
    	}
    	return game_ID;
    }
 //   @AfterReturning("connection.Game.DefaultTrilmaInterface.move() && args(begI, begJ, endI, endJ, player)")
    public void addMovementToList(int begI, int begJ, int endI, int endJ, Player player) {
    	if(movementList==null)
    		movementList=new ArrayList<String>();
    	String movement=player.gameID + " " + player.name + " " + endI + " " + endJ + " " + begI + " " + begJ;
    	movementList.add(movement);
    	System.out.println(movement);
    }
//    @AfterReturning("connection.Game.DefaultTrilmaInterface.setup() && args(gameID,board)")
    public void setupStartingPegPosition(int game_ID,Peg[][] board){
    	
    	for(int i=0; i<=16;i++)
    		for(int j=0; j<=16;j++)
    		{
    			if(board[i][j]!=null)
    			{
    		        String SQL = "INSERT INTO currentposition(game_ID,X,Y,color) VALUES (?, ?, ?, ?) ;";
    		        if(board[i][j].getOwnerColor()==Color.BLUE)
    		        	colorname="BLUE";
    		        if(board[i][j].getOwnerColor()==Color.RED)
    		        	colorname="RED";
    		        if(board[i][j].getOwnerColor()==Color.GREEN)
    		        	colorname="GREEN";
    		        if(board[i][j].getOwnerColor()==Color.YELLOW)
    		        	colorname="YELLOW";
    		        if(board[i][j].getOwnerColor()==Color.CYAN)
    		        	colorname="CYAN";
    		        if(board[i][j].getOwnerColor()==Color.MAGENTA)
    		        	colorname="MAGENTA";
    		         jdbcTemplateObject.update(SQL,game_ID,i,j,colorname);
    				
    			}
    		}

    }

    public void addNewMove(int game_ID,int endX, int endY, int begX, int begY,Color color){
    	  if(color==Color.BLUE)
	        	colorname="BLUE";
	        if(color==Color.RED)
	        	colorname="RED";
	        if(color==Color.GREEN)
	        	colorname="GREEN";
	        if(color==Color.YELLOW)
	        	colorname="YELLOW";
	        if(color==Color.CYAN)
	        	colorname="CYAN";
	        if(color==Color.MAGENTA)
	        	colorname="MAGENTA";
        String SQL = "INSERT INTO movementlog(game_ID,endX,endY,begX,begY,color) VALUES (?, ?, ?, ?, ?, ?) ;";
        jdbcTemplateObject.update(SQL, game_ID,endX,endY,begX,begY,colorname);
        SQL = "UPDATE currentposition SET X = ?, Y = ? WHERE X = ? AND Y = ? ;";
        jdbcTemplateObject.update(SQL,endX,endY,begX,begY);
    }

}
