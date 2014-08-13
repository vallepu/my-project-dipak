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

import app.Log;
import app.Mysql;
import app.Utility;

public class ClientMasterServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(true);
		String clId = req.getParameter("clId");
		
		session.setAttribute("clId", clId);
		
		String clientName = req.getParameter("client_name");
		String clientUrl = req.getParameter("client_url");
		String validFrom = req.getParameter("valid_from");
		String validTo = req.getParameter("valid_to");
		String isActive = req.getParameter("is_active");
		String successUrl = req.getParameter("success_url");
		String failureUrl = req.getParameter("failure_url");
		
		if(clId == "" || clId == null){
			ArrayList arrList = this.saveClientMaster(clientName,clientUrl,validFrom,validTo,isActive,successUrl,failureUrl);
		}else{
			ArrayList updtarrList = this.updateClientMaster(clientName,clientUrl,validFrom,validTo,isActive,successUrl,failureUrl,clId);
		}
		
		String url = "/admin/ViewClientMaster";
		
		Utility.redirect(resp, url);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(true);
		String url = null;
		String clId = req.getParameter("clId");
		
		if(clId == "" || clId == null){
			url = "/admin/clientMaster.jsp";
			
			
		}else{
			
			req.setAttribute("clId", clId);
			
			ArrayList updatedarrList = this.selectClientMaster(clId);
			
			req.setAttribute("updatedarrList", updatedarrList);
			
			HashMap<String, Object> objField = null;
			
			Iterator i = updatedarrList.iterator();
			objField = new HashMap<String, Object>();
			System.out.println("All of the mappings   " + updatedarrList);
			
			objField.putAll((HashMap)i.next());
			
			String client_name = (String) objField.get("client_name");
			String client_url = (String) objField.get("client_url");
			Date valid_from = (Date) objField.get("valid_from");
			Date valid_to = (Date) objField.get("valid_to");
			String is_active = (String) objField.get("is_active");
			String success_url = (String) objField.get("success_url");
			String failure_url = (String) objField.get("failure_url");
			
			req.setAttribute("client_name", client_name);
			req.setAttribute("client_url", client_url);
			req.setAttribute("valid_from", valid_from);
			req.setAttribute("valid_to", valid_to);
			req.setAttribute("is_active", is_active);
			req.setAttribute("success_url", success_url);
			req.setAttribute("failure_url", failure_url);
			
			url = "/admin/clientMaster.jsp";
			
			
		}
		
		try {
			this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(req, resp);
		}
		catch (Exception ex) {
			Log.logger.info("URL Exception" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public ArrayList saveClientMaster(String clientName,String clientUrl,String validFrom,String validTo,String isActive,String successUrl,String failureUrl) {
		
		int lastInsertId = 0;
		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = null;

		try {
			String sql = "INSERT INTO client_master " +
						" SET client_name = ?, client_url = ?, " + 
						" valid_from = ?, valid_to = ?, " +
						" is_active = ?, success_url = ?, " +
						" failure_url = ?, " +
						" created_date = now() ";
			
			PreparedStatement pstmt = mysqlObj.queryUpdate(sql);

			pstmt.setString(1, clientName);
			pstmt.setString(2, clientUrl);
			pstmt.setString(3, validFrom);
			pstmt.setString(4, validTo);
			pstmt.setString(5, isActive);
			pstmt.setString(6, successUrl);
			pstmt.setString(7, failureUrl);

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
			System.out.print("Client Master insert Exception " + ex.toString());
		}
		return arrList;
	}
	
	public ArrayList updateClientMaster(String clientName,String clientUrl,String validFrom,String validTo,String isActive,String successUrl,String failureUrl,String clId) {
		
			Mysql mysqlObj = new Mysql();
			ArrayList<Object> updtarrList = null;
	
			try {
								
				String sql = "UPDATE client_master " +
							" SET client_name = ?, client_url = ?, " + 
							" valid_from = ?, valid_to = ?, " +
							" is_active = ?, success_url = ?, " +
							" failure_url = ?, " +
							" updated_date = now() " +
							" WHERE client_id = ? ";
				
				PreparedStatement pstmt = mysqlObj.queryUpdate(sql);
				
				pstmt.setString(1, clientName);
				pstmt.setString(2, clientUrl);
				pstmt.setString(3, validFrom);
				pstmt.setString(4, validTo);
				pstmt.setString(5, isActive);
				pstmt.setString(6, successUrl);
				pstmt.setString(7, failureUrl);
				pstmt.setString(8, clId);
	
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
				Log.logger.warning("Client Master Update Exception " + ex.toString());
			}
			return updtarrList;
	}
	
	public ArrayList selectClientMaster(String clId) {
		
		Mysql mysqlObj = new Mysql();
		ArrayList selarrList = null;

		try {
			String sql = "SELECT * " +
						" FROM client_master " +
						" WHERE client_id = "+clId;
			
			selarrList =  mysqlObj.resultSetToArrayList(sql);
		} catch (Exception ex) {
			// err
			System.out.print("Client List Exception " + ex.toString());
		}
		return selarrList;
	}
}
