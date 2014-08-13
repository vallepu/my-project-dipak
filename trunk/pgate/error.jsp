<%@ page language="java" session="true"%>
<%@ page import="java.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
	<TITLE> Payment Gateway </TITLE>
</HEAD>
<style>
    .cls{
        font: bold sans-serif message-box;
        font-size: 13px;
        color: red;
}
.name{
       color: black;
       font: bold sans-serif message-box;
       font-size: 13px;
}
</style>
<BODY>
<jsp:include page="header.jsp" flush="true" />
	<br><br>

	<table border="0" align="center" width="70%">
	<tr>
		<th align="left" colspan="2">Oop's Something went wrong. </th>
	</tr>

<%	
                  Enumeration paramNames = request.getParameterNames();
	
			      while (paramNames.hasMoreElements()){
			    		String name = (String)paramNames.nextElement();
						out.println("<tr><td class='name'>" + name + "</td>");
					
						String[] paramValues = request.getParameterValues(name);
					
						for(int i=0; i<paramValues.length; i++) {
							out.println("<td class='cls' align='left'>" +  paramValues[i] + "</td></tr>");
						}
				
			      }
                  
%>	

	</table>
	<br><br>

<jsp:include page="footer.jsp" flush="true" />

</BODY>
</HTML>
