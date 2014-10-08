/**
 * Provides one JDBC connection to the database
 */
package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.json.simple.JSONObject;

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
		
		//Read database config from config file
		ReadConfig config = new ReadConfig("config/server.conf");

		//read server and postgresql config
		JSONObject postgreSqlConfig = config.get("postgresql");
		JSONObject serverConfig = config.get("server");
		
		//set JSON keys that are being read from server.conf
		String pgsqlUrl = "url";
		String pgsqlUsername = "username";
		String pgsqlPassword = "password";
		String pgsqlAutoCommit = config.get(postgreSqlConfig, "autoCommit");
		boolean autoCommit = true;
		if (pgsqlAutoCommit.equalsIgnoreCase("false")) {
			autoCommit = false;
		} else if (pgsqlAutoCommit.equalsIgnoreCase("true")) {
			autoCommit = true;
		} else {
			Log.logger.warn("No autocommit configuration set, using default: true");
		}
		
		//try to connect to databases
		//TODO: Read he postgresql config from server.conf using ReadConfig class -> Done
		//jdbcConnection = null;
		try {
			this.jdbcConnection = DriverManager.getConnection(config.get(postgreSqlConfig, pgsqlUrl), config.get(postgreSqlConfig, pgsqlUsername), config.get(postgreSqlConfig, pgsqlPassword));
			this.jdbcConnection.setAutoCommit(autoCommit);
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
			//Log.logger.info(sql);
			preparedStatement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.logger.error("Unable to insert to database");
		} catch (NullPointerException e) {
			e.getCause();
			Log.logger.error("Got null pointer exception.");
		}
	}
	
	public void createTable(HashMap<String, String> tableHash) {
		
	}
}
