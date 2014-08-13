/**
 * @author:  Dipak A. Mali
 * @since:   20-04-2011
 * @class:   Bill Desk Payment Gateway 
 * @params:   MerchantId, Amount, currencyType, SecurityId, responseUrl
 * @returns: Url of Payment Gateway
 */

package app;



public class BillDesk {

	private String password = ""; 
	
	private String merchantId = "";
	
	private String txnAmount = "";
	
	private String currencyType = "";
	
	private String securityId = "";
	
	private String returnUrl = "";
	
	private char typeField1 = 'R';
	
	private char typeField2 = 'F';
	
	private String checkSumValue = "0"; 
	
	/**
	 * @value => 
	 * 		CC for credit card		
	 * 		DC for debit card
	 * 		NB for NetBanking
	 * 		CD Cash Card
	 */

	private String additionalInfo = "NB";

	private String postString = "";

	public void createPostString(String customerId) {

		/**
		 * MerchantID|CustomerID|NA|TxnAmount|BankID|NA|NA|CurrencyType|ItemCode|TypeField1|SecurityID
		 * 	|NA|NA|TypeField2|AdditionalInfo1|NA|NA|NA|NA|NA|NA|RU
		 */
		
		this.postString = this.merchantId + "|"+ customerId + "|NA|" + this.txnAmount + "|NA|NA|NA|" +
							this.currencyType + "|NA|" + this.typeField1 + "|" + this.securityId + "|NA|NA|" +
							this.typeField2 + "|" + this.additionalInfo + "|NA|NA|NA|NA|NA|NA|" + this.returnUrl;
	}
	
	
	public void generateCheckSum(String sampleMsg){
		
		sampleMsg = sampleMsg + "|" + this.password;
		
		CRC32 objCrc32 = new CRC32();
		
		int checkSum = objCrc32.crc32(sampleMsg);
		
		if(checkSum < 0 ){
			/**
			 * If Checksum is -ve, then make it unsigned 
			 */
			checkSum = ((-1) * checkSum);
		}

		/*checkSum = (checkSum - 1);*/

		Log.logger.info("The value of checksum is " + checkSum);
	    this.checkSumValue = Integer.valueOf(checkSum).toString();
		
	    /*ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
	    CheckedInputStream cis = new CheckedInputStream(bais, new CRC32());
	    byte readBuffer[] = new byte[5];
	    try {
	    	while (cis.read(readBuffer) >= 0){
			    long value = cis.getChecksum().getValue();
			    Log.logger.info("The value of checksum is " + value);
			    this.checkSumValue = Long.valueOf(value).toString();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	
	

	/**
	 * @return the postString
	 */
	public String getPostString() {
		return this.postString;
	}

	/**
	 * @param postString the postString to set
	 */
	public void setPostString(String postString) {
		this.postString = postString;
	}

	/**
	 * @return the checkSumValue
	 */
	public String getCheckSumValue() {
		return this.checkSumValue;
	}

	/**
	 * @param checkSumValue the checkSumValue to set
	 */
	public void setCheckSumValue(String checkSumValue) {
		this.checkSumValue = checkSumValue;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return this.merchantId;
	}

	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @return the txnAmount
	 */
	public String getTxnAmount() {
		return this.txnAmount;
	}

	/**
	 * @param txnAmount the txnAmount to set
	 */
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = Utility.round(txnAmount);
	}

	/**
	 * @return the currencyType
	 */
	public String getCurrencyType() {
		return this.currencyType;
	}

	/**
	 * @param currencyType the currencyType to set
	 */
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	/**
	 * @return the securityId
	 */
	public String getSecurityId() {
		return this.securityId;
	}

	/**
	 * @param securityId the securityId to set
	 */
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	/**
	 * @return the returnUrl
	 */
	public String getReturnUrl() {
		return this.returnUrl;
	}

	/**
	 * @param returnUrl the returnUrl to set
	 */
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	
	
	
}
