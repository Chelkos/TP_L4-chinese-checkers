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
	public void addNewMove(int game_ID,int endX, int endY, int begX, int begY,Color color) {
		String SQL = "INSERT INTO movementlog(game_ID,endX,endY,begX,begY,color) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL, game_ID,endX,endY,begX,begY,color.toString());
		SQL = "UPDATE TABLE currentposition SET X = ?, Y = ? WHERE X = ? AND Y = ?";
		jdbcTemplateObject.update(SQL,endX,endY,begX,begY);
	}
	
}
