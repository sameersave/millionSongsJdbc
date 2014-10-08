/*
 * Class to parse one GZ file without unzipping
 */

package resources;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.util.zip.GZIPInputStream;

/**
 * @author neerajsharma
 *
 */

public class ParseFile {
	
	public void parse(String filePath) {
		InputStream fileStream;
		String line = null;
		int rowSeparator = 0;
		int rowNumber = 1;
		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection dbConnection = jdbcConnection.getConnection();
		
		try {
			//read the gzip file directly, to avoid decompression
			fileStream = new FileInputStream(filePath);
			InputStream gzipStream = new GZIPInputStream(fileStream);
			Reader decoder = new InputStreamReader(gzipStream, "UTF-8");
			BufferedReader buffered = new BufferedReader(decoder);
			
			while ((line = buffered.readLine()) != null) {
				//because first 10 lines represent one row, 11th line is separator for next row
				if(rowSeparator == 11){
					rowSeparator = 0;
					break; //break at one row, temporarily
				} else {
					//Log.logger.info(Integer.toString(i));
					String[] parts = line.split(":");
					if (parts.length > 1) {
						Log.logger.info(parts[1].toString());
						jdbcConnection.insert(parts);
					}
					
					rowSeparator++;
				}
				rowNumber++;
			}
			
		} catch (FileNotFoundException e) {
			Log.logger.error("File not found: ", filePath);
		} catch (IOException e) {
			Log.logger.error("Unable to read file: ", filePath);
		} finally {
			jdbcConnection.commit();
		}

	}

}
