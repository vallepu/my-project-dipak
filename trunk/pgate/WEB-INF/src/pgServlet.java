/*
 * @Author:  Dipak A. Mali
 * @Dated:   30-09-2010
 * @Class:   EServe Payment Gateway
 * @Param:   MerchantId, Amount, currencycode, trackId, responseUrl, errorUrl
 * @returns: Url of Payment Gateway
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Config;
import app.Log;
import app.Mysql;
import app.Transaction;
import app.Utility;

public class pgServlet extends HttpServlet {

	
	@Override
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		Config.setAllConfig();

		if(session == null){
			PrintWriter pw = response.getWriter();
			pw.println("<center>Please Try againg.</center>");
			pw.println("<script>document.onload=history.go(-1);</script>");
		}
		String rplStatus = request.getParameter("rplStatus");
		boolean rplFlag = false;

		String clientId = request.getParameter("clientId");
		String txnAmount = request.getParameter("txnAmount");
		String currency = request.getParameter("currency");
		String orderNo = request.getParameter("orderNo");
		String appParam1 = request.getParameter("appParam1");
		String appParam2 = request.getParameter("appParam2");
		String appParam3 = request.getParameter("appParam3");
		String txnType = request.getParameter("txnType");
		
		session.setAttribute("txnType", txnType);
		session.setAttribute("currency", currency);
		session.setAttribute("appParam1", appParam1);
		session.setAttribute("appParam2", appParam2);
		session.setAttribute("appParam3", appParam3);
		session.setAttribute("orderId", orderNo);
		session.setAttribute("clientId", clientId);
		session.setAttribute("txnAmount", txnAmount);
		
			if (rplStatus != null) {
	
				rplFlag = this.isItTest(rplStatus);
			}
			if (!rplFlag) {
	
				ArrayList<Object> arrList = new ArrayList<Object>();
				Transaction txnObject = new Transaction();
				int txnId = txnObject.startTransaction(clientId, txnAmount, currency, orderNo, appParam1, appParam2, appParam3);
	
				session.setAttribute("txnId", txnId);
				
				//Commented by Asma on 21032011 for a consolidate Online & Offline Payment options 
				/*if(txnType.equalsIgnoreCase("OFFLINE")){
					arrList = this.getOffLinePayList(clientId);
					Log.logger.info("Offline Payment Options");
					System.out.println("Offline Payment Options");
				}else{
					arrList = this.getPGList(clientId, txnAmount, currency, orderNo);
					System.out.println("Online Payment Options");
					Log.logger.info("Online Payment Options");
				}*/
				arrList = this.getPGList(clientId, txnAmount, currency, orderNo);
				
				Log.logger.info("Payment Options" + clientId);
				
				request.setAttribute("resultArray", arrList);
				request.setAttribute("txnType", txnType);
				request.setAttribute("clientId", clientId);
				
				if (arrList.isEmpty()) {
	
					PrintWriter pw = response.getWriter();
						
					String clnt = txnObject.getClientName(clientId);
	
					pw.println(" Something went wrong with the Inputs. <br />Eg. May be <b>Transaction Currency Not supported.</b> OR ");
					pw.println(" <br />May be <b> Transaction Amount exceed the configured amount. </b> OR");
					pw.println(" <br />May be <b> Transaction Amount is less than the configured amount. </b> OR");
					pw.println(" <br /><b> confirm Payment Gateway Validity. </b> OR");
					pw.println(" <br />Make Sure that, The Payment Gateway is Configured for Client : "+ clnt);
				}
	
				String url = "/selectPG.jsp";
	
				try {
					// Set the attribute and Forward to hello.jsp
					this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(request, response);
				} catch (Exception ex) {
					Log.logger.warning("Error: " + ex.getMessage());
					
				}
				// response.sendRedirect( url );
			} else {
				/********************************* Testing **********************************************/
				Transaction txnObject = new Transaction();
				String respUrl = txnObject.getResponseUrl(clientId);
				String respComment = null;
				String status = null;
	
				if (rplStatus.compareTo("APPROVED") == 0) {
					respComment = "Successful";
					status = "APPROVED";
				} else if (rplStatus.compareTo("FAILURE") == 0) {
					respComment = "Card Number Invalid";
					status = "FAILURE";
				}
	
				Random rand = new Random();
				int txnId = rand.nextInt();
	
				txnId = (txnId < 0) ? ((-1) * txnId) : txnId; // txnId must be +ve
	
				if (respUrl != null) {
					
					String txnMode = "Test Simulation";
					
					request.setAttribute("status", status);
					request.setAttribute("txnId", txnId);
					request.setAttribute("orderId", orderNo);
					request.setAttribute("respComment", respComment);
					request.setAttribute("respUrl", respUrl);
					request.setAttribute("appParam1", appParam1);
					request.setAttribute("appParam2", appParam2);
					request.setAttribute("appParam3", appParam3);
					request.setAttribute("txnAmount", txnAmount);
					request.setAttribute("txnType", txnType);
					request.setAttribute("txnMode", txnMode);
	
					String url = "/responsePG.jsp";
					try {
						this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(request,response);
					} catch (Exception ex) {
						Log.logger.warning("Error: " + ex.getMessage());
						
					}
				} else {
					String url = Config.get("SITE_PATH") + "/error.jsp";
					Utility.redirect(response, url);
				}
			}
	}
	
	/* Commented by Asma on 21032011 for a consolidate Online & Offline Payment options */
	/*private ArrayList<Object> getOffLinePayList(String clientId) {
		ArrayList<Object> arrList = new ArrayList<Object>();
		HashMap<String, Object> row = new HashMap<String, Object>();
		HashMap<String, Object> row1 = new HashMap<String, Object>();
			row.put("caption", "Cheque");
			row.put("payType", "Cheque");
			arrList.add(row);
			
			row1.put("caption", "Demand Draft");
			row1.put("payType", "DD");		
			arrList.add(row1);
		return arrList;
	}*/
	
	
	private ArrayList<Object> getPGList(String clientId, String txnAmount, String currency, String orderNo) {

		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = new ArrayList<Object>();

		try {
			String sql = "SELECT pg_id,merchant_id,caption,image_caption FROM mid_client_pg "
					+ " WHERE valid_from <= now() AND valid_to >= now() AND is_active = 'yes' AND "
					+ " limit_from <= " + txnAmount + " AND limit_to >= "
					+ txnAmount + " AND client_id = " + clientId
					+ " AND currency_no = '" + currency + "' "
					+ " GROUP BY image_caption ORDER BY preffered DESC ";
			
			arrList = mysqlObj.resultSetToArrayList(sql);

		} catch (SQLException ex) {
			Log.logger.warning("Error: " + ex.getMessage());
			Log.logger.warning("Error Code: " + ex.getErrorCode());
			
			ex.printStackTrace();
		}

		return arrList;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		pw.println("Page won't accept Get URL");

	}

	private boolean isItTest(String rplStatus) {

		if (rplStatus.compareTo("APPROVED") == 0) {
			return true;
		} else if (rplStatus.compareTo("FAILURE") == 0) {
			return true;
		} else {
			return false;
		}
	}
	
}
