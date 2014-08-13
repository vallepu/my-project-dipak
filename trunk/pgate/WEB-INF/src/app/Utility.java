/**
 * 
 */
package app;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author deepak.mali
 *
 */
public class Utility {

	public static void checkIfSession(HttpServletRequest req, HttpServletResponse resp){
		HttpSession session = req.getSession(false);
		String url = "/admin/login.jsp";
		
		try {
			if((session.getAttribute("userName") == null ||  session.getAttribute("userName") == "") ||
					(session.getAttribute("userRole") == null ||session.getAttribute("userRole") == "") ){
			}else{
				Log.logger.info("Success in checking Session: ");
				
				url = "/admin/welcome.jsp";
			}
		} catch (NullPointerException e) {
			String error = "Session Expires.";
			
			Log.logger.info("Error In checking Session: " + error);
			url += "?error=" + error;
			
		}catch (Exception e) {
			String error = "" + e.getMessage(); 
			
			Log.logger.info("Error In checking Session: " + error);
			url += "?error=" + error;
		}finally{
			Utility.redirect(resp, url);	
		}
	}
	
	/*
	 * Use to Redirect
	 */
	public static void redirect(HttpServletResponse resp, String url) {
		
		if(url == null) {
			return;
		}
		
		if(!url.startsWith(Config.get("SITE_PATH"), 0)) {
			url = Config.get("SITE_PATH") + url;
		}
		
		try {
			resp.sendRedirect( resp.encodeRedirectURL(url));
		} catch (IOException e) {
			Log.logger.severe("Error in Utility redirection: " + e.getMessage());
		
			e.printStackTrace();
		}
	}
	
	/*
	 * String date format yyyy-mm-dd
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static java.sql.Date getSqlDate(String strDate) {

		int day = 1, month = 1, year = 1900;
		try {
			String[] splitedDate = strDate.split("-");
			
		    day = Integer.parseInt(splitedDate[2]);
		
		    month = Integer.parseInt(splitedDate[1]) - 1;
		
		    year = Integer.parseInt(splitedDate[0]) - 1900;
		    
		} catch (Exception e) {
			Log.logger.warning("Error in Utility getSqlDate. May be String date must have yyyy-mm-dd format");
			System.out.println("Error in Utility getSqlDate. May be String date must have yyyy-mm-dd format");
			System.out.println(e.getMessage());
		}
	
		return new java.sql.Date(year, month, day);
	}
	
	 public static float round(float Rval, int Rpl) {
		  float p = (float)Math.pow(10,Rpl);
		  Rval = Rval * p;
		  float tmp = Math.round(Rval);
		  return tmp/p;
	}
	
	public static float round(float Rval) {

		return round(Rval, 2);
	}
	 
	public static String round(String strVal) {
		
		//float floatVal = Float.valueOf(strVal).floatValue();
		//float returnVal =  round(floatVal);
		
		return Float.valueOf(round(Float.valueOf(strVal).floatValue())).toString(); 
	}
	
}
