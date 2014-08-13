package admin.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Log;
import app.Masters;
import app.Mysql;
import app.Utility;

public class PgMasterServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(true);
		String pgmId = req.getParameter("pgmId");
		
		session.setAttribute("pgmId", pgmId);
		
		String pgName = req.getParameter("pg_name");
		String isActive = req.getParameter("is_active");
		
		if(pgmId == "" || pgmId == null){
			ArrayList arrList = this.savePgMaster(pgName,isActive);
		}else if(pgmId != "" || pgmId != null){
			ArrayList updtarrList = this.updatePgMaster(pgName,isActive,pgmId);
			
			ArrayList<Object> arrupdtStatusLists = Masters.updatePgStatus(pgmId,isActive);
			
			req.setAttribute("arrupdtStatusLists", arrupdtStatusLists);
		}
		
		String url = "/admin/ViewPgMaster";
		
		Utility.redirect(resp, url);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(true);
		String url = null;
		String pgmId = req.getParameter("pgmId");
		
		if(pgmId == "" || pgmId == null){
			url = "/admin/pgMaster.jsp";
			
		}else if(pgmId != "" || pgmId != null){
			
			//session.setAttribute("mcpId", mcpId);
			req.setAttribute("pgmId", pgmId);
			
			ArrayList updatedarrList = this.selectPgMaster(pgmId);
			
			req.setAttribute("updatedarrList", updatedarrList);
			
			HashMap<String, Object> objField = null;
			
			Iterator i = updatedarrList.iterator();
			objField = new HashMap<String, Object>();
			//System.out.println("All of the mappings   " + updatedarrList);
			
			objField.putAll((HashMap)i.next());
			
			String pg_name = (String) objField.get("pg_name");
			String is_active = (String) objField.get("is_active");
			
			req.setAttribute("pg_name", pg_name);
			req.setAttribute("is_active", is_active);
			
			url = "/admin/pgMaster.jsp";
			
			
		}
		try {
			this.getServletConfig().getServletContext().getRequestDispatcher(url).forward(req, resp);
		}
		catch (Exception ex) {
			Log.logger.info("URL Exception" + ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
	public ArrayList savePgMaster(String pgName, String isActive) {
		
		int lastInsertId = 0;
		Mysql mysqlObj = new Mysql();
		ArrayList<Object> arrList = null;

		try {
			String sql = "INSERT INTO pg_master " +
						" SET pg_name = ?, " + 
						" is_active = ?, " +
						" created_date = now() ";
			
			PreparedStatement pstmt = mysqlObj.queryUpdate(sql);

			pstmt.setString(1, pgName);
			pstmt.setString(2, isActive);

			pstmt.executeUpdate(); // issue invalid query
			pstmt.close();

			ResultSet result = mysqlObj
					.queryResult("SELECT LAST_INSERT_ID() AS ID");
			if (result.first()) {
				lastInsertId = result.getInt("ID");
			}

			result.close();
		} catch (SQLException ex) {
			// err
			System.out.print("PG Master insert Exception " + ex.toString());
		}
		return arrList;
	}
	
	public ArrayList updatePgMaster(String pgName, String isActive, String pgmId) {
			Mysql mysqlObj = new Mysql();
			ArrayList<Object> updtarrList = null;
	
			try {
				String sql = "UPDATE pg_master " +
							" SET pg_name = ?, " + 
							" is_active = ?, " +
							" updated_date = now() " +
							" WHERE pg_id = ? ";
				
				PreparedStatement pstmt = mysqlObj.queryUpdate(sql);
				
				pstmt.setString(1, pgName);
				pstmt.setString(2, isActive);
				pstmt.setString(3, pgmId);
	
				pstmt.executeUpdate(); // issue invalid query
				pstmt.close();
	
				/*ResultSet result = mysqlObj
						.queryResult("SELECT LAST_INSERT_ID() AS ID");
				if (result.first()) {
					lastInsertId = result.getInt("ID");
				}
	
				result.close();*/
			} catch (SQLException ex) {
				// err
				Log.logger.warning("PG Master Update Exception " + ex.toString());
			}
			return updtarrList;
	}
	
	public ArrayList selectPgMaster(String pgmId) {
		
		int lastInsertId = 0;
		Mysql mysqlObj = new Mysql();
		ArrayList selarrList = null;

		try {
			String sql = "SELECT * " +
						" FROM pg_master " +
						" WHERE pg_id="+pgmId;
			
			selarrList =  mysqlObj.resultSetToArrayList(sql);
		} catch (Exception ex) {
			// err
			System.out.print("Currency List Exception " + ex.toString());
		}
		return selarrList;
}

}
