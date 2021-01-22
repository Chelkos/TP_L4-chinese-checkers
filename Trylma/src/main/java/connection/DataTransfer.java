package connection;
import java.awt.Color;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class DataTransfer {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	 public void setDataSource(DataSource dataSource) {
	      this.dataSource = dataSource;
	      this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	   }
	public void addNewMove(int game_ID,int peg_ID, int X, int Y,Color color) {
		String SQL = "INSERT INTO movementlog(game_ID,peg_ID,X,Y,color) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL, game_ID,peg_ID,X,Y,color.toString());
		SQL = "DELETE FROM lastmovementlog WHERE peg_ID = ?";
		jdbcTemplateObject.update(SQL,peg_ID);
		SQL = "INSERT INTO lastmovementlog(game_ID,peg_ID,X,Y,color) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL, game_ID,peg_ID,X,Y,color.toString());
	}
	
}
