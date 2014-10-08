import java.sql.Connection;
import java.util.HashMap;
import java.util.Set;

import org.json.simple.JSONObject;

import resources.JDBCConnection;
import resources.Log;
import resources.ReadConfig;

/**
 * 
 */

/**
 * @author neerajsharma
 *
 */
public class CreateSchema {

	/**
	 * Create tables based on schema config
	 */
	public CreateSchema() {
		// TODO Auto-generated constructor stub
	}
	
	public void run(String filePath) {
		//file path is schema.conf file path
		
		//Read schema config from schema config file
		ReadConfig schemaConfig = new ReadConfig(filePath);
		JSONObject schema = schemaConfig.get();
		
		//get database connection
		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection dbConnection = jdbcConnection.getConnection();		
		
		HashMap<String, String> tableHash = new HashMap<String, String>();
		Set<String> schemaKeys = schema.keySet();
		for (String key: schemaKeys) {
			//create table for each schema give in conf file
			JSONObject table = schemaConfig.get(key);
			Set<String> tableKeys = table.keySet();
			//one tablehash has one table columns
			for (String tableKey: tableKeys) {
				tableHash.put(tableKey, schemaConfig.get(table, tableKey));
			}
			//create schema for this table
			Log.logger.info("Creating table: " + key);
			jdbcConnection.createTable(tableHash);
			//reset hash
			tableHash = new HashMap<String, String>();
		}		
	}

}
