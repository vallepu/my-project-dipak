<%@page import="java.io.PrintWriter"%>
<%@ page language="java" session="true" %>
<%@ page import="app.Config"%>
<%
	String paymentId=request.getParameter("paymentid");
	String ErrorNo= request.getParameter("Error");
	String udf1=request.getParameter("udf1");
	String udf2=request.getParameter("udf2");
	String udf3=request.getParameter("udf3");
	String udf4=request.getParameter("udf4");
	String udf5=request.getParameter("udf5");

	String 	result,postdate,tranid,auth,trackid,ref,ErrorText;
	
	/************************Response URL***********************************/
	String getProtocol=request.getScheme();
	String getDomain=request.getServerName();
	String getPort=Integer.toString(request.getServerPort());
	  
	String responseURL = getProtocol+"://"+ getDomain + ":" + getPort + Config.get("SITE_PATH") +"/responseServlet";
	
		if(ErrorNo==null)
		{
			result=request.getParameter("result");
			postdate=request.getParameter("postdate");
			tranid=request.getParameter("tranid");
			auth=request.getParameter("auth");
			trackid=request.getParameter("trackid");
			ref=request.getParameter("ref");
			out.println("REDIRECT=" + responseURL + "?paymentId="+paymentId+"&result="+result+"&auth="+auth+"&ref="+ref+"&postdate="+postdate+"&trackid="+trackid+"&tranid="+tranid+"&udf1="+udf1+"&udf2="+udf2+"&udf3="+udf3+"&udf4="+udf4+"&udf5="+udf5);
		}
		else
		{	
			ErrorText=request.getParameter("ErrorText");
			out.println("REDIRECT=" + responseURL + "?paymentId="+paymentId+"&ErrorText="+ErrorText);
		}
%>