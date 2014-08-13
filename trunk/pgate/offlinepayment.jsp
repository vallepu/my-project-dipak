<%@ page language="java" session="true"%>
<%@ page import="java.util.*"%>
<%@ page import="app.Config"%>

<%Config.setAllConfig();%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE> Payment GateWay </TITLE>
	<script language="javascript">
		var SITE_PATH = '<%=Config.get("SITE_PATH")%>';
	</script>
</HEAD>
<style>

.comment {
    font: lighter 10px sans-serif; 
}
</style>

<BODY>

<jsp:include page="header.jsp" flush="true" />

<% if(request.getParameter("error") != null){ %>


	<table align="center" border=1 style="height:30px;" cellpadding="2" cellspacing="0">
        <tr><td style="font-size: medium; color: red">Error: <%=(String)request.getParameter("error")%></td></tr>
    </table>
<%  }  %>


<%
	String payopt = request.getParameter("payopt");
	
	if(payopt.equalsIgnoreCase("Cheque") || payopt.equalsIgnoreCase("Demand Draft")){
%>
		<jsp:include page="chequedd.jsp" flush="true" />
<%
	}else if(payopt.equalsIgnoreCase("RTGS")){
%>
		<jsp:include page="rtgs.jsp" flush="true" />
<%  }else if(payopt.equalsIgnoreCase("Wire Transfer")){  %>
		<jsp:include page="wiretransfer.jsp" flush="true" />
<%  }  %>

<jsp:include page="footer.jsp" flush="true" />
 </BODY>
</HTML>
