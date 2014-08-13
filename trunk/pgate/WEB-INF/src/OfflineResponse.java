import java.io.IOException;
import java.util.ArrayList;
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
 * 
 */

/**
 * @author deepak.mali
 *
 */
public class OfflineResponse extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
								throws ServletException, IOException {
/******************************* CLIENT ACTION RESPONSE PAGE ********************************************/
		HttpSession session = request.getSession(false);

		try{
			
			String orderId = (String) session.getAttribute("orderId");
			String txnId = null;//(String) session.getAttribute("txnId");
			String clientId = (String) session.getAttribute("clientId");
			String txnMode = null;
			String txnAmount = (String)session.getAttribute("txnAmount");
			String currency = (String)session.getAttribute("currency");
			
			Object id = request.getParameter("id");
			String txnType = "Offline"; 
			String respComment = "", status = "";
			if(clientId != null){
				
				Transaction txnObject = new Transaction();
				String respUrl = txnObject.getResponseUrl(clientId);
				
				OfflinePayment objOffpay = new OfflinePayment();
				String whrClause = " id='" + id.toString().trim() + "'";
				ArrayList<OfflinePayment> listResult = objOffpay.OfflinePaymentList(whrClause);
				
				
				if(!(listResult.isEmpty())){
					
					Iterator<OfflinePayment> i = listResult.iterator();
					
					while (i.hasNext()) {
						OfflinePayment type = i.next();
						txnId = type.getMerchant_txn_id();
						status = type.getStatus();
						respComment = type.getComment();
						txnMode = type.getMop();
					}
				}else {
					
					Log.logger.info("in else: " + listResult.toString());
				}
				
				if(respUrl != null){
					request.setAttribute("respUrl", respUrl);
					request.setAttribute("status", status);
					request.setAttribute("txnId", txnId);
					request.setAttribute("orderId", orderId);
					request.setAttribute("respComment", respComment);
					request.setAttribute("txnAmount", txnAmount);
					request.setAttribute("currency", currency);
					
					String appParam1 = (String)session.getAttribute("appParam1");
					String appParam2 = (String)session.getAttribute("appParam2");
					String appParam3 = (String)session.getAttribute("appParam3");
					request.setAttribute("txnMode", txnMode);
					request.setAttribute("txnType", txnType);
					request.setAttribute("appParam1", appParam1);
					request.setAttribute("appParam2", appParam2);
					request.setAttribute("appParam3", appParam3);
					
					String url = "/responsePG.jsp";

					session.invalidate();

					try {
						this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(request, response);
					}
					catch (Exception ex) {
						Log.logger.severe("Error: " + ex.getMessage());
						
					}
				}else{
					String url = Config.get("SITE_PATH") + "/error.jsp?error=client Url is empty. <br/>Kindly check with the configuration.";
					Utility.redirect(response, url);
				}
			}
			else{
				String url = Config.get("SITE_PATH") + "/error.jsp?error=client Id is null.";
				Utility.redirect(response, url);
			}
		}catch (NullPointerException e) {
			System.out.println("Error: " + e.getMessage());
			String url = Config.get("SITE_PATH") + "/error.jsp?error=Session Timeout!";
			Utility.redirect(response, url);
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
			String url = Config.get("SITE_PATH") + "/error.jsp?error=Something went Wrong. Please try again!";
			Utility.redirect(response, url);
			
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
								throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
