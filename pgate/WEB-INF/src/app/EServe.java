/*
 * @author:  Dipak A. Mali
 * @dated:   30-09-2010
 * @class:   EServe Payment Gateway 
 * @params:   MerchantId, Amount, currencycode, txnId, responseUrl, errorUrl
 * @returns: Url of Payment Gateway
 */

package app;

import app.net.Curl;

public class EServe{

	private String merchantId = "90000953";// "90000997"; // Merchant ID

	private String password = "Password1!"; // Merchant ID Password
	private int action = 1; // 1 - Purchase, 2 - Credit, 3 - Void Purchase, 4 - Authorization, 5 - Capture, 6 - Void Credit, 7 - Void Capture
							//8 - Query, 9 - Void Authorization


	private String type = "CC"; // Define the type of Card. Default CC (Credit
								// Card)
	private String language = "USA"; // Default Language set to USA

	private String amount = null; // "$1"; // Total Amount //$Amount = 1; //
									// Total Amount
	private String currencyCode = null; // "840"; // ISO 4217 Currency Code for
										// Currency Name. For e.g USD
	private String txnId = null; // "00001"; // Should be Unique ID and
									// generated everytime // txnId Unique

	private String postString = null;
	private String responseURL = null;
	private String errorUrl = null;
	public boolean ERROR = false;

	public EServe() {

	}

	private void createPostString() {
		this.postString = "id=" + this.merchantId + "&password=" + this.password + "&action=" + this.action + 
						"&type=" + this.type + "&amt=" + this.amount + "&currencycode=" + this.currencyCode + 
						"&trackid=" + this.txnId + "&Language=" + this.language + "&responseurl=" + this.responseURL +
						"&errorurl=" + this.errorUrl;
	}

	private String getPostString() {

		return this.postString;
	}

	public void setMerchantId(String merchantId) {

		this.merchantId = merchantId;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public void setResponseUrl(String responseUrl) {
		this.responseURL = responseUrl;
		this.errorUrl = responseUrl;

	}

	public String getPaymentGateway() {

		/*
		 * if(this.validateInput()){ return this.err; }
		 */

		this.createPostString();

		String targetUrl = "https://egate.sbmonline.com:443/egate/servlet/PaymentInitHTTPServlet";

		Curl curlObj = new Curl();
		String curlData = curlObj.excutePost(targetUrl, this.getPostString(), "POST");

		String dataArr[] = curlData.split(":");
		// String url = toons[1];

		/*
		 * for (String i : dataArr) { System.out.println("URL is>>> " + i); }
		 * System.out.println(dataArr[0]);
		 */

		if(curlData.startsWith("ERROR")) {
			this.ERROR = true;
		}

		String TKn = dataArr[0];

		String url = "https://egate.sbmonline.com/egate/hppaction?formAction=com.aciworldwide.commerce.gateway.payment.action.HostedPaymentPageAction&PaymentID=" + TKn;

		return url;// + "&" + this.getPostString();
		// response.sendRedirect("https://egate.sbmonline.com/egate/hppaction?formAction=com.aciworldwide.commerce.gateway.payment.action.HostedPaymentPageAction&PaymentID="+TKn");

	}
	
	@SuppressWarnings("finally")
	public static String responseCodeToStatus(String responseCode) {

		String statusCode = "FAILURE";

		try{
			if(	responseCode.equalsIgnoreCase("CAPTURED") || responseCode.equalsIgnoreCase("APPROVED") || 
					responseCode.equalsIgnoreCase("SUCCESS"))
			{
				
				statusCode = "APPROVED";
			}else 
			{
				statusCode = "FAILURE";
			}
		}catch (Exception e) {
			// TODO: handle exception
			//Null pointer Exception
		}finally{
			return statusCode;
		}
	}

	

}
