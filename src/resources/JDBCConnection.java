/**
 * Provides one JDBC connection to the database
 */
package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import resources.Log;

/**
 * @author neerajsharma
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
		jdbcConnection = null;
		try {
			jdbcConnection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "data");
			jdbcConnection.setAutoCommit(false);
		} catch (SQLException e) {
			Log.logger.error("Unable to load connection to database.");
		}
		return jdbcConnection;
	}
}
