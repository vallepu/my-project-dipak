package admin.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
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
import app.net.Curl;

public class TransactionServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		//Utility.checkIfSession(req, resp);
		ArrayList<Object> arrList = null;
		String updtTxnId = req.getParameter("updtTxnId");
		
		String userRole = (String) session.getAttribute("userRole");
		Integer userId = (Integer) session.getAttribute("userId");
		
		System.out.println(userRole);
		System.out.println(userId);

		String url =  null;
		if(updtTxnId == "" || updtTxnId == null){
			arrList = this.getTxnList(userRole,userId);

			req.setAttribute("resultArray", arrList);
			
			url = "/admin/viewTransaction.jsp";

			
		}
		else{
			arrList = this.getTxnIdList(updtTxnId);

			req.setAttribute("resultArray", arrList);
			
			url = "/admin/editTransaction.jsp";

			
		}
		
		try {
			this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(req, resp);
		}
		catch (Exception ex) {
			Log.logger.warning("Txn Servlet " + ex.getMessage() +"\n"+ ex.getCause());
			ex.printStackTrace();
		}
		
	}
	

	private ArrayList<Object> getTxnList(String userRole,Integer userId){

		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = null;
		String sql = null;
	
		try{
			if(userRole.equalsIgnoreCase("ADMIN")){
				sql = "SELECT txn.txn_id, txn.currency_code, txn.currency_no, txn.txn_amount, txn.order_no, txn.pg_name, txn.pg_mode, " +
				" txn.merchant_id, clnt.client_name, txn.status, txn.created_date, txn.order_no " +
				" FROM transaction txn, client_master clnt" +
				" WHERE txn.client_id = clnt.client_id AND status='UNAPPROVED' " +
				" ORDER BY txn_id DESC ";
			}else{
				sql = "SELECT txn.txn_id, txn.currency_code, txn.currency_no, txn.txn_amount, txn.order_no, txn.pg_name, txn.pg_mode, " +
				" txn.merchant_id, clnt.client_name, txn.status, txn.created_date, txn.order_no " +
				" FROM transaction txn, client_master clnt, user_clients uc " +
				" WHERE txn.client_id = clnt.client_id AND status='UNAPPROVED' " +
				" AND uc.client_id = clnt.client_id " +
				" AND uc.user_id = "+ userId +
				" ORDER BY txn_id DESC ";
			}
			

			arrList =  mysqlObj.resultSetToArrayList(sql);
	
		}catch(Exception ex){
			//err
		}

		return arrList;
	}
	
	private ArrayList<Object> getTxnIdList(String updtTxnId){

		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = null;
	
		try{
			String sql = "SELECT txn.txn_id, txn.currency_code, txn.currency_no, txn.txn_amount, txn.order_no, txn.pg_name, txn.pg_mode, " +
							" txn.merchant_id, clnt.client_name, txn.status, txn.created_date, txn.order_no " +
							" FROM transaction txn, client_master clnt" +
							" WHERE txn.client_id = clnt.client_id " +
							" AND txn_id = "+ updtTxnId;

			arrList =  mysqlObj.resultSetToArrayList(sql);
	
		}catch(Exception ex){
			//err
		}

		return arrList;
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Utility.checkIfSession(req, resp);
		
		String newstatus = req.getParameter("updt_status");
		String comment = req.getParameter("updt_comment");
		String pgname = req.getParameter("updt_pgmode");
		String txnid = req.getParameter("updt_txnid");
		
		ArrayList oldarrList = this.getOldTxnList(txnid);
		
		/*System.out.println(status);
		System.out.println(comment);
		System.out.println(pgname);
		System.out.println(txnid);*/
		
		ArrayList updtarrList = this.getUpdatedTxnList(newstatus,comment,pgname,txnid);

		if(!(updtarrList.isEmpty())){
			HashMap<String, Object> objField = null;
			
			Iterator i = updtarrList.iterator();
			objField = new HashMap<String, Object>();
			//System.out.println("All of the mappings   " + updtarrList);
			//while (i.hasNext()){
				objField.putAll((HashMap)i.next());
				
				String respUrl = (String) objField.get("success_url");
				String status = (String) objField.get("status");
				Object txnId = objField.get("txn_id");
				String respComment = (String) objField.get("comment");
				String orderId = (String) objField.get("order_no");
				String txnType = (String) objField.get("pg_mode");
				String mop = (String) objField.get("pg_name");
				String txn_no = (String) objField.get("txn_no");
				String bank_name = (String) objField.get("bank_name");
				Date issue_date = (Date) objField.get("issue_date");
				String txnMode = mop + "-" + txn_no + "|" + bank_name + "|" + issue_date;
				String appParam1 = (String) objField.get("client_param1");
				String appParam2 = (String) objField.get("client_param2");
				String appParam3 = (String) objField.get("client_param3");
				Double txnAmount = (Double) objField.get("txn_amount");
				String currency = (String) objField.get("currency_no");
				
				req.setAttribute("respUrl", respUrl);
				req.setAttribute("status", status);
				req.setAttribute("txnId", txnId);
				req.setAttribute("respComment", respComment);
				req.setAttribute("orderId", orderId);
				req.setAttribute("txnType", txnType);
				req.setAttribute("txnMode", txnMode);
				req.setAttribute("appParam1", appParam1);
				req.setAttribute("appParam2", appParam2);
				req.setAttribute("appParam3", appParam3);
				req.setAttribute("txnAmount", txnAmount);
				req.setAttribute("currency", currency);
				
				String poststring = "status="+status+"&txnId="+txnId+"&respComment="+respComment+"&orderId="+orderId+"&txnType="+
				txnType+"&txnMode="+txnMode+"&appParam1="+appParam1+"&appParam2="+appParam2+"&appParam3="+appParam3+"&txnAmount="+txnAmount+
				"&currency="+currency;
				
				String method = "POST";
				
				Curl objCurl = new Curl();
				String curlResponse = objCurl.excutePost(respUrl, poststring, method);
				if(!curlResponse.equalsIgnoreCase("SUCCESS")){
					if(!(oldarrList.isEmpty())){
						HashMap<String, Object> objFields = null;
						
						Iterator it = oldarrList.iterator();
						objFields = new HashMap<String, Object>();
						System.out.println("All of the mappings old arr list  " + oldarrList);
						//while (i.hasNext()){
							objFields.putAll((HashMap)it.next());
							
							String oldstatus = (String) objFields.get("status");
							String oldcomment = (String) objFields.get("comment");
							String oldpgname = (String) objFields.get("pg_name");
							
							updtarrList = this.getUpdatedTxnList(oldstatus,oldcomment,oldpgname,txnid);
					}
					
				}
			//}
		}else {
				
				Log.logger.info("in else: " + updtarrList.toString());
		}
		String url = "/admin/TransactionServlet";
		Utility.redirect(resp, url);
		/*String updturl = "/responsePG.jsp";

		try {
			this.getServletConfig().getServletContext().getRequestDispatcher(updturl).forward(req, resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}*/

	}
	
	private ArrayList<Object> getUpdatedTxnList(String newstatus, String comment, String pgmode, String txnid){

		Mysql mysqlObj = new Mysql();
		ArrayList<Object> updtarrList = null;
	
		try{
		
			String query = "UPDATE transaction SET status = ?, " + 
						" comment = ?, " + 
						" pg_mode = ?, " + 
						" updated_date = now() " + 
						" WHERE txn_id = ?";
			PreparedStatement pstmt = mysqlObj.queryUpdate(query);
			pstmt.setString(1, newstatus);
			pstmt.setString(2, comment);
			pstmt.setString(3, pgmode);
			pstmt.setString(4, txnid);
			
			pstmt.executeUpdate(); // issue invalid query
			pstmt.close();
			
			updtarrList =  this.getList(txnid);
		
		}catch(Exception ex){
			//err
		}
		return updtarrList;
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<Object> getList(String txnid) {
		Mysql mysqlObj = new Mysql();
		ArrayList<Object> updtarrList = null;
		
		try{
			String sql = "SELECT txn.txn_id AS txnId, txn.currency_code, txn.currency_no AS currency, txn.txn_amount AS txnAmount, txn.order_no orderId, txn.pg_name AS txnType, clnt.success_url AS respUrl, txn.merchant_id, clnt.client_name, txn.status AS " +
			" status , txn.comment AS respComment, txn.txn_id AS txnId, txn.created_date, txn.pg_name AS txnMode, txn.client_param1 AS appParam1, txn.client_param2 AS appParam2, txn.client_param3 AS appParam3, txn.bank_name, txn.issue_date, txn.pg_mode " +
			" FROM transaction txn, client_master clnt " +
			" WHERE txn.client_id = clnt.client_id " +
			" AND txn.txn_id = " + txnid;
			updtarrList =  mysqlObj.resultSetToArrayList(sql);
			
		}catch (SQLException e) {
			Log.logger.warning("SQL error for ");
		}
		
		return updtarrList; 
		
	}
	
	private ArrayList<Object> getOldTxnList(String txnid) {
		Mysql mysqlObj = new Mysql();
		ArrayList<Object> oldarrList = null;
		
		try{
			String sql = "SELECT txn.txn_id AS txnId, txn.currency_code, txn.currency_no AS currency, txn.txn_amount AS txnAmount, txn.order_no orderId, txn.pg_name AS txnType, clnt.success_url AS respUrl, txn.merchant_id, clnt.client_name, txn.status AS " +
			" status , txn.comment AS respComment, txn.txn_id AS txnId, txn.created_date, txn.pg_name AS txnMode, txn.client_param1 AS appParam1, txn.client_param2 AS appParam2, txn.client_param3 AS appParam3, txn.bank_name, txn.issue_date, txn.pg_mode " +
			" FROM transaction txn, client_master clnt " +
			" WHERE txn.client_id = clnt.client_id " +
			" AND txn.txn_id = " + txnid;
			oldarrList =  mysqlObj.resultSetToArrayList(sql);
			
		}catch (SQLException e) {
			Log.logger.warning("SQL error for ");
		}
		
		return oldarrList; 
		
	}
	
	
}
