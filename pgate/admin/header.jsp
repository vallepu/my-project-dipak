<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="app.Config"%>
<% Config.setAllConfig(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/pgate.css">
<title>Payment Gateway Admin</title>
</head>
<body>
<%@ page import="app.Config"%>
<% Config.setAllConfig(); %>
<% String userRole = (String) session.getAttribute("userRole"); %>

<div class="page">
    <div class="header" style="height: 90px; border-bottom: 1px solid black; background: none repeat scroll 0% 0% rgb(29, 52, 103); width: 100%; color: rgb(0, 90, 169);">
        
           <table width="100%;" cellspacing="0" cellpadding="5">
		<tbody>
			<tr>
				<td width="20%" valign="middle" align="center">
					<% if(userRole == "" || userRole == null) { %>
					<a href="<%=Config.get("SITE_PATH")%>/admin/index.jsp">
	            		<img src="<%=Config.get("SITE_PATH")%>/admin/images/cnk_logo.gif" border="0">
	            	</a>
	            	<% }else{ %>
	            	<a href="<%=Config.get("SITE_PATH")%>/admin/TransactionServlet">
	            		<img src="<%=Config.get("SITE_PATH")%>/admin/images/cnk_logo.gif" border="0">
	            	</a>
	            	<% } %>
	            </td>
				<td>
					<center>
					<table cellspacing="cellpadding=5" border="0">
						<tbody><tr>
							<td style="text-align: center; color: rgb(255, 255, 255); margin-top: 5px;">
							<h2>
							Payment Gateway Admin
							</h2></td><td>
						</td></tr>
					</tbody></table>
					</center>
				</td>
				<td width="20%">&nbsp;</td>
			</tr>
		</tbody>
	</table>
    </div>
    <div class="spacer"></div>
</div>
<% if(session.getAttribute("userName") != "" && session.getAttribute("userName") != null){  %>
<div style="padding: 0pt; margin-top: 5px; border-bottom: 1px solid rgb(0, 90, 169); height: 25px;">
<table width="100%" cellspacing="0" cellpadding="0" border="0" align="right">
	<tbody><tr>
		<td align="right" class="content_blacknormal12">
			<b>Welcome <%=session.getAttribute("userName")%> </b>| <a href="<%=Config.get("SITE_PATH")%>/admin/Logout.do">Logout</a>
		</td>
		<td width="15" align="right">&nbsp;</td>	
	</tr>
</tbody></table></div>
<br />
<%  }  %>