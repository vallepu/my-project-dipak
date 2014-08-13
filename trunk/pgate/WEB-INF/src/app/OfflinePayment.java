
package app;

/**
 * @author deepak.mali
 *
 */
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class OfflinePayment {

	protected int id = 0;

	protected String merchant_txn_id = null;
	protected String order_no = null;
	
	protected String mop = null;
	protected String ref_no = null;
	protected String drawer = null;
	protected String payee = null;
	protected String drawee = null;
	protected Date issue_date = null;
	protected String currency = null;
	protected String amount = null;
	protected String status = "UNAPPROVED";
	protected String comment = null;
	protected String created_date = null;
	protected String modified_date = null;
	

	//private Mysql dbObj = null;

	public OfflinePayment() {
		
	}
	
	public void save(){
		
		Mysql dbObj = new Mysql();
		PreparedStatement pstmt = null;
	
		try {
			if(this.id == 0){
				String query = "INSERT INTO `payment_gateway`.`offline_payment` ( `merchant_txn_id`, `order_no`, `mop`,`ref_no`, `drawer`, `payee`, `drawee`, " +
				"`issue_date`, `currency`, `amount`, `status`, `comment`, `modified_date`) " + 
				" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()); ";

				pstmt = dbObj.queryUpdate(query);
				pstmt.setString(1, this.merchant_txn_id);
				pstmt.setString(2, this.order_no);
				pstmt.setString(3, this.mop);
				pstmt.setString(4, this.ref_no);
				pstmt.setString(5, this.drawer);
				pstmt.setString(6, this.payee);
				pstmt.setString(7, this.drawee);
				pstmt.setDate(8, this.issue_date);
				pstmt.setString(9, this.currency);
				pstmt.setString(10, this.amount);
				pstmt.setString(11, this.status);
				pstmt.setString(12, this.comment);
				
				
			}else{
				String query = "UPDATE `payment_gateway`.`offline_payment` "
					+ " SET `mop` = ?, `ref_no` = ?, `drawer` = ?, `payee` = ?, "
					+ " `drawee` = ?,  `issue_date`=?, `currency`=?, "
					+ " `amount` = ?, `status`=?, `comment`=?, `modified_date` = NOW(), " 
					+ " `merchant_txn_id` = ?, `order_no` = ? WHERE id = ? ";

				pstmt = dbObj.queryUpdate(query);
				pstmt.setString(1, this.mop);
				pstmt.setString(2, this.ref_no);
				pstmt.setString(3, this.drawer);
				pstmt.setString(4, this.payee);
				pstmt.setString(5, this.drawee);
				pstmt.setDate(6, this.issue_date);
				pstmt.setString(7, this.currency);
				pstmt.setString(8, this.amount);
				pstmt.setString(9, this.status);
				pstmt.setString(10, this.comment);
				pstmt.setString(11, this.merchant_txn_id);
				pstmt.setString(12, this.order_no);
				pstmt.setInt(13, this.id);
			}

			pstmt.executeUpdate(); // issue invalid query
			pstmt.close();
			
			//lastId = this.getLastInserId(dbObj);
		} catch (SQLException e) {
			System.out.println("Error No: " + e.getErrorCode()); 
			System.out.println("\nError:" + e.getMessage());
		}

	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<OfflinePayment> OfflinePaymentList(String whrClause) {

		Mysql dbObj = new Mysql();
		ArrayList<Object> arrList = null;
		ArrayList<OfflinePayment> arrObjOfflinePay = new ArrayList<OfflinePayment>();
		
		String sql = null;
		try {
			if(whrClause == null){
				
				sql = "SELECT * FROM `offline_payment`" ;	
			}else{
				
				sql = "SELECT * FROM `offline_payment` WHERE " + whrClause;
			}
			
			arrList = dbObj.resultSetToArrayList(sql);
			
			//System.out.println("arrList : " + arrList.toString());
			
			Iterator<Object> i = arrList.iterator();
			HashMap<String,Object> row = new HashMap<String,Object> ();
			while (i.hasNext()){
				row.putAll((HashMap)i.next());

				OfflinePayment objOfflinePay = new OfflinePayment();
				
				objOfflinePay.setId((Integer) row.get("id"));
				objOfflinePay.setMerchant_txn_id((String) row.get("merchant_txn_id"));
				objOfflinePay.setOrder_no( (String) row.get("order_no"));

				objOfflinePay.setMop((String) row.get("mop"));
				objOfflinePay.setRef_no((String) row.get("ref_no"));
				objOfflinePay.setDrawer((String) row.get("drawer"));
				objOfflinePay.setPayee((String) row.get("payee"));
				objOfflinePay.setDrawee((String) row.get("drawee"));
				objOfflinePay.setIssue_date(row.get("issue_date").toString());
				objOfflinePay.setCurrency((String) row.get("currency"));
				objOfflinePay.setAmount((String) row.get("amount"));
				objOfflinePay.setStatus((String) row.get("status"));
				objOfflinePay.setComment((String) row.get("comment"));
				objOfflinePay.setCreated_date(row.get("created_date").toString());
				objOfflinePay.setModified_date(row.get("modified_date").toString());
				
				//System.out.println("\n\n\nObj Ele: " + objOfflinePay.toString());
				
				arrObjOfflinePay.add(objOfflinePay);
			}
			
		} catch (SQLException ex) {
			System.out.print("OfflinePayment List Exception " + ex.getMessage());
		}
		//System.out.println("arrObjOP : " + arrObjOfflinePay.toString());
		
		return arrObjOfflinePay;
	}

	public int getLastInserId(){
		
		Mysql dbObj = new Mysql();
		
		int lastInsertId = 0;
		try{	
			ResultSet result = dbObj.queryResult("SELECT LAST_INSERT_ID() AS ID");

			if (result.first()) {
				lastInsertId = result.getInt("ID");
			}
			result.close();
		} catch (Exception e) {
			// log err
		}
		return lastInsertId;
	}

	public static ArrayList<Object> currencyList() {

		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = null;

		try {
			String sql = "SELECT * FROM `currency_master`" ;

			arrList = mysqlObj.resultSetToArrayList(sql);
		} catch (SQLException ex) {
			// err
			System.out.print("Currency List Exception " + ex.toString());
		}
		return arrList;
	}

	@Override
	public String toString() {
		String strReturn = "\norder_id=>" + this.order_no +
							"\nmerchant_txn_id=>" + this.merchant_txn_id +
							"\nmop=>" + this.mop + 
							"\nrefNo=>" + this.ref_no +
							"\namount=>"+ this.amount +
							"\ncomment=>"+ this.comment + 
							"\ncurrency=>" + this.currency + 
							"\ndrawee=>" + this.drawee +
							"\ndrawer=>" + this.drawer + 
							"\nissue date=>" + this.issue_date + 
							"\ncreated Date=>" + this.created_date +
							"\nmodified date=>" + this.modified_date;
		return strReturn;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
	
		this.id = id.intValue();
	}
	
	/**
	 * @return the mop
	 */
	public String getMop() {
		return this.mop;
	}

	/**
	 * @param mop the mop to set
	 */
	public void setMop(String mop) {
		this.mop = mop;
	}

	/**
	 * @return the ref_no
	 */
	public String getRef_no() {
		return this.ref_no;
	}

	/**
	 * @param refNo the ref_no to set
	 */
	public void setRef_no(String refNo) {
		this.ref_no = refNo;
	}

	/**
	 * @return the drawer
	 */
	public String getDrawer() {
		return this.drawer;
	}

	/**
	 * @param drawer the drawer to set
	 */
	public void setDrawer(String drawer) {
		this.drawer = drawer;
	}

	/**
	 * @return the payee
	 */
	public String getPayee() {
		return this.payee;
	}

	/**
	 * @param payee the payee to set
	 */
	public void setPayee(String payee) {
		this.payee = payee;
	}

	/**
	 * @return the drawee
	 */
	public String getDrawee() {
		return this.drawee;
	}

	/**
	 * @param drawee the drawee to set
	 */
	public void setDrawee(String drawee) {
		this.drawee = drawee;
	}

	/**
	 * @return the issue_date
	 */
	public Date getIssue_date() {
		return this.issue_date;
	}
	
	/**
	 * @param date the issue_date to set
	 */
	public void setIssue_date(Date date) {
		
		this.issue_date = date;
	}

	/**
	 * @param date the issue_date to set
	 */
	public void setIssue_date(String date) {
		
		this.issue_date = Utility.getSqlDate(date);
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return this.amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		if(this.status == null){
			this.status = "UNAPPROVED";
		}else{
			this.status = status;
		}
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return this.comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the created_date
	 */
	public String getCreated_date() {
		return this.created_date;
	}

	/**
	 * @param createdDate the created_date to set
	 */
	public void setCreated_date(String createdDate) {
		this.created_date = createdDate;
	}

	/**
	 * @return the modified_date
	 */
	public String getModified_date() {
		return this.modified_date;
	}

	/**
	 * @param modifiedDate the modified_date to set
	 */
	public void setModified_date(String modifiedDate) {
		this.modified_date = modifiedDate;
	}

	/**
	 * @return the merchant_txn_id
	 */
	public String getMerchant_txn_id() {
		return this.merchant_txn_id;
	}

	/**
	 * @param merchantTxnId the merchant_txn_id to set
	 */
	public void setMerchant_txn_id(String merchantTxnId) {
		this.merchant_txn_id = merchantTxnId;
	}

	/**
	 * @return the order_no
	 */
	public String getOrder_no() {
		return this.order_no;
	}

	/**
	 * @param orderNo the order_no to set
	 */
	public void setOrder_no(String orderNo) {
		this.order_no = orderNo;
	}


}
