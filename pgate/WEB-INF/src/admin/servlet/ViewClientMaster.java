package admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.Mysql;
import app.Utility;

public class ViewClientMaster extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList arrList = this.getClientMasterList();

		req.setAttribute("resultArray", arrList);
		
		String url = "/admin/viewClientMaster.jsp";

		try {
			this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(req, resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public ArrayList getClientMasterList(){

		Mysql mysqlObj = new Mysql();
		ArrayList arrList = null;
	
		try{
			String sql = "SELECT * " +
					" FROM client_master " +
					" ORDER BY client_id DESC ";

			arrList =  mysqlObj.resultSetToArrayList(sql);
	
		}catch(Exception ex){
			//err
		}

		return arrList;
	}

}
