package app;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

	private String url = null;
	private String userName = null;
	private String password = null;

	private static Connection activeConnection = null;

	public static Connection getConnection(String url, String userName,
			String password) {
		try{

			if (ConnectionManager.activeConnection == null || ConnectionManager.activeConnection.isClosed()) {
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					ConnectionManager.activeConnection = java.sql.DriverManager
							.getConnection(url, userName, password);
					
					Log.logger.info("\n*************************************\n" +
										"DB Connection Created\n" +
										"*************************************\n");
				} catch (ClassNotFoundException sqlE) {
	
					Log.logger.warning("Cannot connect to database server: Class Not Found");
					Log.logger.warning("Error message: " + sqlE.getMessage());
	
				} catch (Exception e) {
	
					Log.logger.warning("Cannot connect to database server");
					Log.logger.warning("Error message: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			ConnectionManager.getConnection( url, userName, password);
		}
		return ConnectionManager.activeConnection;
	}
	
	public static boolean isConnected() {
		
		boolean flag = false;
		try{
			flag = !ConnectionManager.activeConnection.isClosed();
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}
	
	public static void closeConnection(){
		try {
			
			 if(!ConnectionManager.activeConnection.isClosed()){
				 
				 ConnectionManager.activeConnection.close();
					
				 ConnectionManager.activeConnection = null;
				 
				 Log.logger.info("\n***************************************\n" +
							"DB Connection Closed\n" +
							"***************************************\n");
			 }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}