import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Utility;

/**
 * 
 */

/**
 * @author deepak.mali
 *
 */
public class AdminLogoutServlet extends HttpServlet{

	private void destroyLoginSession(HttpServletRequest req, HttpServletResponse resp){
		HttpSession session = req.getSession(false);
		session.removeAttribute("userName");
		session.removeAttribute("userRole");
		session.invalidate();
		
		String error = "Logout Successful.";
		String url = "/admin/login.jsp?error=" + error;
		Utility.redirect(resp, url);
	}
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
										throws ServletException, IOException {
		
		this.destroyLoginSession(req, resp);
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
											throws ServletException, IOException {
		
		this.destroyLoginSession(req, resp);
	}
	
}
