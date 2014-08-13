package app;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Masters {
	
	public static ArrayList<Object> clientLists() {

		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = null;

		try {
			String sql = "SELECT * FROM `client_master`" ;

			arrList = mysqlObj.resultSetToArrayList(sql);
		} catch (SQLException ex) {
			// err
			Log.logger.warning("Currency List Exception " + ex.toString());
		}
		return arrList;
	}
	
	public static ArrayList<Object> pgLists() {

		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = null;

		try {
			String sql = "SELECT * FROM `pg_master`" ;

			arrList = mysqlObj.resultSetToArrayList(sql);
		} catch (SQLException ex) {
			// err
			Log.logger.warning("Currency List Exception " + ex.toString());
		}
		return arrList;
	}
	
	public static ArrayList<Object> currencyLists() {

		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = null;

		try {
			String sql = "SELECT * FROM `currency_master`" ;

			arrList = mysqlObj.resultSetToArrayList(sql);
		} catch (SQLException ex) {
			// err
			Log.logger.warning("Currency List Exception " + ex.toString());
		}
		return arrList;
	}
	
	public static ArrayList<Object> updatePgStatus(String id, String stat){

		Mysql mysqlObj = new Mysql();
		ArrayList arrList = null;
		ArrayList arrList1 = null;
		ArrayList arrList2 = null;
	
		try{
			
			String selsql1 = "SELECT * " +
			" FROM merchant_master " +
			" WHERE pg_id = "+id;

			arrList1 =  mysqlObj.resultSetToArrayList(selsql1);
			
			String selsql2 = "SELECT * " +
			" FROM mid_client_pg " +
			" WHERE pg_id = "+id;

			arrList2 =  mysqlObj.resultSetToArrayList(selsql2);
			
			if(arrList1 != null){
				String updtmm = "UPDATE merchant_master " +
				" SET is_active = ? " +
				" WHERE pg_id = ? ";

				PreparedStatement pstmt = mysqlObj.queryUpdate(updtmm);
		
				pstmt.setString(1, stat);
				pstmt.setString(2, id);
				
				pstmt.executeUpdate(); // issue invalid query
				pstmt.close();
			}
			
			if(arrList2 != null){
				String updtmcp = "UPDATE mid_client_pg " +
				" SET is_active = ? " +
				" WHERE pg_id = ? ";

				PreparedStatement pstmt = mysqlObj.queryUpdate(updtmcp);
		
				pstmt.setString(1, stat);
				pstmt.setString(2, id);
				
				pstmt.executeUpdate(); // issue invalid query
				pstmt.close();
			}
			
		}catch(Exception ex){
			//err
		}

		return arrList;
	}
	
	public static ArrayList<Object> updateMerchantStatus(String id, String stat){

		Mysql mysqlObj = new Mysql();
		ArrayList arrList = null;
		ArrayList arrList1 = null;
	
		try{
			
			String selsql = "SELECT * " +
			" FROM mid_client_pg " +
			" WHERE pg_id = "+id;

			arrList1 =  mysqlObj.resultSetToArrayList(selsql);
			
			if(arrList1 != null){
				String updtmcp = "UPDATE mid_client_pg " +
				" SET is_active = ? " +
				" WHERE pg_id = ? ";

				PreparedStatement pstmt = mysqlObj.queryUpdate(updtmcp);
		
				pstmt.setString(1, stat);
				pstmt.setString(2, id);
				
				pstmt.executeUpdate(); // issue invalid query
				pstmt.close();
			}
			
		}catch(Exception ex){
			//err
		}

		return arrList;
	}
}
