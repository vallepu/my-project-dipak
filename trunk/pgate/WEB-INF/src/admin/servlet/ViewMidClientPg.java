package admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.Mysql;

public class ViewMidClientPg extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Utility.checkIfSession(req, resp);
		ArrayList arrList = this.getMidClientPgList();

		req.setAttribute("resultArray", arrList);
		
		String url = "/admin/viewMidClientPg.jsp";

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
		/*String mcpId = req.getParameter("mcpId");
		if(mcpId != ""){
			String updateurl = "/admin/addMidClientPg.jsp";

			try {
				this.getServletConfig().getServletContext().getRequestDispatcher(updateurl).forward(req, resp);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else{*/
		
			String id = req.getParameter("id");
			String stat = req.getParameter("stat");
			PrintWriter pwd = resp.getWriter();
			
			if(id == "" || id == null){
				ArrayList arrList = this.getMidClientPgList();
				
				req.setAttribute("resultArray", arrList);
				String url = "/admin/viewMidClientPg.jsp";

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
			
		//}
	}
	
	public ArrayList getMidClientPgList(){

		Mysql mysqlObj = new Mysql();
		ArrayList arrList = null;
	
		try{
			String sql = "SELECT cm.client_name, pm.pg_name, mm.merchant_code, mcp.* " +
					" FROM mid_client_pg mcp, client_master cm, pg_master pm, merchant_master mm " +
					" WHERE cm.client_id = mcp.client_id " +
					" AND pm.pg_id = mcp.pg_id " +
					" AND mm.merchant_id = mcp.merchant_id " +
					" ORDER BY mcp.mcp_id DESC ";

			arrList =  mysqlObj.resultSetToArrayList(sql);
	
		}catch(Exception ex){
			//err
		}

		return arrList;
	}
	
	public ArrayList updateStatus(String id, String stat){

		Mysql mysqlObj = new Mysql();
		ArrayList arrList = null;
	
		try{
			String sql = "UPDATE mid_client_pg " +
					" SET is_active = ? " +
					" WHERE mcp_id = ? ";

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
