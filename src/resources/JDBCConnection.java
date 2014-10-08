/**
 * Provides one JDBC connection to the database
 */
package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author neerajsharma, sameersave
 *
 */
public class JDBCConnection {
	private Connection jdbcConnection;
	
	public Connection getConnection() {
		
		//check for postgresql drivers
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			Log.logger.error("Postgresql driver not loaded.");
		}
		
		//try to connect to databases
		//TODO: Read he postgresql config from server.conf using ReadConfig class
		//jdbcConnection = null;
		try {
			this.jdbcConnection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "data");
			this.jdbcConnection.setAutoCommit(false);
		} catch (SQLException e) {
			Log.logger.error("Unable to load connection to database.");
		}
		return this.jdbcConnection;
	}
	
	public void commit() {
		try {
			this.jdbcConnection.commit();
		} catch (SQLException e) {
			Log.logger.error("Unable to commit to database.");
		}
	}
	
	public void insert(String[] data) {
		try {
			String productId = data[1];
			
			Statement preparedStatement;
			preparedStatement = this.jdbcConnection.createStatement();		
			String sql = "INSERT into amazon (product_id) values ('" + productId + "');";
			Log.logger.info(sql);
			preparedStatement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.logger.error("Unable to insert to database");
		} catch (NullPointerException e) {
			e.getCause();
			Log.logger.error("Got null pointer exception.");
		}
	}
}
