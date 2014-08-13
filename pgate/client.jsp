<%
	String  client = (String)(request.getParameter( "client" ));

	if(client.equalsIgnoreCase("spicejet")){ // spiceJet
%>
		<jsp:include page="cl-spice/header.jsp" flush="true" />
		<jsp:include page="cl-spice/footer.jsp" flush="true" />
<%
	}else if(client.equalsIgnoreCase("rirtl")){ //RIRTL
%>
		<jsp:include page="cl-rirtl/header.jsp" flush="true" />
		<jsp:include page="cl-rirtl/footer.jsp" flush="true" />
<%
	}else if(client.equalsIgnoreCase("cnk")){ //Cox And Kings
%>
		<jsp:include page="cl-cnk/header.jsp" flush="true" />
		<jsp:include page="cl-cnk/footer.jsp" flush="true" />
<%
	}else{	//Cox And Kings
%>
		<jsp:include page="cl-cnk/header.jsp" flush="true" />
		<jsp:include page="cl-cnk/footer.jsp" flush="true" />
<%		
	}
%>
