package admin.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Log;
import app.Masters;
import app.Mysql;

public class MidClientPgServlet extends HttpServlet {
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(true);
		String mcpId = req.getParameter("mcpId");
		
		session.setAttribute("mcpId", mcpId);
		
		String clientId = req.getParameter("client_id");
		String pgId = req.getParameter("pg_id");
		String merchantId = req.getParameter("merchant_id");
		String currencyNo = req.getParameter("currency_no");
		String limitFrom = req.getParameter("limit_from");
		String limitTo = req.getParameter("limit_to");
		String validFrom = req.getParameter("valid_from");
		String validTo = req.getParameter("valid_to");
		String isActive = req.getParameter("is_active");
		String caption = req.getParameter("caption");
		
		if(mcpId == "" || mcpId == null){
			ArrayList arrList = this.saveMidClientPg(clientId,pgId,merchantId,currencyNo,limitFrom,limitTo,validFrom,validTo,isActive,caption);
		}else if(mcpId != "" || mcpId != null){
			ArrayList updtarrList = this.updateMidClientPg(clientId,pgId,merchantId,currencyNo,limitFrom,limitTo,validFrom,validTo,isActive,caption,mcpId);
		}
		
		String url = "/admin/ViewMidClientPg";
		
		try {
			this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(req, resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(true);
		
		String mcpId = req.getParameter("mcpId");
		
		if(mcpId == "" || mcpId == null){
			ArrayList<Object> arrClientLists = Masters.clientLists();
			
			req.setAttribute("arrClientLists", arrClientLists);
			
			ArrayList<Object> arrPgLists = Masters.pgLists();
			
			req.setAttribute("arrPgLists", arrPgLists);	
			
			ArrayList<Object> arrCurrencyLists = Masters.currencyLists();
			
			req.setAttribute("arrCurrencyLists", arrCurrencyLists);
		}
		else if(mcpId != "" || mcpId != null){
			
			//session.setAttribute("mcpId", mcpId);
			req.setAttribute("mcpId", mcpId);
			
			ArrayList updatedarrList = this.selectMidClientPg(mcpId);
			
			req.setAttribute("updatedarrList", updatedarrList);
			
			HashMap<String, Object> objField = null;
			
			Iterator i = updatedarrList.iterator();
			objField = new HashMap<String, Object>();
			//System.out.println("All of the mappings   " + updatedarrList);
			
			objField.putAll((HashMap)i.next());
			
			Object client_id = objField.get("client_id");
			String client_name = (String) objField.get("client_name");
			Object pg_id = objField.get("pg_id");
			String pg_name = (String) objField.get("pg_name");
			Object merchant_id = objField.get("merchant_id");
			String merchant_code = (String) objField.get("merchant_code");
			String currency_no = (String) objField.get("currency_no");
			String currency_name = (String) objField.get("currency_name");
			Object limit_from = objField.get("limit_from");
			Object limit_to = objField.get("limit_to");
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String valid_from = sdfDate.format(objField.get("valid_from"));
			SimpleDateFormat sdfDates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String valid_to = sdfDates.format(objField.get("valid_to"));
			String is_active = (String) objField.get("is_active");
			String caption = (String) objField.get("caption");
			
			req.setAttribute("client_id", client_id);
			req.setAttribute("client_name", client_name);
			req.setAttribute("pg_id", pg_id);
			req.setAttribute("pg_name", pg_name);
			req.setAttribute("merchant_id", merchant_id);
			req.setAttribute("merchant_code", merchant_code);
			req.setAttribute("currency_no", currency_no);
			req.setAttribute("currency_name", currency_name);
			req.setAttribute("limit_from", limit_from);
			req.setAttribute("limit_to", limit_to);
			req.setAttribute("valid_from", valid_from);
			req.setAttribute("valid_to", valid_to);
			req.setAttribute("is_active", is_active);
			req.setAttribute("caption", caption);
		
			ArrayList<Object> arrClientLists = Masters.clientLists();
			
			req.setAttribute("arrClientLists", arrClientLists);
			
			ArrayList<Object> arrPgLists = Masters.pgLists();
			
			req.setAttribute("arrPgLists", arrPgLists);	
			
			ArrayList<Object> arrCurrencyLists = Masters.currencyLists();
			
			req.setAttribute("arrCurrencyLists", arrCurrencyLists);
			
			/*String updturl = "/admin/addMidClientPg.jsp";
	
			try {
				this.getServletConfig().getServletContext().getRequestDispatcher(updturl).forward(req, resp);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}*/
		}
		String url = "/admin/addMidClientPg.jsp";
		
		try {
			this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(req, resp);
		}
		catch (Exception ex) {
			Log.logger.info("URL Exception" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public ArrayList saveMidClientPg(String clientId, String pgId, String merchantId, String currencyNo, 
			String limitFrom, String limitTo, String validFrom, String validTo, String isActive, String caption) {
		
		int lastInsertId = 0;
		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = null;

		try {
			String sql = "INSERT INTO mid_client_pg " +
						" SET client_id = ?, pg_id = ?, " + 
						" merchant_id = ?, currency_no = ?, " +
						" limit_from = ?, limit_to = ?, valid_from = ?, valid_to = ?, is_active = ?, caption = ?, " +
						" created_date = now() ";
			
			PreparedStatement pstmt = mysqlObj.queryUpdate(sql);

			pstmt.setString(1, clientId);
			pstmt.setString(2, pgId);
			pstmt.setString(3, merchantId);
			pstmt.setString(4, currencyNo);
			pstmt.setString(5, limitFrom);
			pstmt.setString(6, limitTo);
			pstmt.setString(7, validFrom);
			pstmt.setString(8, validTo);
			pstmt.setString(9, isActive);
			pstmt.setString(10, caption);

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
			System.out.print("Currency List Exception " + ex.toString());
		}
		return arrList;
	}
	
	public ArrayList selectMidClientPg(String mcpId) {
		
			int lastInsertId = 0;
			Mysql mysqlObj = new Mysql();
			ArrayList selarrList = null;
	
			try {
				String sql = "SELECT cm.client_name, pm.pg_name, mm.merchant_code, currm.currency_name, mcp.* " +
							" FROM mid_client_pg mcp, client_master cm, pg_master pm, merchant_master mm, currency_master currm " +
							" WHERE cm.client_id = mcp.client_id " +
							" AND pm.pg_id = mcp.pg_id " +
							" AND mm.merchant_id = mcp.merchant_id " +
							" AND currm.currency_no = mcp.currency_no " +
							" AND mcp.mcp_id="+mcpId;
				
				selarrList =  mysqlObj.resultSetToArrayList(sql);
			} catch (Exception ex) {
				// err
				System.out.print("Currency List Exception " + ex.toString());
			}
			return selarrList;
	}
	
	public ArrayList updateMidClientPg(String clientId, String pgId, String merchantId, String currencyNo, 
			String limitFrom, String limitTo, String validFrom, String validTo, String isActive, String caption, String mcpId) {
		/*Object client_id, Object pg_id, Object merchant_id, String currency_no, 
			Object limit_from, Object limit_to, String valid_from, String valid_to, String is_active, String caption,*/
			Mysql mysqlObj = new Mysql();
			ArrayList<Object> updtarrList = null;
	
			try {
				String sql = "UPDATE mid_client_pg " +
							" SET client_id = ?, pg_id = ?, " + 
							" merchant_id = ?, currency_no = ?, " +
							" limit_from = ?, limit_to = ?, valid_from = ?, valid_to = ?, is_active = ?, caption = ?, " +
							" updated_date = now() " +
							" WHERE mcp_id = ? ";
				
				PreparedStatement pstmt = mysqlObj.queryUpdate(sql);
				
				/*pstmt.setObject(1, client_id);
				pstmt.setObject(2, pg_id);
				pstmt.setObject(3, merchant_id);
				pstmt.setString(4, currency_no);
				pstmt.setObject(5, limit_from);
				pstmt.setObject(6, limit_to);
				pstmt.setString(7, valid_from);
				pstmt.setString(8, valid_to);
				pstmt.setString(9, is_active);
				pstmt.setString(10, caption);*/
				pstmt.setString(1, clientId);
				pstmt.setString(2, pgId);
				pstmt.setString(3, merchantId);
				pstmt.setString(4, currencyNo);
				pstmt.setString(5, limitFrom);
				pstmt.setString(6, limitTo);
				pstmt.setString(7, validFrom);
				pstmt.setString(8, validTo);
				pstmt.setString(9, isActive);
				pstmt.setString(10, caption);
				pstmt.setString(11, mcpId);
	
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
				Log.logger.warning("Update Query Exception " + ex.toString());
			}
			return updtarrList;
	}

}
