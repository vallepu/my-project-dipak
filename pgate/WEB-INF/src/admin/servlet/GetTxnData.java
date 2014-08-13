package admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.Mysql;

public class GetTxnData extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String merchantCode = req.getParameter("merchantCode");
		PrintWriter pwd = resp.getWriter();
		
		ArrayList arrTxnDataLists = gettxndata(merchantCode);
		
		//req.setAttribute("arrMerchantLists", arrMerchantLists);
		
		if(!(arrTxnDataLists.isEmpty())){
			
			HashMap<String, Object> objField = null;
			
			Iterator i = arrTxnDataLists.iterator();
			objField = new HashMap<String, Object>();
			//System.out.println("All of the mappings   " + arrTxnDataLists);
			while (i.hasNext()){
				objField.putAll((HashMap)i.next());
				
                String txn_limit = objField.get("txn_limit").toString();
                String valid_from = objField.get("valid_from").toString();
                String valid_to = objField.get("valid_to").toString();
    			
                pwd.println("<input type='text' name='txnLimit' id='txnLimit' value='"+txn_limit+"'>");
                pwd.println("<input type='text' name='validFrom' id='validFrom' value='"+valid_from+"'>");
                pwd.println("<input type='text' name='validTo' id='validTo' value='"+valid_to+"'>");
                
			}
		}
	}
	
	public static ArrayList<Object> gettxndata(String merchantCode) {

		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = null;

		try {
			String sql = "SELECT * FROM `merchant_master` where merchant_id  = " + merchantCode;

			arrList = mysqlObj.resultSetToArrayList(sql);
		} catch (SQLException ex) {
			// err
			System.out.print("Currency List Exception " + ex.toString());
		}
		return arrList;
	}

}
