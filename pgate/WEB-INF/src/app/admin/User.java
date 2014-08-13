
package app.admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import app.Mysql;

/**
 * @author deepak.mali
 *
 */
public class User {
	
	private String tblName = "`payment_gateway`.`user_master`";
	
	protected int id = 0;
	
	protected String user_role = null;

	protected String username = null;
	protected String password = null;
	protected String created_date = null;
	protected String modified_date = null;
	
	
	public void save() {

		Mysql dbObj = new Mysql();
		PreparedStatement pstmt = null;
	
		try {
			if(this.id == 0){
				String query = "INSERT INTO " + this.getTblName() + " ( `user_role`, `username`, `password`, `modified_date`) " + 
				" VALUES ( ?, ?, ?, NOW()); ";

				pstmt = dbObj.queryUpdate(query);
				pstmt.setString(1, this.getUserRole());
				pstmt.setString(2, this.getUsername());
				pstmt.setString(3, this.getPassword());
	
			} else {
				String query = "UPDATE " + this.getTblName() + " SET " +
								" `user_role`=?, `username`=?, `password`=?, `modified_date`=NOW() "+
								" WHERE id = ? ";

				pstmt = dbObj.queryUpdate(query);
				pstmt.setString(1, this.getUserRole());
				pstmt.setString(2, this.getUsername());
				pstmt.setString(3, this.getPassword());				
				pstmt.setInt(4, this.getId());
			}	
		
			pstmt.executeUpdate(); // issue invalid query
			pstmt.close();
		}catch (SQLException e) {
			System.out.println("Error No: " + e.getErrorCode()); 
			System.out.println("\nError:" + e.getMessage());
		}
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
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<User> getUserList(String whrClause) {
		
		ArrayList<User> arrUserObjList = new ArrayList<User>();
		Mysql dbObj = new Mysql();
		ArrayList<Object> arrList = null;
		
		
		String sql = null;
		try {
			if(whrClause == null){
				
				sql = "SELECT * FROM " + this.getTblName() ;	
			}else{
				
				sql = "SELECT * FROM " + this.getTblName() + " WHERE " + whrClause;
			}
			
			arrList = dbObj.resultSetToArrayList(sql);
			
			System.out.println(" arrList : " + arrList.toString());
			
			Iterator<Object> i = arrList.iterator();
			HashMap<String,Object> row = new HashMap<String,Object> ();
			while (i.hasNext()){
				row.putAll((HashMap)i.next());
				
				User objUser = new User();
				
				objUser.setId((Integer)row.get("id"));
				objUser.setUserRole((String) row.get("user_role"));
				objUser.setUsername((String) row.get("username"));
				objUser.setPassword((String) row.get("password"));
				
				objUser.setCreated_date(row.get("created_date"));
				objUser.setModified_date(row.get("modified_date"));
				
				arrUserObjList.add(objUser);
			}
			
		} catch (SQLException ex) {
			System.out.print( this.getClass().getName() + " List Exception " + ex.getMessage());
		}
		
		return arrUserObjList; 
	}
	
	
	
	@Override
	public String toString(){
		String returnString = "\nId=> " 		+ this.id +
								"\nuserRole=> " + this.user_role +
								"\nUserName=> " + this.username +
								"\nPassword=> " + this.password +
								"\nCreated=> " 	+ this.created_date + 
								"\nModified=> " + this.modified_date; 
		return returnString;
	}
	
	
	/**
	 * @return the tbl_name
	 */
	public String getTblName() {
		return this.tblName;
	}

	/**
	 * @param tblName the tbl_name to set
	 */
	public void setTblName(String tblName) {
		this.tblName = tblName;
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
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * @param createdDate the created_date to set
	 */
	public void setCreated_date(Object createdDate) {
		if(createdDate == null){
			this.created_date = null;
		}else{
			this.created_date = createdDate.toString();
		}
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
	 * @param modifiedDate the modified_date to set
	 */
	public void setModified_date(Object modifiedDate) {
		if(modifiedDate == null){
			this.modified_date = null;
		}else{
			this.modified_date = modifiedDate.toString();
		}
	}
	/**
	 * @return the user_role
	 */
	public String getUserRole() {
		return this.user_role;
	}

	/**
	 * @param userRole the user_role to set
	 */
	public void setUserRole(String userRole) {
		this.user_role = userRole;
	}
}
