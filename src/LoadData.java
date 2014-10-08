/**
 * 
 */

import resources.ParseFile;

/**
 * @author neerajsharma
 *
 */
public class LoadData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ParseFile dataFile = new ParseFile();
		dataFile.parse("/Users/neerajsharma/Downloads/amazonData/Arts.txt.gz");
	}

}
