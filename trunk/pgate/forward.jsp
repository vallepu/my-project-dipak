<%@ page import="java.util.*"%>
<%
	String pgName = request.getParameter("pgName");
	String clientId = request.getParameter("clientId");
	String txnAmount = request.getParameter("txnAmount");
	String orderNo = request.getParameter("orderNo");
	
	String merchantCode = request.getParameter("merchantCode");
	String currency = request.getParameter("currency");
	String pgId = request.getParameter("pgId");


/********************* SOF E-Serve ****************************/
	if(pgName.equalsIgnoreCase("eServe")){

		String currencyCode = request.getParameter("currencyCode");

		try{
		%>		
			<jsp:forward page="/EServeServlet" >
				<jsp:param name="clientId" value="<%=clientId %>" /> 
				<jsp:param name="txnAmount" value="<%=txnAmount %>" /> 
				<jsp:param name="orderNo" value="<%=orderNo %>" /> 
				<jsp:param name="merchantCode" value="<%=merchantCode %>" /> 
				<jsp:param name="currencyCode" value="<%=currencyCode %>" /> 
			</jsp:forward>
		<%
		}
		catch(Exception ex){
			out.println("Can not Forwad to EServe");
			ex.printStackTrace ();
		}
    }
/********************* EOF E-Serve ****************************/


/********************* SOF ICICI ****************************/
	if(pgName.equalsIgnoreCase("ICICI")){

		try{
		%>		
			<jsp:forward page="/ICICIServlet" >
				<jsp:param name="clientId" value="<%=clientId %>" /> 
				<jsp:param name="txnAmount" value="<%=txnAmount %>" /> 
				<jsp:param name="orderNo" value="<%=orderNo %>" /> 
				<jsp:param name="merchantCode" value="<%=merchantCode %>" /> 
				<jsp:param name="currency" value="<%=currency %>" /> 
			</jsp:forward>
		<%
		}
		catch(Exception ex){
			out.println("Can not Forwad to ICICI");
			ex.printStackTrace ();
		}
   }
/********************* EOF ICICI ****************************/


/********************* SOF ICICI ****************************/
    if(pgName.equalsIgnoreCase("BillDesk")){

        String password = request.getParameter("password");
        String securityId = request.getParameter("securityId");
        try{
        %>      
            <jsp:forward page="/BillDeskServlet" >
                <jsp:param name="clientId" value="<%=clientId %>" /> 
                <jsp:param name="txnAmount" value="<%=txnAmount %>" /> 
                <jsp:param name="orderNo" value="<%=orderNo %>" /> 
                <jsp:param name="merchantCode" value="<%=merchantCode %>" /> 
                <jsp:param name="password" value="<%=password %>" />
                <jsp:param name="securityId" value="<%=securityId %>" />
                <jsp:param name="currency" value="<%=currency %>" /> 
            </jsp:forward>
        <%
        }
        catch(Exception ex){
            out.println("Can not Forwad to ICICI");
            ex.printStackTrace ();
        }
   }
/********************* EOF ICICI ****************************/

%>