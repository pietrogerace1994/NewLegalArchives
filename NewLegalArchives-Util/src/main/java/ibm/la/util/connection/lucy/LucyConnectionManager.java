package ibm.la.util.connection.lucy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manager class of Lucy DB tables
 * @author MASSIMO CARUSO
 */
public class LucyConnectionManager {
	
	Connection connection = null;
	Statement statement   = null;
	
	/**
	 * Constructor
	 * @author MASSIMO CARUSO
	 */
	public LucyConnectionManager() {

	}
	
	/**
	 * Start the connection to Lucy Database.
	 * A new statement is created.
	 * @author MASSIMO CARUSO
	 */
	public void startConnection() {
		try {	
			System.out.println("Connecting to Lucy database...");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@//172.27.0.43:1521/LPLUCY","LUCYSRG","lucysrg");
			statement = connection.createStatement();
			System.out.println("Connection to Lucy database estabilished");
		}catch(SQLException e) {
			System.err.println("Error while opening Lucy connection");
			e.printStackTrace();
		}
	}

	/**
	 * Stop the connection to Lucy Database
	 * @author MASSIMO CARUSO
	 */
	public void stopConnection() {
		try {
			System.out.println("Closing Lucy database connection...");
			statement.close();
			connection.close();
			System.out.println("Connection closed");
		} catch (SQLException e) {
			System.err.println("Error while closing Lucy connection");
			e.printStackTrace();
		}
	}

	/**
	 * Execute the passed query
	 * @author MASSIMO CARUSO
	 * @param query the query to execute
	 * @return the result of query or null if an exception occurs.
	 */
	public ResultSet executeQuery(String query) {
		ResultSet query_result = null;
		try {
			query_result = statement.executeQuery(query);
		} catch (SQLException e) {
			System.err.println("Error during execution of: "+query);
			e.printStackTrace();
		}
		return query_result;
	}
	
}
