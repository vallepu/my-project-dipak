package admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.Mysql;

public class ViewPgMaster extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Utility.checkIfSession(req, resp);
		ArrayList arrList = this.getPgMasterList();

		req.setAttribute("resultArray", arrList);
		
		String url = "/admin/viewPgMaster.jsp";

		try {
			this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(req, resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = req.getParameter("id");
		String stat = req.getParameter("stat");
		PrintWriter pwd = resp.getWriter();
		
		if(id == "" || id == null){
			ArrayList arrList = this.getPgMasterList();
			
			req.setAttribute("resultArray", arrList);
			String url = "/admin/viewPgMaster.jsp";

			try {
				this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(req, resp);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else{
			ArrayList arrStatus = updateStatus(id,stat);
	        pwd.println("<td class='classTd' id='act_status'>"+ stat +"</td>");
		}
	}
	
	public ArrayList getPgMasterList(){

		Mysql mysqlObj = new Mysql();
		ArrayList arrList = null;
	
		try{
			String sql = "SELECT * " +
					" FROM pg_master " +
					" ORDER BY pg_id DESC ";

			arrList =  mysqlObj.resultSetToArrayList(sql);
	
		}catch(Exception ex){
			//err
		}

		return arrList;
	}
	
	public ArrayList updateStatus(String id, String stat){

		Mysql mysqlObj = new Mysql();
		ArrayList arrList = null;
		ArrayList arrList1 = null;
		ArrayList arrList2 = null;
	
		try{
			
			String selsql1 = "SELECT * " +
			" FROM merchant_master " +
			" WHERE pg_id = "+id;

			arrList1 =  mysqlObj.resultSetToArrayList(selsql1);
			
			String selsql2 = "SELECT * " +
			" FROM mid_client_pg " +
			" WHERE pg_id = "+id;

			arrList2 =  mysqlObj.resultSetToArrayList(selsql2);
			
			if(arrList1 != null){
				String updtmm = "UPDATE merchant_master " +
				" SET is_active = ? " +
				" WHERE pg_id = ? ";

				PreparedStatement pstmt = mysqlObj.queryUpdate(updtmm);
		
				pstmt.setString(1, stat);
				pstmt.setString(2, id);
				
				pstmt.executeUpdate(); // issue invalid query
				pstmt.close();
			}
			
			if(arrList2 != null){
				String updtmcp = "UPDATE mid_client_pg " +
				" SET is_active = ? " +
				" WHERE pg_id = ? ";

				PreparedStatement pstmt = mysqlObj.queryUpdate(updtmcp);
		
				pstmt.setString(1, stat);
				pstmt.setString(2, id);
				
				pstmt.executeUpdate(); // issue invalid query
				pstmt.close();
			}
			
			String sql = "UPDATE pg_master " +
			" SET is_active = ? " +
			" WHERE pg_id = ? ";

			PreparedStatement pstmt = mysqlObj.queryUpdate(sql);
	
			pstmt.setString(1, stat);
			pstmt.setString(2, id);
			
			pstmt.executeUpdate(); // issue invalid query
			pstmt.close();
			
			
	
		}catch(Exception ex){
			//err
		}

		return arrList;
	}
}
