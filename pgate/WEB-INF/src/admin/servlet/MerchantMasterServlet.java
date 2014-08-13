package admin.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Masters;
import app.Log;
import app.Mysql;
import app.Utility;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MerchantMasterServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(true);
		String mmId = req.getParameter("mmId");
		
		session.setAttribute("mmId", mmId);
		
		String merchantCode = req.getParameter("merchant_code");
		String password = req.getParameter("password");
		String securityId = req.getParameter("security_id");
		String txnLimit = req.getParameter("txn_limit");
		String validFrom = req.getParameter("valid_from");
		String validTo = req.getParameter("valid_to");
		String isActive = req.getParameter("is_active");
		String pgid = req.getParameter("pg_id");
		
		if(mmId == "" || mmId == null){
			ArrayList arrList = this.saveMerchantMaster(merchantCode,password,securityId,txnLimit,validFrom,validTo,isActive,pgid);
		}else{
			ArrayList updtarrList = this.updateMerchantMaster(merchantCode,password,securityId,txnLimit,validFrom,validTo,isActive,pgid,mmId);
			
			ArrayList<Object> arrupdtMerchantLists = Masters.updateMerchantStatus(pgid,isActive);
			
			req.setAttribute("arrupdtMerchantLists", arrupdtMerchantLists);
		}
		
		String url = "/admin/ViewMerchantMaster";
		
		Utility.redirect(resp, url);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(true);
		String url = null;
		String mmId = req.getParameter("mmId");
		
		ArrayList<Object> arrPgLists = Masters.pgLists();
		
		req.setAttribute("arrPgLists", arrPgLists);	
		
		if(mmId == "" || mmId == null){
			
			url = "/admin/merchantMaster.jsp";
			
			
		}else{
			
			req.setAttribute("mmId", mmId);
			
			ArrayList updatedarrList = this.selectMerchantMaster(mmId);
			
			req.setAttribute("updatedarrList", updatedarrList);
			
			HashMap<String, Object> objField = null;
			
			Iterator i = updatedarrList.iterator();
			objField = new HashMap<String, Object>();
			//System.out.println("All of the mappings   " + updatedarrList);
			
			objField.putAll((HashMap)i.next());
			
			String merchant_code = (String) objField.get("merchant_code");
			String password = (String) objField.get("password");
			String security_id = (String) objField.get("security_id");
			Double txn_limit = (Double) objField.get("txn_limit");
			SimpleDateFormat fromDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String valid_from = fromDate.format(objField.get("valid_from"));
			SimpleDateFormat toDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String valid_to = toDate.format(objField.get("valid_to"));
			String is_active = (String) objField.get("is_active");
			Integer pg_id = (Integer) objField.get("pg_id");
			String pg_name = (String) objField.get("pg_name");
			
			req.setAttribute("merchant_code", merchant_code);
			req.setAttribute("password", password);
			req.setAttribute("security_id", security_id);
			req.setAttribute("txn_limit", txn_limit);
			req.setAttribute("valid_from", valid_from);
			req.setAttribute("valid_to", valid_to);
			req.setAttribute("is_active", is_active);
			req.setAttribute("pg_id", pg_id);
			req.setAttribute("pg_name", pg_name);
			
			url = "/admin/merchantMaster.jsp";
			
			
		}
		
		try {
			this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(req, resp);
		}
		catch (Exception ex) {
			Log.logger.info("URL Exception" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public ArrayList saveMerchantMaster(String merchantCode,String password,String securityId,String txnLimit,String validFrom,String validTo,String isActive,String pgid) {
		
		int lastInsertId = 0;
		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = null;

		try {
			String sql = "INSERT INTO merchant_master " +
						" SET merchant_code = ?, password = ?, " + 
						" security_id = ?, txn_limit = ?, " +
						" valid_from = ?, valid_to = ?, " +
						" is_active = ?, pg_id = ?, " +
						" created_date = now() ";
			
			PreparedStatement pstmt = mysqlObj.queryUpdate(sql);

			pstmt.setString(1, merchantCode);
			pstmt.setString(2, password);
			pstmt.setString(3, securityId);
			pstmt.setString(4, txnLimit);
			pstmt.setString(5, validFrom);
			pstmt.setString(6, validTo);
			pstmt.setString(7, isActive);
			pstmt.setString(8, pgid);

			pstmt.executeUpdate(); // issue invalid query
			pstmt.close();

			ResultSet result = mysqlObj
					.queryResult("SELECT LAST_INSERT_ID() AS ID");
			if (result.first()) {
				lastInsertId = result.getInt("ID");
			}

			result.close();
		} catch (SQLException ex) {
			// err
			System.out.print("Merchant Master insert Exception " + ex.toString());
		}
		return arrList;
	}
	
	public ArrayList updateMerchantMaster(String merchantCode,String password,String securityId,String txnLimit,String validFrom,String validTo,String isActive,String pgid,String mmId) {
		
			Mysql mysqlObj = new Mysql();
			ArrayList<Object> updtarrList = null;
	
			try {
				
				System.out.println(merchantCode);
				System.out.println(password);
				System.out.println(securityId);
				System.out.println(txnLimit);
				System.out.println(validFrom);
				System.out.println(validTo);
				System.out.println(isActive);
				System.out.println(pgid);
				System.out.println(mmId);
				
				String sql = "UPDATE merchant_master " +
							" SET merchant_code = ?, password = ?, " + 
							" security_id = ?, txn_limit = ?, " +
							" valid_from = ?, valid_to = ?, " +
							" is_active = ?, pg_id = ?, " +
							" updated_date = now() " +
							" WHERE merchant_id = ? ";
				
				PreparedStatement pstmt = mysqlObj.queryUpdate(sql);
				
				pstmt.setString(1, merchantCode);
				pstmt.setString(2, password);
				pstmt.setString(3, securityId);
				pstmt.setString(4, txnLimit);
				pstmt.setString(5, validFrom);
				pstmt.setString(6, validTo);
				pstmt.setString(7, isActive);
				pstmt.setString(8, pgid);
				pstmt.setString(9, mmId);
	
				pstmt.executeUpdate(); // issue invalid query
				pstmt.close();
	
				/*ResultSet result = mysqlObj
						.queryResult("SELECT LAST_INSERT_ID() AS ID");
				if (result.first()) {
					lastInsertId = result.getInt("ID");
				}
	
				result.close();*/
			} catch (SQLException ex) {
				// err
				Log.logger.warning("Merchant Master Update Exception " + ex.toString());
			}
			return updtarrList;
	}
	
	public ArrayList selectMerchantMaster(String mmId) {
		
		Mysql mysqlObj = new Mysql();
		ArrayList selarrList = null;

		try {
			String sql = "SELECT mm.*, pgm.pg_id, pgm.pg_name " +
						" FROM merchant_master mm JOIN pg_master pgm ON mm.pg_id = pgm.pg_id " +
						" WHERE mm.merchant_id = "+mmId;
			
			selarrList =  mysqlObj.resultSetToArrayList(sql);
		} catch (Exception ex) {
			// err
			System.out.print("Merchant List Exception " + ex.toString());
		}
		return selarrList;
	}
}
