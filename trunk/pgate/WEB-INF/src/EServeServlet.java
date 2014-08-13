/**
 * @author:  Dipak A. Mali
 * @since:   30-09-2010
 * @Class:   EServe Payment Gateway 
 * @Param:   MerchantId, Amount, currencycode, trackId, responseUrl, errorUrl
 * @returns: Url of Payment Gateway
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Config;
import app.Log;
import app.Transaction;

import com.aciworldwide.commerce.gateway.plugins.e24PaymentPipe;

public class EServeServlet extends HttpServlet{

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		
		HttpSession session = request.getSession(false);
		Config.setAllConfig();
		/*****************EServe Servlet**********************************/
		session.setAttribute("usingPG", "EServe");
		/*****************EServe Servlet**********************************/

		String clientId = request.getParameter("clientId");
		String txnAmount = request.getParameter("txnAmount");
		String orderNo = request.getParameter("orderNo");
		String merchantCode = request.getParameter("merchantCode");
		String currencyCode = request.getParameter("currencyCode");

		String txnId = session.getAttribute("txnId").toString();

		/************************Response URL***********************************/
		String getProtocol=request.getScheme();
		String getDomain=request.getServerName();
		String getPort=Integer.toString(request.getServerPort());
		
		String responseErrorURL = getProtocol+"://"+ getDomain + ":" + getPort + Config.get("SITE_PATH") +"/responseServlet";
		String responseURL = getProtocol+"://"+ getDomain + ":" + getPort + Config.get("SITE_PATH") +"/eserveResult.jsp";
		//String responseErrorURL = getProtocol+"://"+ getDomain + ":" + getPort + Config.get("SITE_PATH") +"/error.jsp";
		
		/*Transaction objTxn = new Transaction();
		String responseErrorURL = objTxn.getResponseUrl(clientId);*/
	
		Log.logger.info("CurrencyCode:  " + currencyCode + " \n:Redirect to this: " + responseURL );
		/***********************Response URL*****************************************/

		e24PaymentPipe pipe = new e24PaymentPipe();
		
		pipe.setResourcePath (Config.get("ESERVE_RESOURCE"));
		pipe.setAlias (Config.get("ESERVE_ALIAS"));//HDFC VISA Payment Mode
		pipe.setAction (Config.get("ESERVE_ACTION"));
		
		pipe.setAmt (txnAmount);
		pipe.setCurrency (currencyCode);
		pipe.setResponseURL (responseURL);
		pipe.setErrorURL (responseErrorURL);
		pipe.setTrackId (txnId);
		//pipe.Udf1 = "Your User Defined Field 1";
		//pipe.Udf2 = "Your User Defined Field 2";
		//pipe.Udf3 = "Your User Defined Field 3";

		try{
			if(pipe.performPaymentInitialization() != e24PaymentPipe.SUCCESS) {
				String error = "Error sending Payment Initialization Request: ";
				String debug = "&debug=" + pipe.getDebugMsg();
				String errMsg = "&err=" + pipe.getErrorMsg();
				
			
				Log.logger.info(pipe.getDebugMsg());
				Log.logger.info(pipe.getErrorMsg());
				Log.logger.info(error);
				
				error = error + debug + errMsg;
				
				response.sendRedirect( response.encodeRedirectURL("error.jsp?error=" + error) );
				return;
			}
		}catch(Exception e){
			
			Log.logger.severe(" Exception." + e.getMessage());
		}
		
		Log.logger.info(" PaymentInit Sent and Response Received.");
		
		String payID = pipe.getPaymentId();
		String payURL = pipe.getPaymentPage();
		
		
		Log.logger.info(payID + " : payId \n payURL : " + payURL);
		response.sendRedirect( payURL + "?PaymentID=" + payID );


		/*
		EServe eServePG = new EServe();
	
		eServePG.setMerchantId( merchantCode );
		eServePG.setAmount( txnAmount );
		eServePG.setCurrencyCode( currencyCode );
		
		eServePG.setTxnId( txnId ); //has to be unique TxnId
		
		eServePG.setResponseUrl( responseURL );
		
		String urlPG = eServePG.getPaymentGateway();
		
		if(urlPG != null){
			response.sendRedirect( urlPG );
		}else{
			PrintWriter pw = response.getWriter();
			pw.println("Welcome you On EServe Payment Gateway. Something Went Wrong with your Request. Please try later.");

		}
		*/
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		PrintWriter pw = response.getWriter();
		pw.println("Page won't accept Get URL");
	}

}
