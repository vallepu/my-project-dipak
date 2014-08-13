<%
    String  clientObj = null; 
    int client = 0;
    try{
        clientObj = (String)(session.getAttribute( "clientId" ));
        client = Integer.parseInt(clientObj);
    }catch(Exception e){
        e.printStackTrace();
    }
    
	if(client == 4 || client == 9 || client == 8 || client == 10){	//spiceJet
%>
		<jsp:include page="cl-spice/header.jsp" flush="true" />
<%
	}else if(client == 2 || client == 7){	//RIRTL
%>
		<jsp:include page="cl-rirtl/header.jsp" flush="true" />
<%
	}else if(client == 0 || client == 1){ //Cox And Kings
%>
		<jsp:include page="cl-cnk/header.jsp" flush="true" />
<%
	}else{ //Cox And Kings
%>
		<jsp:include page="cl-cnk/header.jsp" flush="true" />
<%		
	}
%>
