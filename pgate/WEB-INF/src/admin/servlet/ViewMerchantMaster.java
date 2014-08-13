package admin.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.Mysql;

public class ViewMerchantMaster extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList arrList = this.getMerchantMasterList();

		req.setAttribute("resultArray", arrList);
		
		String url = "/admin/viewMerchantMaster.jsp";

		try {
			this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(req, resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public ArrayList getMerchantMasterList(){

		Mysql mysqlObj = new Mysql();
		ArrayList arrList = null;
	
		try{
			String sql = "SELECT mm.*, pgm.pg_id, pgm.pg_name " +
					" FROM merchant_master mm JOIN pg_master pgm ON mm.pg_id = pgm.pg_id " +
					" ORDER BY mm.merchant_id DESC ";

			arrList =  mysqlObj.resultSetToArrayList(sql);
	
		}catch(Exception ex){
			//err
		}

		return arrList;
	}
}
