package admin.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.Mysql;

public class ViewCurrencyMaster extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList arrList = this.getCurrencyMasterList();

		req.setAttribute("resultArray", arrList);
		
		String url = "/admin/viewCurrencyMaster.jsp";

		try {
			this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(req, resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public ArrayList getCurrencyMasterList(){

		Mysql mysqlObj = new Mysql();
		ArrayList arrList = null;
	
		try{
			String sql = "SELECT * " +
					" FROM currency_master " +
					" ORDER BY currency_id DESC ";

			arrList =  mysqlObj.resultSetToArrayList(sql);
	
		}catch(Exception ex){
			//err
		}

		return arrList;
	}
}
