import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
//import java.text.*;
//import java.util.Date;
	 

public class JDBC_connect {

	public static Connection connect() {

		try {
			Class.forName("org.postgresql.Driver");	
		}	
		catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
			e.printStackTrace();
			
			}
		System.out.println("PostgreSQL JDBC Driver Registered!");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/test", "postgres","admin");
			connection.setAutoCommit(false);
		}
		catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		return connection;

	}
		
	public void insert_data(Connection con_obj, String data[]) {
		
		try 
		{
			
				double latitude = Double.parseDouble(data[0]);
				double longitude = Double.parseDouble(data[1]);
				double area = Double.parseDouble(data[3]);
			
				//SimpleDateFormat old = new SimpleDateFormat("dd/MM/yyyy");
				//SimpleDateFormat new_format = new SimpleDateFormat("yyyy-mm-dd");
				String report_date = data[2];
				
				String fire_name = data[4].replace("'","");
				String fire_number = data[5];
				String gacc = data[9].toString();
			
				Statement stmt = null;
				stmt = con_obj.createStatement();
				//String sql_select = "SELECT * FROM fire";
				String sql_insert = "INSERT INTO fire(latitude, longitude, area, report_date, fire_name, fire_number, gacc) VALUES ("+latitude+","+longitude+","+ area+",to_date('"+ report_date+"', 'dd/MM/yyyy'), '"+ fire_name +"' , '"+ fire_number+"','"+ gacc+"')";
				stmt.executeUpdate(sql_insert);
				//stmt.close();
	
				
				(con_obj).commit();
			
		}
		
		catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	    }
	}
	
/*	public Date format_date(String dateInString) {
		SimpleDateFormat old = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat new_format = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		try {
			new_format.format(old.parse(dateInString));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	return date;
	}*/
}