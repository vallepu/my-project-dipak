package app;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Transaction {

	private String strAuthIdCode = "";
	private String strTxnId = "";
	private String orderId = "";
	private String strResponseCode = "";
	private String strMessage = "";
	private String strRRN = "";
	private String strMerchantTxnId = "";
	private String strTxnType = "";
	private String merchantId = "";
	private String paymentId = "";
	private String postdate = "";
	
	public Transaction() {

	}

	public int startTransaction(String clientId, String txnAmount, String currency, String orderNo, 
									String appParam1, String appParam2, String appParam3) {

		int lastInsertId = 0;
		Mysql mysqlObj = new Mysql();

		try {

			String query = "INSERT INTO transaction " +
							" SET currency_no = ?, txn_amount = ?, " + 
							" order_no = ?, client_id = ?, " +
							" client_param1 = ?, client_param2 = ?, client_param3 = ?," +
							" created_date = now(), updated_date = now() ";
			PreparedStatement pstmt = mysqlObj.queryUpdate(query);

			pstmt.setString(1, currency);
			pstmt.setString(2, txnAmount);
			pstmt.setString(3, orderNo);
			pstmt.setString(4, clientId);
			pstmt.setString(5, appParam1);
			pstmt.setString(6, appParam2);
			pstmt.setString(7, appParam3);

			pstmt.executeUpdate(); // issue invalid query
			pstmt.close();

			ResultSet result = mysqlObj
					.queryResult("SELECT LAST_INSERT_ID() AS ID");
			if (result.first()) {
				lastInsertId = result.getInt("ID");
			}

			result.close();
		} catch (SQLException e) {
			System.out.println("Error message: " + e.getMessage());
			System.out.println("Error number: " + e.getErrorCode());
		}catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
		return lastInsertId;
	}

	public void updateTransaction(String merchantId, String pgName,
			String currencyCode, String pgMode, int txnId) {

		Mysql mysqlObj = new Mysql();

		try {
			String query = "UPDATE transaction " + " SET merchant_id = ?, "
					+ " pg_name = ?, " + " currency_code = ?, pg_mode = ?, "
					+ " updated_date = now() " + " WHERE txn_id = ?";
			PreparedStatement pstmt = mysqlObj.queryUpdate(query);
			pstmt.setString(1, merchantId);
			pstmt.setString(2, pgName);
			pstmt.setString(3, currencyCode);
			pstmt.setString(4, pgMode);
			pstmt.setInt(5, txnId);

			pstmt.executeUpdate(); // issue invalid query
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Error message: " + e.getMessage());
			System.out.println("Error number: " + e.getErrorCode());
		}catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public ArrayList updateOfflineTransaction(String merchantId, String pgName,
			String currencyCode, String txnNo, String userName, String merchantName, String bankName, String issueDate, String amount, String pgMode, int txnId) {

		Mysql mysqlObj = new Mysql();
		ArrayList updtarrList = null;

		try {
			String query = "UPDATE transaction " + " SET merchant_id = ?, pg_name = ?, currency_code = ?, "
					+ " txn_no = ?, user_name = ?, merchant_name = ?, bank_name = ?, issue_date = ?, txn_amount = ?, pg_mode = ?, "
					+ " updated_date = now() " + " WHERE txn_id = ?";
			PreparedStatement pstmt = mysqlObj.queryUpdate(query);
			pstmt.setString(1, merchantId);
			pstmt.setString(2, pgName);
			pstmt.setString(3, currencyCode);
			pstmt.setString(4, txnNo);
			pstmt.setString(5, userName);
			pstmt.setString(6, merchantName);
			pstmt.setString(7, bankName);
			pstmt.setString(8, issueDate);
			pstmt.setString(9, amount);
			pstmt.setString(10, pgMode);
			pstmt.setInt(11, txnId);

			pstmt.executeUpdate(); // issue invalid query
			pstmt.close();
			
			String selsql = "SELECT txn.txn_id AS txnId, txn.currency_code, txn.currency_no AS currency, txn.txn_amount AS txnAmount, txn.order_no orderId, txn.pg_name AS txnType, clnt.success_url AS respUrl, txn.merchant_id, clnt.client_name, txn.status AS " +
			" status , txn.comment AS respComment, txn.txn_no AS txn_no, txn.created_date, txn.pg_name AS txnMode, txn.client_param1 AS appParam1, txn.client_param2 AS appParam2, txn.client_param3 AS appParam3, txn.bank_name, txn.issue_date, txn.pg_mode " +
			" FROM transaction txn, client_master clnt " +
			" WHERE txn.client_id = clnt.client_id " +
			" AND txn.txn_id = " + txnId;
			
			updtarrList =  mysqlObj.resultSetToArrayList(selsql);
			
		} catch (SQLException e) {
			System.out.println("Error message: " + e.getMessage());
			System.out.println("Error number: " + e.getErrorCode());
		}catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
		return updtarrList;
	}

	public void updateTxnStatus(String status, String comment, int txnId) {
		Mysql mysqlObj = new Mysql();

		try {
			String query = "UPDATE transaction SET status = ?, " + 
							" comment = ? " + 
							" WHERE txn_id = ?";
			PreparedStatement pstmt = mysqlObj.queryUpdate(query);
			pstmt.setString(1, status);
			pstmt.setString(2, comment);
			pstmt.setInt(3, txnId);

			pstmt.executeUpdate(); // issue invalid query
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Error message: " + e.getMessage());
			System.out.println("Error number: " + e.getErrorCode());
		}catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void setAuthIdCode(String strAuthIdCode) {
		this.strAuthIdCode = strAuthIdCode;
	}

	public void setTxnId(String strTxnId) {
		this.strTxnId = strTxnId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setResponseCode(String strResponseCode) {
		this.strResponseCode = strResponseCode;
	}

	public void setMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	public void setRRN(String strRRN) {
		this.strRRN = strRRN;
	}

	public void setMerchantTxnId(String strMerchantTxnId) {
		this.strMerchantTxnId = strMerchantTxnId;
	}

	public void setTxnType(String strTxnType) {
		this.strTxnType = strTxnType;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	public void setPostDate(String postdate) {
		this.postdate = postdate;
	}

	public void logTransaction() {

		Mysql mysqlObj = new Mysql();

		try {
			String query = "INSERT INTO txn_log " + " SET merchant_id = ?, "
					+ " response_code = ?, " + " response_message = ?, "
					+ " txn_ref_no = ?, " + " merchant_txn_id = ?, "
					+ " auth_id_code = ?, " + " rrn_no = ?, "
					+ " txn_type = ?, " + " order_no = ?, "
					+ " payment_id = ?, postdate = ?";

			PreparedStatement pstmt = mysqlObj.queryUpdate(query);
			pstmt.setString(1, this.merchantId);
			pstmt.setString(2, this.strResponseCode);
			pstmt.setString(3, this.strMessage);
			pstmt.setString(4, this.strTxnId);

			pstmt.setString(5, this.strMerchantTxnId);
			pstmt.setString(6, this.strAuthIdCode);
			pstmt.setString(7, this.strRRN);
			pstmt.setString(8, this.strTxnType);
			
			pstmt.setString(9, this.orderId);
			pstmt.setString(10, this.paymentId);
			pstmt.setString(11, this.postdate);
				
			pstmt.executeUpdate(); // issue invalid query
			pstmt.close();
		} catch (Exception e) {
			// log err
		}
	}

	public String getClientName(String clientId){
		String clientName = null;
		
		try{
			Mysql mysqlObj = new Mysql();
			
			String query = "SELECT * FROM `client_master` WHERE `client_id` = "
					+ clientId;
			ResultSet result = mysqlObj.queryResult(query);
			if (result.first()) {
				clientName = result.getString("client_name");
			}
			result.close();
			
		}catch (SQLException e) {
			System.out.println("Error message: " + e.getMessage());
			System.out.println("Error number: " + e.getErrorCode());
		}catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
		return clientName;
		
	}
	
	
	public ArrayList<String> getAllClientUrl() {
		ArrayList<String> arrClientUrl = new ArrayList<String>();
		Mysql mysqlObj = new Mysql();
		String query = "SELECT cliet_url FROM client_master";

		try {
			ResultSet result = mysqlObj.queryResult(query);
			while (result.next()) {
				String url = result.getString("cliet_url");
				arrClientUrl.add(url);
			}
			result.close();
		}catch(SQLException e){
				System.out.println("Error message: " + e.getMessage());
				System.out.println("Error number: " + e.getErrorCode());
		}catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
		return arrClientUrl;
	}

	public String getResponseUrl(String clientId) {

		String respUrl = null;
		Mysql mysqlObj = new Mysql();
		String query = "SELECT success_url FROM client_master WHERE client_id = "
				+ clientId;
		try {
			ResultSet result = mysqlObj.queryResult(query);
			if (result.next()) {
				respUrl = result.getString("success_url");
			}
			result.close();
		} catch (SQLException e) {
			System.out.println("Error message: " + e.getMessage());
			System.out.println("Error number: " + e.getErrorCode());
		}catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
		return respUrl;
	}
}