/*
 * @Author:  Dipak A. Mali
 * @Dated:   30-09-2010
 * @Class:   ICICI Payment Gateway 
 * @Param:   MerchantId, Amount, currencycode, txnId, responseUrl, errorUrl
 * @returns: Url of Payment Gateway
 */

package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.StringTokenizer;

import com.opus.epg.sfa.java.EPGCryptLib;
import com.opus.epg.sfa.java.EPGMerchantEncryptionLib;
import com.opus.epg.sfa.java.SFAApplicationException;

public class ICICI {

	public com.opus.epg.sfa.java.BillToAddress oBTA = null;
	public com.opus.epg.sfa.java.ShipToAddress oSTA = null;
	public com.opus.epg.sfa.java.Merchant oMerchant = null;
	public com.opus.epg.sfa.java.MPIData oMPI = null;
	public com.opus.epg.sfa.java.CardInfo oCI = null;

	public com.opus.epg.sfa.java.PostLib oPostLib = null;
	public com.opus.epg.sfa.java.PGReserveData oPGReserveData = null;

	public com.opus.epg.sfa.java.CustomerDetails oCustomer = null;
	public com.opus.epg.sfa.java.MerchanDise oMerchanDise = null;

	public com.opus.epg.sfa.java.SessionDetail oSessionDetail = null; // So that
																		// can
																		// be
																		// done
																		// from
																		// out
																		// side

	public com.opus.epg.sfa.java.Address oHomeAddress = null;
	public com.opus.epg.sfa.java.Address oOfficeAddress = null;
	public com.opus.epg.sfa.java.AirLineTransaction oAirLineTrans = null;

	private String merchantId = null;// 00002601; // Merchant ID

	private String astrMerchantTxnID = null;

	private String astrOrderReferenceNo = null;

	private String astrRespURL = null;

	private String astrCurrCode = null; // Currency code eg. INR, USD, ETC

	private final String astrInvoiceNo = null;

	private final String astrMessageType = "req.Sale"; // req.Preauthorization

	private String astrAmount = "00.00";
	private String astrAmountWithoutDot = "0000";

	private final String astrGMTOffset = "GMT+05:30";

