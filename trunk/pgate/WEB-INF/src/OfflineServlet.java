import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Config;
import app.Log;
import app.OfflinePayment;
import app.Transaction;
import app.Utility;

/**
 * @author deepak.mali
 *
 */
@SuppressWarnings("serial")
public class OfflineServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
									throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		String clientId = (String) session.getAttribute("clientId");
		String txnMode = (String) session.getAttribute("txnMode");
		
		try{
			if(clientId != null){
				
				ArrayList<Object> arrCurrencyList = OfflinePayment.currencyList();
		
				request.setAttribute("arrCurrencyList", arrCurrencyList);
		
				String url = "/offlinepayment.jsp";
		
				try {
					this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(request, response);
				} catch (Exception ex) {
					Log.logger.severe("Error: " +ex.getMessage());
					
				}
			}else{
				PrintWriter out = response.getWriter();
				out.println("Session Expired!");
			}
		}catch (NullPointerException e) {
			PrintWriter out = response.getWriter();
			System.out.println("Error: " + e.getMessage());
			out.println("Session Expired!");
		}catch (Exception e) {
			PrintWriter out = response.getWriter();
			System.out.println("Error: " + e.getMessage());
			out.println("Something went Wrong. \nPlease try again!");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
									throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);
		
		try{
			String clientId = (String) session.getAttribute("clientId");
			String orderNo = (String) session.getAttribute("orderId");
			String merchantTxnId = session.getAttribute("txnId").toString();
			int txnId = Integer.parseInt(merchantTxnId);
			
			if(clientId != null){
				//Commented by Asma on 21032011 for a consolidate Online & Offline Payment options
				//String mop = req.getParameter("mop");
				String mop = req.getParameter("txnMode");
				String txnNo = req.getParameter("txn_no");
				String userName = req.getParameter("user_name");
				String merchantName = req.getParameter("merchant_name");
				String bankName = req.getParameter("bank_name");
				String issueDate = req.getParameter("issue_date");
				String currencys = req.getParameter("currency_no");
				String amount = req.getParameter("amount");
				String pgMode = (String) session.getAttribute("pgMode");
				
				String amountReq =  (String) session.getAttribute("txnAmount");
				
				int amtRequired = Integer.parseInt(amountReq);
				int amtPaid = Integer.parseInt(amount);
				
				
				Log.logger.info("int req amt " + amtRequired + "\n Amt paid " + amtPaid);
				if (amtPaid < amtRequired) {//check if amount less than the actual amount to be paid
					//Redirect with err
					String error = "amount must be greater than "+ amountReq ;
					String url = Config.get("SITE_PATH") + "/OfflineServlet?error=" + error ;
					Utility.redirect(resp, url);
					return;
				}
				 
				session.setAttribute("txnAmount", amount);
				  
				/*OfflinePayment objOfflinePayment = new OfflinePayment();
				objOfflinePayment.setMop(mop);
				objOfflinePayment.setRef_no(txn_no);
				objOfflinePayment.setDrawer(user_name);
				objOfflinePayment.setPayee(merchant_name);
				objOfflinePayment.setDrawee(bank_name);
				objOfflinePayment.setIssue_date(issue_date);
				objOfflinePayment.setCurrency(currency);
				objOfflinePayment.setAmount(amount);
				objOfflinePayment.setMerchant_txn_id(merchantTxnId);
				objOfflinePayment.setOrder_no(orderNo);
				objOfflinePayment.save();
				int lastInsertId = objOfflinePayment.getLastInserId();*/
				
				Transaction txnObject = new Transaction();
				String merchantId = "";
				String pgName = mop;
				String currencyCode = "";
				
				txnObject.updateOfflineTransaction(merchantId, pgName, currencyCode, txnNo, userName, merchantName, bankName, issueDate, amount, pgMode, txnId);
				
				ArrayList updtarrList = txnObject.updateOfflineTransaction(merchantId, pgName, currencyCode, txnNo, userName, merchantName, bankName, issueDate, amount, pgMode, txnId);

				if(!(updtarrList.isEmpty())){
					HashMap<String, Object> objField = null;
					
					Iterator i = updtarrList.iterator();
					objField = new HashMap<String, Object>();
					//System.out.println("All of the mappings   " + updtarrList);
					//while (i.hasNext()){
						objField.putAll((HashMap)i.next());
						
						String respUrl = (String) objField.get("success_url");
						String status = (String) objField.get("status");
						String respComment = (String) objField.get("comment");
						String orderId = (String) objField.get("order_no");
						String txnType = (String) objField.get("pg_mode");
						String txnNum = (String) objField.get("txn_no");
						String pgNames = (String) objField.get("pg_name");
						String bank_name = (String) objField.get("bank_name");
						Date issue_date = (Date) objField.get("issue_date");
						String txnMode = pgNames + "-" + txnNum + "|" + bank_name + "|" + issue_date;
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
					//}
				}else {
						
						Log.logger.info("in else: " + updtarrList.toString());
				}
				
				
				String updturl = "/responsePG.jsp";

				try {
					this.getServletConfig().getServletContext().getRequestDispatcher(updturl).forward(req, resp);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
				
				/***Redirect to Response Servlet***/
				String url = Config.get("SITE_PATH") + "/OfflineResponse?id=" + txnId;
				System.out.println("URL is:- "+url + "id " +txnId);
				resp.sendRedirect(resp.encodeRedirectURL(url));
			}else{
				PrintWriter out = resp.getWriter();
				out.println("Client Id can not be NULL. \nPlease try again! ");
			}
		}catch (NullPointerException e) {
			PrintWriter out = resp.getWriter();
			System.out.println("Error: " + e.getMessage());
			out.println("Session Expired!");
		}catch (Exception e) {
			PrintWriter out = resp.getWriter();
			System.out.println("Error: " + e.getMessage());
			out.println("Something went Wrong. \nPlease try again!");
			e.printStackTrace();
		}
		
	}

}
