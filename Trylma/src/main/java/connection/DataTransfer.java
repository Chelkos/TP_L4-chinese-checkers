package connection;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import connection.Game.Player;
import gameobjects.Peg;
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
    	game_ID = jdbcTemplateObject.queryForObject(SQL,Integer.class);
    	game_ID++;
    	}
    	catch(NullPointerException e)
    	{
    		game_ID=0;
    	}
    	return game_ID;
    }
   public void addMovementToList(int begI, int begJ, int endI, int endJ, Player player) {
    	if(movementList==null)
    		movementList=new ArrayList<String>();
    	String colorname="";
    	 if(player.color==Color.BLUE)
	        	colorname="BLUE";
	        if(player.color==Color.RED)
	        	colorname="RED";
	        if(player.color==Color.GREEN)
	        	colorname="GREEN";
	        if(player.color==Color.YELLOW)
	        	colorname="YELLOW";
	        if(player.color==Color.CYAN)
	        	colorname="CYAN";
	        if(player.color==Color.MAGENTA)
	        	colorname="MAGENTA";
    	String movement=player.gameID + "," + colorname + "," + endI + "," + endJ + "," + begI + "," + begJ;
    	movementList.add(movement);
    	System.out.println(movement);
    }
  public Peg[][] loadBoard(int gameID)
  {	
	  String SQL = "SELECT * FROM currentposition WHERE game_ID = ?;";
	  List<Data> movementLog ;
	  movementLog = new ArrayList<Data>();
	  movementLog = jdbcTemplateObject.query(SQL, new Mapper(),gameID) ;
	  Peg[][] board =new Peg[17][17];
	  for(int i=0; i<=16;i++)
  		for(int j=0; j<=16;j++)
  			board[i][j]=null;
	  for(Data d : movementLog)
	  {
		  board[d.X][d.Y]=new Peg(d.color);
		  
	  }
	  return board;

  }
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

    public void addNewMove(){
    	int begX=0;
    	int begY=0;
    	int endX=0;
    	int endY=0;
    	int game_ID=0;
    	String color="NONE";
    	String parsing[];
    	String SQL = "INSERT INTO movementlog(game_ID,endX,endY,begX,begY,color) VALUES (?, ?, ?, ?, ?, ?) ;";
    	 for(String s : movementList)
    	 {
    	
    		parsing=s.split(",");
    		game_ID=Integer.parseInt(parsing[0]);
    		colorname=parsing[1];
    		endX=Integer.parseInt(parsing[2]);
    		endY=Integer.parseInt(parsing[3]);
    		begX=Integer.parseInt(parsing[4]);
    		begY=Integer.parseInt(parsing[5]);
            jdbcTemplateObject.update(SQL, game_ID,endX,endY,begX,begY,colorname);
            SQL = "UPDATE currentposition SET X = ?, Y = ? WHERE X = ? AND Y = ? ;";
            jdbcTemplateObject.update(SQL,endX,endY,begX,begY);
   
    	 }
    	 movementList.clear();
    	
    }
    public class Data{
		int X=0;
    	int Y=0;
    	int game_ID=0;
    	Color color;  
	}
   private class Mapper implements RowMapper<Data>{

 @Override
	public Data mapRow(ResultSet rs, int rowNum) throws SQLException {
		Data data = new Data();
		data.game_ID=rs.getInt("game_ID");
		  if(rs.getString("color")=="BLUE")
			  data.color=Color.BLUE;
	        if(rs.getString("color")=="RED")
	        	data.color=Color.RED;
	        if(rs.getString("color")=="GREEN")
	        	data.color=Color.GREEN;
	        if(rs.getString("color")=="YELLOW")
	        	data.color=Color.YELLOW;
	        if(rs.getString("color")=="CYAN")
	        	data.color=Color.CYAN;
	        if(rs.getString("color")=="MAGENTA")
	        	data.color=Color.MAGENTA;
		
		data.X=rs.getInt("X");
		data.Y=rs.getInt("Y");
		return data;
		
		
	}
	   
	   
   }
   
   
}
