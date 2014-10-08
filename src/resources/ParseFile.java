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
import java.util.zip.GZIPInputStream;

import resources.Log;

public class ParseFile {
	
	public void parse(String filePath) {
		InputStream fileStream;
		try {
			//read the gzip file directly, to avoid decompression
			fileStream = new FileInputStream(filePath);
			InputStream gzipStream = new GZIPInputStream(fileStream);
			Reader decoder = new InputStreamReader(gzipStream, "UTF-8");
			BufferedReader buffered = new BufferedReader(decoder);
			
			String line = null;
			int i = 0;
			while((line = buffered.readLine()) != null){
				if(i==11){
					i = 0;
					break;
				} else {
					Log.logger.info(Integer.toString(i));
					String[] parts = line.split(":");
					for (String part: parts) {
						Log.logger.info(part);
					}
					i++;
				}
			}
		} catch (FileNotFoundException e) {
			Log.logger.error("File not found: ", filePath);
		} catch (IOException e) {
			Log.logger.error("Unable to read file: ", filePath);
		}
	}

}
