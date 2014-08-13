/*
 * @Author:  Dipak A. Mali
 * @Dated:   30-09-2010
 * @Class:   Response From Payment Gateway 
 * @Param:   MerchantId, Amount, currencycode, trackId, responseUrl, errorUrl
 * @returns: Url of Application
 */

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Config;
import app.EServe;
import app.ICICI;
import app.Log;
import app.Transaction;
import app.Utility;

public class responseServlet extends HttpServlet{

	public String[] responseICICI(String strMerchantId, String astrResponseData, String orderId){
		
		String astrDirectoryPath = Config.get("ICICI_KEY_DIR_PATH");
		//String astrClearData = null;
		
		ICICI icici = new ICICI();
		
		Hashtable oHashtable= icici.response(astrResponseData,astrDirectoryPath,strMerchantId);

		String strResponseCode = (String) oHashtable.get("RespCode");
		String strMessage = ((String)oHashtable.get("Message")).replace('+',' ');
		String strMerchantTxnId = (String)oHashtable.get("TxnID");
		String strTxnId = (String)oHashtable.get("ePGTxnID");
		String strAuthIdCode = (String) oHashtable.get("AuthIdCode");
		String strRRN =  (String)oHashtable.get("RRN");
		String strTxnType = (String)oHashtable.get("TxnType");
		String FDMSResult	= (String)oHashtable.get("FDMSResult");
		String FDMSScore 	= (String)oHashtable.get("FDMSScore");

		Transaction txnObject = new Transaction();
		txnObject.setAuthIdCode( strAuthIdCode );
		txnObject.setTxnId( strTxnId );
		txnObject.setOrderId( orderId );
		
		txnObject.setResponseCode( strResponseCode );
		txnObject.setMessage( strMessage );
		txnObject.setRRN( strRRN );
		txnObject.setMerchantTxnId( strMerchantTxnId );
		txnObject.setTxnType( strTxnType );
		txnObject.setMerchantId( strMerchantId );
		txnObject.logTransaction();

		int respCode = Integer.parseInt(strResponseCode);
		String statusCode = icici.responseCodeToStatus(respCode);

		String returnString[] = new String[3];

		returnString[0] = statusCode;
		returnString[1] = strMessage;
		returnString[2] = strTxnType;
		return returnString;
	}
/*
 * @Capture the Response of PG's 
 * 
 */
public void doCaptureTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		
		Config.setAllConfig();
		
		String usingPG = session.getAttribute("usingPG").toString();
		String merchantCode = (String) session.getAttribute("merchantCode");
		String orderId = (String) session.getAttribute("orderId");
		Integer txnId = (Integer) session.getAttribute("txnId");
		String clientId = (String) session.getAttribute("clientId");
		
		String status = null; 
		String respComment = null;
		String strTxnType = null;
		
