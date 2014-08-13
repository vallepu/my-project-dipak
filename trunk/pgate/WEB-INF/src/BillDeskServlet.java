/**
 * @author:  Dipak A. Mali
 * @since:   20-04-2011
 * @Class:   Bill Desk Payment Gateway 
 * @Param:   MerchantId, Amount, currencycode, trackId, responseUrl
 * @returns: Url of Payment Gateway
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.BillDesk;
import app.Config;
import app.Log;


public class BillDeskServlet extends HttpServlet {

	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);
		Config.setAllConfig();
		/*****************BillDesk Servlet**********************************/
		session.setAttribute("usingPG", "BillDesk");
		/*****************BillDesk Servlet**********************************/
		
		String clientId = req.getParameter("clientId");
		String txnAmount = req.getParameter("txnAmount");
		String orderNo = req.getParameter("orderNo");
		String merchantCode = req.getParameter("merchantCode");
		String password = req.getParameter("password");
		String securityId = req.getParameter("securityId");
		
		String currency = req.getParameter("currency");
		
		
		
		String txnId = session.getAttribute("txnId").toString();


		/************************Response URL***********************************/
		String getProtocol=req.getScheme();
		String getDomain=req.getServerName();
		String getPort=Integer.toString(req.getServerPort());
		
		String responseURL = getProtocol+"://"+ getDomain + ":" + getPort + Config.get("SITE_PATH") +"/responseServlet";
	
		Log.logger.info("Currency:  " + currency + " \n:Redirect to this: " + responseURL );
		/***********************Response URL*****************************************/
		
		BillDesk objBillDesk = new BillDesk();
		
		objBillDesk.setMerchantId(merchantCode);
		objBillDesk.setPassword(password);
		objBillDesk.setSecurityId(securityId);
		
		objBillDesk.setTxnAmount(txnAmount);
		objBillDesk.setCurrencyType(currency);
		
		objBillDesk.setReturnUrl(responseURL);
		
		objBillDesk.createPostString(orderNo);
		
		String sampleMsg = objBillDesk.getPostString();
		
		objBillDesk.generateCheckSum(sampleMsg);
	
		String checkSumValue = objBillDesk.getCheckSumValue();
		
		sampleMsg = sampleMsg + "|" + checkSumValue;
		
		sampleMsg = "SHOPTUDROP|0STUCND1|NA|2.0|NA|NA|NA|INR|NA|R|shoptudrop|NA|NA|F|NB|NA|NA|NA|NA|NA|NA|http://develop.coxandkings.com:80/pgate/responseServlet|289789063";

		Log.logger.info("Bill Desk Post Msg with checkSum => "  + sampleMsg);
		
		/********************* Submitting HTML Form ***********************************/
		String js = "<script type='text/javascript'>" +
					"document.onload = document.billdesk.submit();" +
					"</script>";
		
		String htmlStart = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Frameset//EN'" +
						"'http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd'>" +
						"<html xmlns='http://www.w3.org/1999/xhtml'" +
						"xml:lang='en' lang='en' dir='ltr'>" +
						"<head>  </head>";
		
		String form = "<body>" +
						"<form name='billdesk' method='POST' action='" + Config.get("BILLDESK_URL") + "'>" + 
						"<input type='hidden' name='msg' value='" + sampleMsg + "'>" + 
						"</form>" + js + 
					"</body>";
		
		String htmlEnd = "</html>";
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		out.println(htmlStart + form + htmlEnd);
		//out.println(js);
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		pw.println("Page won't accept Get URL");
	}
}
