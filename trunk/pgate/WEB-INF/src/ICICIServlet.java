/*
 * @Author:  Dipak A. Mali
 * @Dated:   30-09-2010
 * @Class:   EServe Payment Gateway 
 * @Param:   MerchantId, Amount, currencycode, trackId, responseUrl, errorUrl
 * @returns: Url of Payment Gateway
 */

//import app.net.Curl;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Config;
import app.ICICI;
import app.Log;

import com.opus.epg.sfa.java.PGResponse;

public class ICICIServlet extends HttpServlet{

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{

		HttpSession session = request.getSession(false);
		/*****************ICICI Servlet**********************************/
		session.setAttribute("usingPG", "ICICI");
		/*****************ICICI Servlet**********************************/
		
		Config.setAllConfig();

		String clientId = request.getParameter("clientId");
		String txnAmount = request.getParameter("txnAmount");
		String orderNo = request.getParameter("orderNo");
		String merchantCode = request.getParameter("merchantCode");
		String currencyCode = request.getParameter("currencyCode");
		String currency = request.getParameter("currency");

		String txnId = session.getAttribute("txnId").toString();
		
		long unixTime = (System.currentTimeMillis() / 1000L);
		txnId = "" + new Long(unixTime).toString();
		
		Log.logger.info("ICICIServlet: Transaction Id: " + txnId);
		
		/************************Response URL***********************************/
		String getProtocol=request.getScheme();
		String getDomain=request.getServerName();
		String getPort=Integer.toString(request.getServerPort());
		  
		String responseURL = getProtocol+"://"+ getDomain + ":" + getPort + Config.get("SITE_PATH") +"/responseServlet";
	
		
		Log.logger.info( "Redirect to this: " + responseURL );
		/***********************Response URL*****************************************/
		
		ICICI icici = new ICICI();
	
		icici.setMerchantId( merchantCode );
		icici.setAmount( txnAmount );
		icici.setCurrencyCode( currency );
		icici.setOrderId(orderNo);
		
		icici.setTxnId( txnId ); //need to be present and Unique
		
		icici.setResponseUrl( responseURL );
		//icici.setBillAddress();
		//icici.setShipAddress();

		icici.oSessionDetail.setSessionDetails(request.getRemoteAddr(), //This Customer ip,merchant need to send it.
							  this.getSecureCookie(request),  //cookie string
							  request.getLocale().getCountry(),
							  request.getLocale().getLanguage(), 
							  request.getLocale().getVariant() ,
							  request.getHeader ("user-agent")
					  );
		
		String urlPG = this.getPaymentGateway(merchantCode, response, icici);
		//pw.println(urlPG);
		if(urlPG != null) {
			response.sendRedirect( urlPG );
		} else{
			PrintWriter pw = response.getWriter();
			pw.println("Welcome you On ICICI Payment Gateway. Something Went Wrong with your Request. Please try later.");

		}
	}

	private String getPaymentGateway(String merchantCode, HttpServletResponse response, ICICI icici) {
		
		icici.setMerchantDetails();
		
		//icici.setMPIDetails();	// Commented By Dipak as it was suggested by PGHELPDESK@ICICIBANK.COM
		
		//icici.setProductDetails("AirLineBooking"); //Set the Parameter as AirLineBooking if it is Airline booking
		//icici.setCustomerDetails();
		//icici.setBillAddress();
		//icici.setShipAddress();
		String strRedirectionURL = null;

		PGResponse oPGResponse = icici.oPostLib.postSSL(icici.oBTA,icici.oSTA,icici.oMerchant,icici.oMPI,response,icici.oPGReserveData,icici.oCustomer,icici.oSessionDetail,icici.oAirLineTrans,null);
		if(oPGResponse.getRedirectionUrl() != null) {
		
			strRedirectionURL = oPGResponse.getRedirectionUrl();
		}
		else {
			String errMsg = "Error encountered. Error Code : " + oPGResponse.getRespCode() + " . Message " +  oPGResponse.getRespMessage();
			Log.logger.severe(errMsg);
			
		}
		return strRedirectionURL;
	}
	

	private String getSecureCookie(HttpServletRequest request) {

		String secureCookie = null;
		
		Cookie[] cookies = request.getCookies();
		
		if (cookies != null) { 
		
			for (int i = 0; i < cookies.length; i++) {
			
				if (cookies[i].getName().equals("vsc")) {
				
					secureCookie = cookies[i].getValue().trim();
					break; 
				}
			}
		}
		return secureCookie;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		PrintWriter pw = response.getWriter();
		pw.println("Page won't accept Get URL");
	}

}
