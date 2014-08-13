package admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.Masters;
import app.Mysql;

public class GetMids extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pgIds = req.getParameter("pgIdval");
		PrintWriter pwd = resp.getWriter();
		
		//pwd.println("Hidden Pg Id:- " + pgIds);
		
		ArrayList arrMerchantLists = getmerchantLists(pgIds);
		
		//req.setAttribute("arrMerchantLists", arrMerchantLists);
		
		if(!(arrMerchantLists.isEmpty())){
			//pwd.println("<select name='merchant_id' class='txtl'>");
			pwd.println("<option value=''>SELECT</option>");
			HashMap<String, Object> objField = null;
			
			Iterator i = arrMerchantLists.iterator();
			objField = new HashMap<String, Object>();
			//System.out.println("All of the mappings   " + arrMerchantLists);
			while (i.hasNext()){
				objField.putAll((HashMap)i.next());
				
				String merchant_id = objField.get("merchant_id").toString();
                String merchant_code = objField.get("merchant_code").toString();
                String txn_limit = objField.get("txn_limit").toString();
                String valid_from = objField.get("valid_from").toString();
                String valid_to = objField.get("valid_to").toString();
                
                /*req.setAttribute("txn_limit", txn_limit);
                req.setAttribute("valid_from", valid_from);
    			req.setAttribute("valid_to", valid_to);*/
    			
                pwd.println("<option value="+ merchant_id+">"+merchant_code +"</option>");
                
			}
			//pwd.println("</select>");
		}
		
	}
	
	public static ArrayList<Object> getmerchantLists(String pgIds) {

		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = null;

		try {
			String sql = "SELECT * FROM `merchant_master` where pg_id = " + pgIds;

			arrList = mysqlObj.resultSetToArrayList(sql);
		} catch (SQLException ex) {
			// err
			System.out.print("Currency List Exception " + ex.toString());
		}
		return arrList;
	}

}
