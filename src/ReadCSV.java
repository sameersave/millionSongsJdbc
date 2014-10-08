/**
 * 
 */

/**
 * @author Sameer
 *
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.sql.Connection;

public class ReadCSV {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ReadCSV myObj = new ReadCSV();
		myObj.run();

	}

//	@SuppressWarnings("null")
	public void run() {
		
		JDBC_connect obj = new JDBC_connect();
		Connection conn = JDBC_connect.connect();
		String csvFile = "";
		String dir = "/Users/Sameer/Documents/Academics/Fall 2014/CMPE 226/Lab/fire/data/";
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		boolean flag = false;
		
		try {
			File f1 = new File(dir);
			if (f1.isDirectory()) {
				String file_list[] = f1.list();
			
				for(int j=0; j<file_list.length; j++)	{
					csvFile = dir + file_list[j]; 						//create list of all files in the dir
					System.out.println(file_list[j]);
					
					br = new BufferedReader(new FileReader(csvFile)); 	//read each file as csv
					int count = 0;
					while ((line = br.readLine()) != null) {
						count++;
						if (count == 1)
							continue;
						String[] data = line.split(cvsSplitBy);			// use comma as separator
						
						if (data.length < 10) {
								break;
						 }else if (data[0].isEmpty()|| data[1].isEmpty()|| data[2].isEmpty()
								 || data[3].isEmpty()|| data[4].isEmpty()
								|| data[5].isEmpty() || data[9].isEmpty()){
							flag = true;
						} 
						else if (flag == false)
							obj.insert_data(conn, data);
					}
						
				}
						//System.out.println();
			}
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
			
	}
}
