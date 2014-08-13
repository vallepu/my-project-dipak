package admin.servlet;

import java.io.IOException;
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

import app.Log;
import app.Mysql;
import app.Utility;

public class CurrencyMasterServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(true);
		String cmId = req.getParameter("cmId");
		
		session.setAttribute("cmId", cmId);
		
		String currencyName = req.getParameter("currency_name");
		String currencyNo = req.getParameter("currency_no");
		String currencyCode = req.getParameter("currency_code");
		String currencyLocation = req.getParameter("currency_location");
		
		if(cmId == "" || cmId == null){
			ArrayList arrList = this.saveCurrencyMaster(currencyName,currencyNo,currencyCode,currencyLocation);
		}else if(cmId != "" || cmId != null){
			ArrayList updtarrList = this.updateCurrencyMaster(currencyName,currencyNo,currencyCode,currencyLocation,cmId);
		}
		
		String url = "/admin/ViewCurrencyMaster";
		
		Utility.redirect(resp, url);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(true);
		String url = null;
		String cmId = req.getParameter("cmId");
		
		if(cmId == "" || cmId == null){
			url = "/admin/currencyMaster.jsp";
			
			
		}else if(cmId != "" || cmId != null){
			
			//session.setAttribute("mcpId", mcpId);
			req.setAttribute("cmId", cmId);
			
			ArrayList updatedarrList = this.selectCurrencyMaster(cmId);
			
			req.setAttribute("updatedarrList", updatedarrList);
			
			HashMap<String, Object> objField = null;
			
			Iterator i = updatedarrList.iterator();
			objField = new HashMap<String, Object>();
			//System.out.println("All of the mappings   " + updatedarrList);
			
			objField.putAll((HashMap)i.next());
			
			String currency_name = (String) objField.get("currency_name");
			String currency_no = (String) objField.get("currency_no");
			String currency_code = (String) objField.get("currency_code");
			String currency_location = (String) objField.get("currency_location");
			
			req.setAttribute("currency_name", currency_name);
			req.setAttribute("currency_no", currency_no);
			req.setAttribute("currency_code", currency_code);
			req.setAttribute("currency_location", currency_location);
			
			url = "/admin/currencyMaster.jsp";
			
			
		}
		try {
			this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(req, resp);
		}
		catch (Exception ex) {
			Log.logger.info("URL Exception" + ex.getMessage());
			ex.printStackTrace();
		}
	
	}
	
	public ArrayList saveCurrencyMaster(String currencyName,String currencyNo,String currencyCode,String currencyLocation) {
		
		int lastInsertId = 0;
		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = null;

		try {
			String sql = "INSERT INTO currency_master " +
						" SET currency_name = ?, currency_no = ?, " + 
						" currency_code = ?, currency_location = ?, " +
						" created_date = now() ";
			
			PreparedStatement pstmt = mysqlObj.queryUpdate(sql);

			pstmt.setString(1, currencyName);
			pstmt.setString(2, currencyNo);
			pstmt.setString(3, currencyCode);
			pstmt.setString(4, currencyLocation);

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
			System.out.print("Currency Master insert Exception " + ex.toString());
		}
		return arrList;
	}
	
	public ArrayList updateCurrencyMaster(String currencyName,String currencyNo,String currencyCode,String currencyLocation,String cmId) {
		
			Mysql mysqlObj = new Mysql();
			ArrayList<Object> updtarrList = null;
	
			try {
								
				String sql = "UPDATE currency_master SET currency_name = ?, currency_no = ?, " + 
							" currency_code = ?, currency_location = ?, " +
							" updated_date = now() " +
							" WHERE currency_id = ? ";
				
				PreparedStatement pstmt = mysqlObj.queryUpdate(sql);
				
				pstmt.setString(1, currencyName);
				pstmt.setString(2, currencyNo);
				pstmt.setString(3, currencyCode);
				pstmt.setString(4, currencyLocation);
				pstmt.setString(5, cmId);
	
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
				Log.logger.warning("Currency Master Update Exception " + ex.toString());
			}
			return updtarrList;
	}
	
	public ArrayList selectCurrencyMaster(String cmId) {
		
		Mysql mysqlObj = new Mysql();
		ArrayList selarrList = null;

		try {
			String sql = "SELECT * " +
						" FROM currency_master " +
						" WHERE currency_id = "+cmId;
			
			selarrList =  mysqlObj.resultSetToArrayList(sql);
		} catch (Exception ex) {
			// err
			System.out.print("Currency List Exception " + ex.toString());
		}
		return selarrList;
	}


}