	public ICICI() {

		this.oMerchant = new com.opus.epg.sfa.java.Merchant();
		this.oMPI = new com.opus.epg.sfa.java.MPIData();
		// this.oCI = new com.opus.epg.sfa.java.CardInfo();//only for card Info
		// => MOTO Merchant
		this.oPGReserveData = new com.opus.epg.sfa.java.PGReserveData();
		this.oSessionDetail = new com.opus.epg.sfa.java.SessionDetail();

		try {
			this.oPostLib = new com.opus.epg.sfa.java.PostLib();
		} catch (Exception ex) {
		}
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public void setOrderId(String orderId) {
		this.astrOrderReferenceNo = orderId;
	}

	public void setTxnId(String txnId) {
		this.astrMerchantTxnID = txnId;
	}

	public void setResponseUrl(String responseUrl) {
		this.astrRespURL = responseUrl;
	}

	public void setCurrencyCode(String currencyCode) {
		this.astrCurrCode = currencyCode;
	}

	public void setAmount(String amount) {
		this.astrAmount = amount;

		String arg = amount.replace("$", "");
		arg = amount.replace(".", "");

		this.astrAmountWithoutDot = arg;
	}

	public void setMerchantDetails() {

		this.oMerchant.setMerchantDetails(this.merchantId // Merchant Id
				, this.merchantId // Vendor Id
				, this.merchantId // Partner
				, "000.001.001.000" // Server IP Addr can be null for SSL
									// merchants
				, this.astrMerchantTxnID // MerchantTxnId
				, this.astrOrderReferenceNo // "ORD123" //OrderReferenceId can
											// be null
				, this.astrRespURL // response Url
				, "POST" // response Method
				, this.astrCurrCode // CurrencyCode
				, this.astrInvoiceNo // "INV123" //InvoiceNo can be null
				, this.astrMessageType // "req.Sale" //MessageType
				, this.astrAmount // txnAmount
				, this.astrGMTOffset // "GMT+05:30" //GMT Offset
				, "Ext1" // Extra Param....
				, "true", "Ext3", "Ext4", "Ext5");
	}

	public void setMPIDetails() {

		this.oMPI
				.setMPIRequestDetails(
						this.astrAmountWithoutDot // Unformatted purchase amount
						,
						this.astrAmount // Formatted purchase amount
						,
						this.astrCurrCode // INR //ISO Numeric currency code
						,
						"2" // number of digits after the decimal point in
							// amount
						,
						"2 shirts" // Brief description of items purchased,
									// determined by the Merchant
						,
						"" // Set if Payment is Recurring only
						,
						"" // Set if Payment is Recurring only date(yyyymmdd)
						,
						"1" // Number of Installments
						,
						"0" // indicates mode of transaction
						,
						"" // to denote the browser version.
						,
						"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, application/x-shockwave-flash, */*"
						// The Accept request-header
						, "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)" // The
																				// User-Agent-header
				);
	}

	public void setProductDetails(String arg) {

		if (arg.compareTo("AirLineBooking") == 0) {

			this.oAirLineTrans = new com.opus.epg.sfa.java.AirLineTransaction();
			this.oAirLineTrans.setAirLineTransactionDetails("10-06-2007", // booking
																			// Date
					"22-06-2007", // Flight Date
					"13:20", // Flight Time
					"119", // Flight number
					"Sandeep", // Passenger Name
					"1", // Number if Tickets
					"Y",// PassebgerName and Card name match
					"25c",// PBR
					"Pune", // Sector From
					"Mumbai"// Sector To
			);
		} else {
			this.oMerchanDise = new com.opus.epg.sfa.java.MerchanDise();
			this.oMerchanDise.setMerchanDiseDetails("Computer", // itemPurchased
					"2", // Quantity
					"Intel", // strBrand
					"P4", // ModelNumber
					"Sandeep Patil", // buyers name
					"Y" // Buyername and Card name match.
			);
		}
	}

	public void setCustomerDetails() {

		this.oCustomer = new com.opus.epg.sfa.java.CustomerDetails();
		this.oHomeAddress = new com.opus.epg.sfa.java.Address();
		this.oOfficeAddress = new com.opus.epg.sfa.java.Address();

		this.oHomeAddress.setAddressDetails("2,Sandeep", "Uttam Corner",
				"Chinchwad", "Pune", "state", "4385435873", "IND",
				"test@test.com");

		this.oOfficeAddress.setAddressDetails("2,Opus", "MayFairTowers",
				"Wakdewadi", "Pune", "state", "4385435873", "IND",
				"test@test.com");

		this.oCustomer.setCustomerDetails("Sandeep", // first name
				"patil", // last name
				this.oOfficeAddress, this.oHomeAddress, "9423203297",// mobile
																		// number
				"13-06-2007", // Reg Date
				"Y" // Billing and shipping address match
		);
	}

	public void setBillAddress() {

		this.oBTA = new com.opus.epg.sfa.java.BillToAddress();

		this.oBTA.setAddressDetails("CID", "CustName", "AddrLine1",
				"AddrLine2", "AddrLine3", "city", "state", "523466", "IND",
				"test@test.com");
	}

	public void setShipAddress() {

		this.oSTA = new com.opus.epg.sfa.java.ShipToAddress();

		this.oSTA.setAddressDetails("2 Mitali", "Orion Comp", "Aundh Road",
				"city", "state", "4385435873", "IND", "test@test.com");
	}

	/************************************ ICICI RESPONSE METHOD *******************************************/

	@SuppressWarnings("finally")
	private String validateEncryptedData(String astrResponseData,
			String astrDirectoryPath, String strMerchantId) throws Exception {

		EPGMerchantEncryptionLib oEncryptionLib = new EPGMerchantEncryptionLib();
		String astrClearData = null;
		try {
			FileInputStream oFileInputStream = new FileInputStream(new File(
					astrDirectoryPath + strMerchantId + ".key"));
			BufferedReader oBuffRead = new BufferedReader(
					new InputStreamReader(oFileInputStream));
			String strModulus = oBuffRead.readLine();
			if (strModulus == null) {
				throw new SFAApplicationException(
						"Invalid credentials. Transaction cannot be processed");
			}
			strModulus = this.decryptMerchantKey(strModulus, strMerchantId);
			if (strModulus == null) {
				throw new SFAApplicationException(
						"Invalid credentials. Transaction cannot be processed");
			}
			String strExponent = oBuffRead.readLine();
			if (strExponent == null) {
				throw new SFAApplicationException(
						"Invalid credentials. Transaction cannot be processed");
			}
			strExponent = this.decryptMerchantKey(strExponent, strMerchantId);
			if (strExponent == null) {
				throw new SFAApplicationException(
						"Invalid credentials. Transaction cannot be processed");
			}
			astrClearData = oEncryptionLib.decryptDataWithPrivateKeyContents(
					astrResponseData, strModulus, strExponent);

		} catch (Exception oEx) {
			oEx.printStackTrace();
		} finally {
			return astrClearData;
		}
	}

	private String decryptMerchantKey(String astrData, String astrMerchantId)
			throws Exception {
		return (this.decryptData(astrData, (astrMerchantId + astrMerchantId)
				.substring(0, 16)));
	}

	private String decryptData(String strData, String strKey) throws Exception {
		if (strData == null || strData == "") {
			return null;
		}
		if (strKey == null || strKey == "") {
			return null;
		}

		EPGCryptLib moEPGCryptLib = new EPGCryptLib();
		String strDecrypt = moEPGCryptLib.Decrypt(strKey, strData);

		return strDecrypt;
	}

	public Hashtable response(String astrResponseData,
			String astrDirectoryPath, String strMerchantId) {

		String astrClearData = null;

		try {
			astrClearData = this.validateEncryptedData(astrResponseData,
					astrDirectoryPath, strMerchantId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Hashtable oHashtable = new Hashtable();

		StringTokenizer oStringTokenizer = new StringTokenizer(astrClearData,
				"&");

		while (oStringTokenizer.hasMoreElements()) {
			String strData = (String) oStringTokenizer.nextElement();

			System.out.println("strData is >>>" + strData);

			StringTokenizer oObj1 = new StringTokenizer(strData, "=");
			String strKey = (String) oObj1.nextElement();
			String strValue = (String) oObj1.nextElement();
			oHashtable.put(strKey, strValue);
		}
		return oHashtable;
	}

	public String responseCodeToStatus(int responseCode) {

		String statusCode = "FAILURE";

		switch (responseCode) {

		case 0:
			statusCode = "APPROVED";
			break;
		// statusCode = "UNAPPROVED";
		case 1:
			statusCode = "FAILURE";
			break;

		case 2:
			statusCode = "FAILURE";
			break;
		}
		return statusCode;
	}

}