		if(usingPG.equalsIgnoreCase("ICICI")){
			
			Log.logger.info("proceed to ICICI");
			/***********************************SOF ICICI RESPONSE*****************************************************/
			String astrResponseData = request.getParameter("DATA");
			String resultString[] = this.responseICICI(merchantCode, astrResponseData, orderId);
			status = resultString[0];
			respComment = resultString[1];
			strTxnType = resultString[2];
			/**************************************BOF ICICI RESPONSE************************************************/
			
		}else if(usingPG.equalsIgnoreCase("EServe")){
			
			Log.logger.info("proceed to Eserve");
			/***********************************BOF EServe RESPONSE*****************************************************/
			String paymentId = request.getParameter ("paymentId");
			
			if(paymentId == null){
				Log.logger.info("Payment response null");
				Transaction objTxn = new Transaction();
				String responseErrorURL = objTxn.getResponseUrl(clientId);
				responseErrorURL += "?ORDER_ID=" + orderId;
				response.sendRedirect(response.encodeRedirectURL(responseErrorURL));
				System.exit(0);
			}//if closed paymentId != null
			
			String strMessage = null;
			String strResponseCode = null;

			String ErrorText=request.getParameter("ErrorText");

			String result = request.getParameter("result");	//Message
			String postdate = request.getParameter("postdate");	//

			strMessage = result;
			strResponseCode = EServe.responseCodeToStatus(result);//"APPROVED";
			
			status = strResponseCode;
			respComment	= strMessage;
			
			if(ErrorText != null){
				String errMsg[] = ErrorText.split("-");
				respComment = errMsg[2];
				strMessage = ErrorText;
			}
			
			String strMerchantId = merchantCode; // Merchant Id
			String strTxnId = request.getParameter("tranid");	//Payment Gateway TranId
			String strAuthIdCode = request.getParameter("auth");	//Auth Code
			String strMerchantTxnId = request.getParameter("trackid"); // Merchant Transaction Id
			String strRRN = request.getParameter("ref");	//RRN NO
			/*String udf1=request.getParameter ("udf1");
			String udf2=request.getParameter ("udf2");
			String udf3=request.getParameter ("udf3");
			String udf4=request.getParameter ("udf4");
			String udf5=request.getParameter ("udf5");*/

			Transaction txnObject = new Transaction();
				txnObject.setAuthIdCode( strAuthIdCode );
				txnObject.setTxnId( strTxnId );
				txnObject.setOrderId( orderId );
				
				txnObject.setResponseCode( strResponseCode );
				txnObject.setMessage( strMessage );
				txnObject.setRRN( strRRN );
				txnObject.setMerchantTxnId( strMerchantTxnId );
				txnObject.setTxnType( strTxnType );
				txnObject.setMerchantId( strMerchantId );
				txnObject.setPaymentId(paymentId);
				txnObject.setPostDate (postdate);
				txnObject.logTransaction();

			/***********************************EOF EServe RESPONSE*****************************************************/
			}
		

		Transaction txnObject = new Transaction();
		txnObject.updateTxnStatus(status, respComment, txnId);

/******************************* CLIENT ACTION RESPONSE PAGE ********************************************/
		
		String respUrl = txnObject.getResponseUrl(clientId);
		
/********************************************************************************************************/

		if(respUrl != null){
			request.setAttribute("status", status);
			request.setAttribute("txnId", txnId);
			request.setAttribute("orderId", orderId);
			request.setAttribute("respComment", respComment);
			request.setAttribute("respUrl", respUrl);
			
			String txnMode = (String)session.getAttribute("txnMode");
			String txnType = (String)session.getAttribute("txnType");
			String appParam1 = (String)session.getAttribute("appParam1");
			String appParam2 = (String)session.getAttribute("appParam2");
			String appParam3 = (String)session.getAttribute("appParam3");
			String txnAmount = (String)session.getAttribute("txnAmount");
			String currency = (String)session.getAttribute("currency");
			
			
			request.setAttribute("txnMode", txnMode);
			request.setAttribute("txnType", txnType);
			request.setAttribute("appParam1", appParam1);
			request.setAttribute("appParam2", appParam2);
			request.setAttribute("appParam3", appParam3);
			request.setAttribute("txnAmount", txnAmount);
			request.setAttribute("currency", currency);
			
			session.invalidate();
			
			String url = "/responsePG.jsp";
			try {
				// Set the attribute and Forward to hello.jsp
				this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(request, response);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else{
			String url = Config.get("SITE_PATH") + "/error.jsp";
			response.sendRedirect( url );
		}
		

 /***********************RESPONSE BY ITZ *******************************/
  /******
  JAVA Response Page
String orderId = request.getParameter("orderid");
String amount = request.getParameter("productcost");
String respCode = request.getParameter("responsecode");
String desc = request.getParameter("description");
String resp = request.getParameter("actiontype");
String txnId = request.getParameter("transactionid");
***************/
	}

 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 
	 HttpSession session = request.getSession(false);
	 try{
		 String usingPG = session.getAttribute("usingPG").toString();
	 	 this.doCaptureTransaction(request, response);
	 	 
	 }catch (NullPointerException e) {
		Log.logger.warning("NullPointerException: " + e.getMessage());
		String param = "Session expired.<br>Please try again!";
		String url = Config.get("SITE_PATH") + "/error.jsp?error=" + param;
		Utility.redirect(response, url);
	}catch (Exception e) {
		Log.logger.warning("Exception: " + e.getMessage());
		String param = "Something went wrong.<br> Please try again!";
		String url = Config.get("SITE_PATH") + "/error.jsp?error=" + param;
		Utility.redirect(response, url);
	}
 }
 
 
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}