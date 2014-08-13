import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Utility;
import app.admin.User;

/**
 * @author deepak.mali
 *
 */
public class AdminLoginServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String url = "/admin/login.jsp";
		
		if(userName != null && password != null){
			String whrClause = " username='" + userName + "' AND password='" + password + "'";
			
			User objUser = new User();
			ArrayList<User> arrObjUser = objUser.getUserList(whrClause);
			
			if(arrObjUser.size() == 1){
				
				this.registerLoginSession(req, arrObjUser.get(0));
				url = "/admin/TransactionServlet";
			}else{
				String error = "UserName/Password does not match!";
				url = "/admin/login.jsp?error=" + error;
			}
		}else{
			String error = "UserName/Password can not be empty!";
			url = "/admin/login.jsp?error=" + error;
		}
		Utility.redirect(resp, url);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Utility.checkIfSession(req, resp);
		
	}
	
	private void registerLoginSession(HttpServletRequest req, User objUser){
		
		HttpSession session = req.getSession(true);
		session.setAttribute("userName", objUser.getUsername());
		session.setAttribute("userRole", objUser.getUserRole());
		session.setAttribute("userId", objUser.getId());
	}
}
