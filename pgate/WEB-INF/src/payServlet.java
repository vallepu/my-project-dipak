/*
 * @Author:  Dipak A. Mali
 * @Dated:   30-09-2010
 * @Class:   Payment Gateway 
 * @Param:   MerchantId, Amount, currencycode, trackId, responseUrl, errorUrl
 * @returns: Url of Payment Gateway
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Config;
import app.Mysql;
import app.Transaction;
import app.Utility;

public class payServlet extends HttpServlet{

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		
		HttpSession session = request.getSession(false);
		Config.setAllConfig();
		
		String clientId = request.getParameter("clientId");
		
		String orderNo = request.getParameter("orderNo");
		String pgId = request.getParameter("pgId");
		String pgMode = request.getParameter("pgMode");
		String txnMode = request.getParameter("txnMode");
		String currency = (String) session.getAttribute("currency");
		String txnAmount = (String) session.getAttribute("txnAmount");
		session.setAttribute("txnMode", txnMode);
		session.setAttribute("pgMode", pgMode);
		session.setAttribute("txnAmount", txnAmount);
		
		String offurl = "/OfflineServlet?payopt="+txnMode;
		
		if(pgId.equalsIgnoreCase("0")){
			Utility.redirect(response, offurl);
		}
		//Commented by Asma on 21032011 for a consolidate Online & Offline Payment options
		//String txnMode = request.getParameter("txnType");
		
		String merchantId = null;
		String merchantCode = null;
		String password = null;
		String securityId = null;
		String currencyCode = null;
		String pgName = null;
		
		int count = 0;
		Mysql mysqlObj = new Mysql();

		String where = " WHERE CM.currency_id = MC.currency_id AND MC.merchant_id = MCP.merchant_id  AND " +
			" PM.pg_id = MCP.pg_id  AND MM.merchant_id = MCP.merchant_id AND " +
			" MCP.valid_from <= now() AND MCP.valid_to >= now() AND MCP.is_active = 'yes' AND " +
			" MCP.limit_from <= " + txnAmount + " AND MCP.limit_to >= " + txnAmount + 
			" AND MCP.client_id =" + clientId + " AND PM.pg_id = " + pgId + " AND  CM.currency_no = '" + currency + "'" ;


		String sql = " SELECT count(*) AS COUNT " +
			" FROM merchant_currency MC, pg_master PM, mid_client_pg MCP, currency_master CM, merchant_master MM " +
				where;

		try{
			ResultSet rs = mysqlObj.queryResult(sql);
			if(rs.first()){
				count = rs.getInt("COUNT");
			}

		}catch(SQLException ex){
			//errr
		}

		if(count >= 1){
			
			if(count > 1){
				where += " AND MCP.preffered = 'yes' ";
			}
			
			String query = " SELECT CM.currency_code, PM.pg_name, MM.merchant_id, MM.merchant_code, MM.security_id, MM.password " +
			" FROM merchant_currency MC, pg_master PM, mid_client_pg MCP, currency_master CM, merchant_master MM " +
			where;
			
			try{
				ResultSet resultSet = mysqlObj.queryResult(query);
				if(resultSet.first()){
					merchantId = resultSet.getString("merchant_id");
					merchantCode = resultSet.getString("merchant_code");
					password = resultSet.getString("password");
					securityId = resultSet.getString("security_id");
					currencyCode = resultSet.getString("currency_code");
					pgName = resultSet.getString("pg_name");
				}
				
			int txnId = Integer.parseInt(session.getAttribute("txnId").toString());
			Transaction txnObject = new Transaction();
			txnObject.updateTransaction ( merchantId, pgName, currencyCode, pgMode, txnId );

			}catch(SQLException ex){
				//err
			}

			// SESSION VARIBLE DECLARATION `merchantCode`
			session.setAttribute("merchantCode", merchantCode);
			
			String paramStr = "merchantId=" + merchantId + "&merchantCode=" + merchantCode + 
								"&password=" + password + "&securityId=" + securityId + 
								"&currencyCode=" + currencyCode + "&pgName=" + pgName;

			String url = "/forward.jsp?" + paramStr;

			try {
				// Set the attribute and Forward to hello.jsp
				this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(request, response);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			//response.sendRedirect( url );
		}else{
			PrintWriter pw = response.getWriter();
			try{
				String clnt = null;
				String queryErr = "SELECT * FROM `client_master` WHERE `client_id` = " + clientId;
				ResultSet resultErr = mysqlObj.queryResult(queryErr);
				if(resultErr.next()){
					clnt = resultErr.getString("client_name");
				}
				resultErr.close();
				pw.println("No Payment Gateway configured for Client : " + clnt);
				pw.println(" OR Something went wrong with the Query");
			}
			catch(SQLException e){
				//log err
			}
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		PrintWriter pw = response.getWriter();
		pw.println("Page won't accept Get URL");

	}

}
