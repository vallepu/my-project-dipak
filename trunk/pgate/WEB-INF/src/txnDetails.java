

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.Mysql;


public class txnDetails extends HttpServlet{ 
  
  @Override
public void doGet(HttpServletRequest request, HttpServletResponse response)
												throws ServletException,IOException{

		ArrayList arrList = this.getTxnList();

		request.setAttribute("resultArray", arrList);
		
		String url = "/txnHtmlDetails.jsp";

		try {
			this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(request, response);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}



	private ArrayList getTxnList(){

		Mysql mysqlObj = new Mysql();
		ArrayList arrList = null;
	
		try{
			String sql = "SELECT txn.txn_id, txn.currency_code, txn.currency_no, txn.txn_amount, txn.order_no, txn.pg_name, " +
							" txn.merchant_id, clnt.client_name, txn.status, txn.created_date " +
							" FROM transaction txn, client_master clnt" +
							" WHERE txn.client_id = clnt.client_id " +
							" ORDER BY txn_id DESC ";

			arrList =  mysqlObj.resultSetToArrayList(sql);
	
		}catch(Exception ex){
			//err
		}

		return arrList;
	}
	

}
