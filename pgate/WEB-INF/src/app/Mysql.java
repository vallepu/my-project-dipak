package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Mysql{

	private Connection conn = null;

	public Mysql() {
		Config.setAllConfig();

		String hostName = Config.get("DB_HOST") + ":" + Config.get("DB_PORT");
		String userName = Config.get("DB_USER");
		String password = Config.get("DB_PASSWORD");
		String dbName = Config.get("DB_NAME");

		String url = "jdbc:mysql://"
				+ hostName
				+ "/"
				+ dbName
				+ "?noDatetimeStringSync=true&zeroDateTimeBehavior=convertToNull";

		/*try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.conn = java.sql.DriverManager.getConnection(url, userName, password);
			
			System.out.println("\n***************\nDB Connection Created\n");
		} catch (ClassNotFoundException sqlE) {
			System.err.println("Cannot connect to database server: Class Not Found");
			System.err.println("Error message: " + sqlE.getMessage());

		} catch (Exception e) {

			System.err.println("Cannot connect to database server");
			System.err.println("Error message: " + e.getMessage());
			e.printStackTrace();
		}*/
		
		this.conn = ConnectionManager.getConnection(url, userName, password);
		System.out.println("Cnnection obj: " + this.conn.toString());
	}

	public ResultSet queryResult(String query) {

		Statement s = null;
		ResultSet rs = null;

		if (query.length() <= 0) {
			Log.logger.info("Query can not be Empty");
			return rs;
		}

		try {
			s = this.conn.createStatement();
			s.executeQuery(query); // issue invalid query
			rs = s.getResultSet();
			
			Log.logger.info("\n******************************************\n" + 
					query + 
					"\n********************************************\n\n");
			
		} catch (SQLException e) {
			Log.logger.warning("Error message: " + e.getMessage());
			Log.logger.warning("Error number: " + e.getErrorCode());
		}

		return rs;
	}

	public PreparedStatement queryUpdate(String query) {

		PreparedStatement s = null;

		if (query.length() <= 0) {
			Log.logger.info("Query can not be Empty");
			return s;
		}
		try {
			s = this.conn.prepareStatement(query);
			
			Log.logger.info("\n******************************************\n" + 
					query + 
					"\n********************************************\n\n");
			
		} catch (SQLException e) {
			Log.logger.warning("Error message: " + e.getMessage());
			Log.logger.warning("Error number: " + e.getErrorCode());
		}
		return s;
	}

	public ArrayList<Object> resultSetToArrayList(String query)
			throws SQLException {

		ArrayList<Object> results = null;
		Statement s = null;
		ResultSet rs = null;

		if (query.length() <= 0) {
			Log.logger.info("Query can not be Empty");
			return results;
		}

		try {
			s = this.conn.createStatement();
			s.executeQuery(query); // issue invalid query
			Log.logger.info("\n******************************************\n" + 
								query + 
								"\n********************************************\n\n");
			
			rs = s.getResultSet();
			results = new ArrayList<Object>();

			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();

			while (rs.next()) {

				HashMap<String, Object> row = new HashMap<String, Object>();
				results.add(row);
				for (int i = 1; i <= columns; i++) {
					row.put(md.getColumnName(i), rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			Log.logger.warning("\nError\n******************************************" + 
					query + 
					"****************************************************\n\n");
			Log.logger.warning("Error message: " + e.getMessage());
			Log.logger.warning("Error number: " + e.getErrorCode());
		} finally {
			if(!rs.isClosed()) {
				rs.close();
			}
			if(!s.isClosed()){
				s.close();	
			}
			
		}
		return results;
	}

	public static void close() {
		try {
			if(ConnectionManager.isConnected()) {
				ConnectionManager.closeConnection();
			}
		} catch (Exception e) {
			Log.logger.warning("Can not disconnect!");
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		Mysql.close();
	}
	
}
