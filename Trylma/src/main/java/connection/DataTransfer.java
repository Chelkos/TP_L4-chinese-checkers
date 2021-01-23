package connection;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import connection.Game.Player;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;

@Aspect
public class DataTransfer {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    private List<String> movementList;

    public void setDataSource(DataSource dataSource){
          this.dataSource = dataSource;
          this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
    
    @AfterReturning("connection.Game.DefaultTrilmaInterface.move() && args(begI, begJ, endI, endJ, player)")
    public void addMovementToList(int begI, int begJ, int endI, int endJ, Player player) {
    	if(movementList==null)
    		movementList=new ArrayList<String>();
    	String movement=player.gameID + " " + player.name + " " + endI + " " + endJ + " " + begI + " " + begJ;
    	movementList.add(movement);
    	System.out.println(movement);
    }
    
    public void setupStartingPegPosition(int game_ID,int X,int Y,Color color){
        String SQL = "INSERT INTO currentposition(game_ID,X,Y,color) VALUES (?, ?, ?, ?) ;";
        jdbcTemplateObject.update(SQL,game_ID,X,Y,color.toString());
    }
    
    public void addNewMove(int game_ID,int endX, int endY, int begX, int begY,Color color){
        String SQL = "INSERT INTO movementlog(game_ID,endX,endY,begX,begY,color) VALUES (?, ?, ?, ?, ?, ?) ;";
        jdbcTemplateObject.update(SQL, game_ID,endX,endY,begX,begY,color.toString());
        SQL = "UPDATE currentposition SET X = ?, Y = ? WHERE X = ? AND Y = ? ;";
        jdbcTemplateObject.update(SQL,endX,endY,begX,begY);
    }

}
