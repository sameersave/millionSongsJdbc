package resources;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * This class will help application read any JSON format config file.
 */

/**
 * @author neerajsharma
 *
 */

public class ReadConfig {
	private String filePath;
	private JSONObject jsonObject;
	protected static Logger logger = LoggerFactory.getLogger(resources.ReadConfig.class);
	
	//constructor
	public ReadConfig(String filePath) {
		try {
			//read the json file
			FileReader fileReader = new FileReader(filePath);
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
			
			//set the object
			this.jsonObject = jsonObject;
			
		} catch (FileNotFoundException e) {
			logger.error("Config file not found: ", filePath);
		} catch (IOException e) {
			logger.error("Cannot read file: ", filePath);
		} catch (ParseException e) {
			logger.error("Cannot parse JSON file: ", filePath);
		} catch (NullPointerException e) {
			logger.error("Null pointer exception.");
		}
		
	}
	
	public int getNumOfObjects() {
		return this.jsonObject.size();
	}
}
